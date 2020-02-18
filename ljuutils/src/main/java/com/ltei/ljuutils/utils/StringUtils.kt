package com.ltei.ljuutils.utils

fun String.capitalizeWords(): String {
    val split = split(" ")
    return split.withIndex().fold(StringBuilder()) { acc, (idx, it) ->
        acc.append(it.capitalize())
        if (idx < split.lastIndex) acc.append(" ")
        acc
    }.toString()
}

fun Number.format(decimals: Int) = "%.${decimals}f".format(this.toDouble())