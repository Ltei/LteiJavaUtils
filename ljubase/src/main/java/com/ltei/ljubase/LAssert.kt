package com.ltei.ljubase

object LAssert {

    fun isTrue(valueTag: String, value: Boolean) {
        if (!value) {
            throw IllegalArgumentException("$valueTag is false")
        }
    }

    fun isTrue(value: Boolean) {
        isTrue("value", value)
    }

    fun isFalse(valueTag: String, value: Boolean) {
        if (value) {
            throw IllegalArgumentException("$valueTag is true")
        }
    }

    fun isFalse(value: Boolean) {
        isFalse("value", value)
    }

    fun isIn(value: Int, range: IntRange) {
        if (value !in range) {
            throw IllegalArgumentException("value($value) not in range($range)")
        }
    }

    fun eq(value1: Any?, value2: Any?, value1Tag: String = "value1", value2Tag: String = "value2") {
        if (value1 != value2) {
            throw IllegalArgumentException("$value1Tag($value1) != $value2Tag($value2)")
        }
    }

    fun eq(
        value1: Float,
        value2: Float,
        delta: Float = 0.00001f,
        value1Tag: String = "value1",
        value2Tag: String = "value2"
    ) {
        if (value1 < value2 - delta || value1 > value2 + delta) {
            throw IllegalArgumentException("$value1Tag($value1) != $value2Tag($value2)")
        }
    }

    fun ne(value1: Any?, value2: Any?, value1Tag: String = "value1", value2Tag: String = "value2") {
        if (value1 == value2) {
            throw IllegalArgumentException("$value1Tag($value1) == $value2Tag($value2)")
        }
    }

    fun notNull(value: Any?) {
        if (value == null) {
            throw IllegalArgumentException("value($value) is null")
        }
    }

    fun noNull(vararg values: Any?) {
        for ((idx, v) in values.withIndex()) {
            if (v == null) {
                throw IllegalArgumentException("value $idx is null")
            }
        }
    }

    fun allNull(vararg values: Any?) {
        for (v in values) {
            if (v != null) {
                throw IllegalArgumentException("value($v) is not null")
            }
        }
    }

    fun isMore(value: Double, moreThan: Double) {
        if (value <= moreThan) {
            throw IllegalArgumentException("value($value) <= moreThan($moreThan)")
        }
    }
}
