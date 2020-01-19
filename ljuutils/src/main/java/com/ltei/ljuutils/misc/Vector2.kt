package com.ltei.ljuutils.misc

import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

open class Vector2<T: Number>(
    open val x: T,
    open val y: T
) {

    companion object {
        fun vectorAngle(x: Float, y: Float) = atan2(y, x)
        //Compute the dot product AB . BC
        private fun dotProduct(pointA: Vector2<Float>, pointB: Vector2<Float>, pointC: Vector2<Float>): Float {
            val AB = Vector2(pointB.x - pointA.x, pointB.y - pointA.y)
            val BC = Vector2(pointC.x - pointB.x, pointC.y - pointB.y)
            return AB.x * BC.x + AB.y * BC.y
        }

        //Compute the cross product AB x AC
        private fun crossProduct(pointA: Vector2<Float>, pointB: Vector2<Float>, pointC: Vector2<Float>): Float {
            val abX = pointB.x - pointA.x
            val abY = pointB.y - pointA.y
            val acX = pointC.x - pointA.x
            val acY = pointC.y - pointA.y
            return abX * acY - abY * acX
        }

        //Compute the distance from A to B
        fun distance(pointA: Vector2<Float>, pointB: Vector2<Float>): Float {
            val d1 = pointA.x - pointB.x
            val d2 = pointA.y - pointB.y
            return sqrt(d1 * d1 + d2 * d2)
        }

        //Compute the distance from AB to C
        //if isSegment is true, AB is a segment, not a line.
        fun lineToPointDistance2D(point: Vector2<Float>, segmentA: Vector2<Float>, segmentB: Vector2<Float>, isSegment: Boolean): Float {
            val dist = crossProduct(
                segmentA,
                segmentB,
                point
            ) / distance(segmentA, segmentB)
            if (isSegment) {
                val dot1 = dotProduct(
                    segmentA,
                    segmentB,
                    point
                )
                if (dot1 > 0) return distance(
                    segmentB,
                    point
                )
                val dot2 = dotProduct(
                    segmentB,
                    segmentA,
                    point
                )
                if (dot2 > 0) return distance(
                    segmentA,
                    point
                )
            }
            return abs(dist)
        }
    }

}

open class MutableVector2<T: Number>(
    override var x: T,
    override var y: T
): Vector2<T>(x, y)