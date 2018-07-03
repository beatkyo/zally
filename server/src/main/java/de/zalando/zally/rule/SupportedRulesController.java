package de.zalando.zally.rule;

import de.zalando.zally.dto.RuleDTO;
import de.zalando.zally.dto.RulesListDTO;
import de.zalando.zally.dto.SeverityBinder;
import de.zalando.zally.rule.api.Severity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@CrossOrigin
@RestController
public class SupportedRulesController {

    private final RulesManager rules;
    private final RulesPolicy rulesPolicy;

    @Autowired
    public SupportedRulesController(RulesManager rules, RulesPolicy rulesPolicy) {
        this.rules = rules;
        this.rulesPolicy = rulesPolicy;
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Severity.class, new SeverityBinder());
    }

    @ResponseBody
    @GetMapping("/supported-rules")
    public RulesListDTO listSupportedRules(
            @RequestParam(value = "type", required = false) Severity typeFilter,
            @RequestParam(value = "is_active", required = false) Boolean isActiveFilter) {

        List<RuleDTO> filteredRules = rules
                .getRules()
                .stream()
                .filter(details -> filterByIsActive(details, isActiveFilter))
                .filter(details -> filterByType(details, typeFilter))
                .map(this::toDto)
                .sorted(comparing(RuleDTO::getType)
                        .thenComparing(RuleDTO::getCode)
                        .thenComparing(RuleDTO::getTitle))
                .collect(toList());

        return new RulesListDTO(filteredRules);
    }

    private boolean filterByIsActive(RuleDetails details, Boolean isActiveFilter) {
        boolean isActive = rulesPolicy.accepts(details.getRule());
        return isActiveFilter == null || isActive == isActiveFilter;
    }

    private boolean filterByType(RuleDetails details, Severity typeFilter) {
        return typeFilter == null || typeFilter.equals(details.getRule().severity());
    }

    private RuleDTO toDto(RuleDetails details) {
        return new RuleDTO(
                details.getRule().title(),
                details.getRule().severity(),
                details.getRuleSet().url(details.getRule()).toString(),
                details.getRule().id(),
                rulesPolicy.accepts(details.getRule())
        );
    }
}
