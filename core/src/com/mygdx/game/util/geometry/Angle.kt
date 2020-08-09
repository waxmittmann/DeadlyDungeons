package com.mygdx.game.util.geometry

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import java.lang.Math.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin


class Angle internal constructor(val degrees: Float) {
    val radians: Double = Math.toRadians(degrees.toDouble())

    companion object Factory {
        fun create(degrees: Float): Angle = Angle(0f).rotate(degrees)


        fun create(p: Point2): Angle {
            if (p.x == 0.0) {
                if (p.y >= 0) {
                    return create(0f)
                } else {
                    return create(180f)
                }
            }

            val up = Vector2D(0.0, 1.0)
            val dir = Vector2D(p.x.toDouble(), p.y.toDouble())


            val radianAngle = Vector2D.angle(up, dir)
            val degreeAngle = toDegrees(radianAngle)

            return if (p.x >= 0) create(degreeAngle.toFloat())
            else create(-degreeAngle.toFloat())
        }

        fun create(up: Vec2, dir: Vec2): Angle {
            val abBar = (up.x * dir.y) + (dir.x * up.y)
            val abMag = kotlin.math.sqrt((up.x * up.x + up.y * up.y)) * kotlin.math.sqrt(
                    (dir.x * dir.x + dir.y * dir.y))

            val acos = acos(abBar / abMag)

            return create((acos / PI * 180.0f).toFloat())
        }
    }

    fun rotate(degreesBy: Float): Angle {
        var degreesNew = degrees + degreesBy
        if (degreesNew < 0) degreesNew = 360 - (-degreesNew % 360)
        else if (degreesNew > 360) degreesNew %= 360
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
        return (degrees * 1000000).toInt()
    }

    fun transform(c: Point2, p: Point2): Point2 {
        val xo = (p.x - c.x)
        val yo = (p.y - c.y)

        val xn = xo * cos(radians) - yo * sin(radians)
        val yn = yo * cos(radians) + xo * sin(radians)

        return Point2(xn + c.x, yn + c.y)
    }

    fun invert(): Angle {
        val a = Angle.create(-degrees)
        return a
    }
}