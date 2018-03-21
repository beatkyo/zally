package de.zalando.zally.rule.zally

import de.zalando.zally.getFixture
import de.zalando.zally.rule.ApiAdapter
import de.zalando.zally.rule.api.Violation
import io.swagger.v3.oas.models.OpenAPI
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class NoProtocolInHostRuleTest {

    private val rule = NoProtocolInHostRule()

    val expectedViolation = rule.let {
        Violation("", emptyList())
    }

    @Test
    fun emptySwagger() {
        assertThat(rule.validate(ApiAdapter(OpenAPI()))).isNull()
    }

    //TODO rewrite
/*
    @Test
    fun positiveCase() {
        val swagger = Swagger().apply { host = "google.com" }
        assertThat(rule.validate(ApiAdapter(swagger))).isNull()
    }



    @Test
    fun negativeCaseHttp() {
        val swagger = Swagger().apply { host = "http://google.com" }
        val res = rule.validate(swagger)
        assertThat(res?.copy(description = "")).isEqualTo(expectedViolation)
    }

    @Test
    fun negativeCaseHttps() {
        val swagger = Swagger().apply { host = "https://google.com" }
        val res = rule.validate(swagger)
        assertThat(res?.copy(description = "")).isEqualTo(expectedViolation)
    }
*/

    @Test
    fun positiveCaseSpp() {
        val swagger = getFixture("api_spp.json")
        assertThat(rule.validate(ApiAdapter(swagger))).isNull()
    }

    @Test
    fun positiveCaseSpa() {
        val swagger = getFixture("api_spa.yaml")
        assertThat(rule.validate(ApiAdapter(swagger))).isNull()
    }
}
