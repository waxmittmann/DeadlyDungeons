package com.mygdx.game.util.geometry

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import java.lang.Math.*
import kotlin.math.acos


class Angle internal constructor(val degrees: Int) {
    companion object Factory {
        fun create(degrees: Int): Angle = Angle(0).rotate(degrees)


//        fun create(dirV: Vec2): Angle {
        fun create(p: Point2): Angle {
//            val up = Vec2(0, 1)

            if (p.x == 0.0) {
                if (p.y >= 0) {
                    return create(0)
                } else {
                    return create(180)
                }
            }

            val up = Vector2D(0.0, 1.0)
            val dir = Vector2D(p.x.toDouble(), p.y.toDouble())



            val radianAngle = Vector2D.angle(up, dir)
//    val radianAngle = Vector2D.angle(dir, up)
    val degreeAngle = toDegrees(radianAngle)

            if (p.x >= 0)
               return create(degreeAngle.toInt())
            else
                return create(-degreeAngle.toInt())


//            return create(Vector2D.angle(up, dir))
        }

        fun create2(up: Vec2, dir: Vec2): Angle {
            val abBar = (up.x * dir.y) + (dir.x * up.y)
            val abMag = kotlin.math.sqrt((up.x * up.x + up.y * up.y).toDouble()) * kotlin.math.sqrt((dir.x * dir.x + dir.y * dir.y).toDouble())

            val a = abBar.toDouble() / abMag
//            println("A " + a)
//            println("Ac " + cos(a))
            val acos = acos(abBar.toDouble() / abMag)

            return create((acos / PI * 180.0f).toInt())
        }

//        fun create(center: Point2, right: Point2, dir: Point2): Angle {
//            var offset90 = false
//            var dirO = dir
//            if (center.x == dir.x) {
//
//                offset90 = true
//
//                if (dir.y - center.y >= 0) {
//                    dirO = Point2( ,dir.y)
//                } else {
//                    dirO = Point2( ,dir.y)
//                }
//
//
////                return if (dir.y - center.y >= 0)
////                    create(0)
////                else
////                    create(180)
//            }
//
//            val m2 = (dir.y - center.y) / (dir.x - center.x)
//            val m1 = (right.y - center.y) / (right.x - center.x)
//
//            val num = m2 - m1
//            val den = 1 + m1 * m2
//
//            return create(((atan(num.toDouble()/den) - 90) / Math.PI * 180.0f).toInt())
//        }
//    }

//        fun create(point: Point2): Angle {
//            var pointO = point
//            if (point.x == 0) {
//                return if (point.y == 0) {
//                    create(0)
//                } else if (point.y >= 0) {
//                    create(0)
//                } else {
//                    create(180)
//                }
//            }
//
//            val m2 = pointO.y.toDouble() / pointO.x
//
////            val den = 1 + m2
////            val den = 1
//
////            val amt = atan(m2 /den)
//            val amt = atan(m2)
//
//            println("Amount: $amt")
//
//            return create(((amt) / Math.PI * 180.0f).toInt() - 90)
////            return create(((amt) / Math.PI * 180.0f).toInt())
//        }
    }


    fun rotate(degreesBy: Int): Angle {
        var degreesNew = degrees + degreesBy
        if (degreesNew < 0)
            degreesNew = 360 - (-degreesNew % 360)
        else if (degreesNew > 360)
            degreesNew %= 360
        return Angle(degreesNew)
    }

    override fun toString(): String = "Angle $degrees"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Angle

        if (degrees != other.degrees) return false

        return true
    }

    override fun hashCode(): Int {
        return degrees
    }
}