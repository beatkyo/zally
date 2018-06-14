package de.zalando.zally.rule

import com.fasterxml.jackson.core.JsonPointer
import de.zalando.zally.rule.api.Violation
import org.assertj.core.api.AbstractListAssert
import org.assertj.core.api.ListAssert
import org.assertj.core.api.ObjectAssert

class ViolationsAssert(violations: List<Violation>?) : AbstractListAssert<ViolationsAssert, List<Violation>, Violation, ObjectAssert<Violation>>(violations, ViolationsAssert::class.java) {

    override fun toAssert(value: Violation?, description: String?): ObjectAssert<Violation> {
        return ObjectAssert<Violation>(value).`as`(description)
    }

    fun descriptionsAllEqualTo(description: String): ViolationsAssert {
        descriptions().containsOnly(description)
        return this
    }

    fun descriptionsEqualTo(vararg descriptions: String): ViolationsAssert {
        descriptions().containsExactly(*descriptions)
        return this
    }

    fun descriptions(): ListAssert<String> {
        isNotNull
        return ListAssert(actual.map { it.description }).`as`("descriptions")
    }

    fun pointers(): ListAssert<JsonPointer?> {
        isNotNull
        return ListAssert(actual.map { it.pointer }).`as`("pointers")
    }

    fun pointersEqualTo(vararg pointers: String): ViolationsAssert {
        isNotNull
        ListAssert(actual.map { it.pointer!!.toString() }).`as`("pointers").containsExactly(*pointers)
        return this
    }
}
