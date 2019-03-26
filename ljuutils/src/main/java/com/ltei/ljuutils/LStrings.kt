package com.ltei.ljuutils

object LStrings {

    fun formatDouble(double: Double, precision: Int = 2): String {
        return "%.${precision}f".format(double)
    }

    fun formatName(str: String): String {
        if (str.isBlank()) return str

        val result = StringBuilder()
        for (chunk in str.split(" ")) {
            if (chunk.isNotEmpty()) {
                result.append(chunk[0].toUpperCase() + chunk.drop(1))
                result.append(" ")
            }
        }

        return if (result.isNotEmpty()) {
            result.substring(0, result.length - 1)
        } else {
            result.toString()
        }
    }

    fun capitalizeFirst(str: String): String {
        return str[0].toUpperCase() + str.substring(1)
    }

    fun trimNonAlphanum(str: String): String {
        return str.trim { !it.isLetterOrDigit() }
    }

    fun toUTF8(str: String): ByteArray {
        return str.toByteArray(Charsets.UTF_8)
    }

}