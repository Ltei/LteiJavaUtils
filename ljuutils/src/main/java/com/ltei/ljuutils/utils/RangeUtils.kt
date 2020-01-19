package com.ltei.ljuutils.utils

fun IntRange.clamp(range: IntRange) = kotlin.math.max(first, range.first)..kotlin.math.min(last, range.last)