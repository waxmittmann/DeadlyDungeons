package com.mygdx.game.util.geometry

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import java.lang.Math.PI
import java.lang.Math.toDegrees
import kotlin.math.acos


class Angle internal constructor(val degrees: Int) {
    val radians: Double = Math.toRadians(degrees.toDouble())

    companion object Factory {
        fun create(degrees: Int): Angle = Angle(0).rotate(degrees)


        fun create(p: Point2): Angle {
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
            val degreeAngle = toDegrees(radianAngle)

            return if (p.x >= 0) create(degreeAngle.toInt())
            else create(-degreeAngle.toInt())
        }

        fun create(up: Vec2, dir: Vec2): Angle {
            val abBar = (up.x * dir.y) + (dir.x * up.y)
            val abMag = kotlin.math.sqrt((up.x * up.x + up.y * up.y)) * kotlin.math.sqrt(
                    (dir.x * dir.x + dir.y * dir.y))

            val acos = acos(abBar / abMag)

            return create((acos / PI * 180.0f).toInt())
        }
    }

    fun rotate(degreesBy: Int): Angle {
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
        return degrees
    }
}