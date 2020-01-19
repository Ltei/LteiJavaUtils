package com.ltei.ljuutils.utils

import com.ltei.ljubase.debug.Logger
import kotlin.math.abs

fun IntArray.debug(logger: Logger) {
    val avg = average().toFloat()
    val avgDelta = fold(0f) { acc, it -> acc + abs(it - avg) } / size
    logger.debug("(min=${min()}, max=${max()}, avg=$avg, avgDelta=$avgDelta)")
}

fun FloatArray.debug(logger: Logger) {
    val avg = average().toFloat()
    val deltaAcc = fold(0f) { acc, it ->
        acc + abs(it - avg)
    }
    logger.debug("(min=${min()}, max=${max()}, avg=$avg, avgDelta=${deltaAcc / size})")
}