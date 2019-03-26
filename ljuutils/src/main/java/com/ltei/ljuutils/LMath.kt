package com.ltei.ljuutils

import com.ltei.ljubase.LAssert
import kotlin.math.*

object LMath {

    fun crop(value: Float, min: Float, max: Float): Float {
        return when {
            value < min -> min
            value > max -> max
            else -> value
        }
    }

    fun rescaleFrom01(value: Float, newValue0: Float, newValue1: Float): Float {
        return (newValue1 - newValue0) * value + newValue0
    }

    fun rescaleTo01(value: Float, lastValue0: Float, lastValue1: Float): Float {
        return (value - lastValue0) / (lastValue1 - lastValue0)
    }

    fun rescale(value: Float, lastValue0: Float, lastValue1: Float, newValue0: Float, newValue1: Float): Float {
        return rescaleFrom01(rescaleTo01(value, lastValue0, lastValue1), newValue0, newValue1)
    }

    fun graphicPowBR(x: Float, pow: Int): Float {
        return x.pow(pow)
    }

    fun graphicPowBL(x: Float, pow: Int): Float {
        return (1f - x).pow(pow)
    }

    fun graphicPowTR(x: Float, pow: Int): Float {
        return 1f - graphicPowBR(x, pow)
    }

    fun graphicPowTL(x: Float, pow: Int): Float {
        return 1f - graphicPowBL(x, pow)
    }


    fun rescaleFrom01(value: Double, newValue0: Double, newValue1: Double): Double {
        return (newValue1 - newValue0) * value + newValue0
    }

    fun rescaleTo01(value: Double, lastValue0: Double, lastValue1: Double): Double {
        return (value - lastValue0) / (lastValue1 - lastValue0)
    }

    fun rescale(value: Double, lastValue0: Double, lastValue1: Double, newValue0: Double, newValue1: Double): Double {
        return rescaleFrom01(rescaleTo01(value, lastValue0, lastValue1), newValue0, newValue1)
    }

    fun modPositive(value: Double, mod: Double): Double {
        LAssert.isMore(mod, 0.0)
        var result = value
        while (result < 0) result += mod
        while (result > mod) result -= mod
        return result
    }

    fun distance2(x0: Int, y0: Int, x1: Int, y1: Int): Int {
        val dx = x0 - x1
        val dy = y0 - y1
        return dx * dx + dy * dy
    }

    fun distance2(x0: Long, y0: Long, x1: Long, y1: Long): Long {
        val dx = x0 - x1
        val dy = y0 - y1
        return dx * dx + dy * dy
    }

    fun distance2(x0: Double, y0: Double, x1: Double, y1: Double): Double {
        val dx = x0 - x1
        val dy = y0 - y1
        return dx * dx + dy * dy
    }


    fun angleRadFromVec(x: Double, y: Double): Double {
        var angle = Math.atan2(y, x)
        if (angle < 0) angle += 2.0 * Math.PI
        return angle
    }

    fun angleRadFromVec(x: Float, y: Float): Float {
        return angleRadFromVec(x.toDouble(), y.toDouble()).toFloat()
    }

    fun angleRadDifference(angle1: Double, angle2: Double): Double {
        var result = angle2 - angle1
        while (result <= -180.0) result += 360.0
        while (result >= 180.0) result -= 360.0
        return result
    }

    fun angleRadMinDistance(angle1: Double, angle2: Double): Double {
        var result = angle2 - angle1
        while (result <= -180.0) result += 360.0
        while (result >= 180.0) result -= 360.0
        return result
    }

    fun angleRadDistance(angle1: Double, angle2: Double): Double {
        return modPositive(angle1 - angle2, 2.0 * PI)
    }

    fun angleRadScaledAbsDistance(angle1: Double, angle2: Double, cosScale: Double? = null, sinScale: Double? = null): Double {
        var distance = angleRadDistance(angle1, angle2)
        cosScale?.let {
            val cosDelta = abs(cos(angle1) - cos(angle2)) / 2.0
            distance *= cosDelta * (it - 1.0) + 1.0
        }
        sinScale?.let {
            val sinDelta = abs(sin(angle1) - sin(angle2)) / 2.0
            distance *= sinDelta * (it - 1.0) + 1.0
        }
        return distance
    }

    fun split2PI(angle0: Double, splitCount: Int): List<Double> {
        val pi2 = 2.0 * PI
        return (0 until splitCount).fold(ArrayList()) { acc, item ->
            acc.add(modPositive(angle0 + pi2 * item / splitCount, pi2))
            acc
        }
    }

}