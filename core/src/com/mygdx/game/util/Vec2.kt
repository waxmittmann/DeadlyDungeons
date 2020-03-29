package com.mygdx.game.util

import com.badlogic.gdx.math.Vector2
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class Vec2(val x: Int, val y: Int) {

    companion object Factory {
        fun random(ux: Int, uy: Int): Vec2 =
                Vec2(Random.nextInt(0, ux), Random.nextInt(0, uy))
    }

    fun minus(point: Point2): Vec2 {
        return Vec2(x - point.x, y - point.y)
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    fun plus(point: Vec2): Vec2 {
        return Vec2(x + point.x, y + point.y)
    }

    fun plus(_x: Int, _y: Int): Vec2 {
        return Vec2(x + _x, y + _y)
    }

    fun rotate(angle: Angle, clockwise: Boolean = true): Vec2 {
        val degrees = if (clockwise) Math.toRadians(360 - angle.degrees.toDouble()) else Math.toRadians(angle.degrees.toDouble())

        val _x = cos(degrees) * x - sin(degrees) * y
        val _y = sin(degrees) * x + cos(degrees) * y

        return Vec2(_x.toInt(), _y.toInt())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vec2

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    fun toVector2D(): Vector2D {
        return Vector2D(x.toDouble(), y.toDouble())
    }
}