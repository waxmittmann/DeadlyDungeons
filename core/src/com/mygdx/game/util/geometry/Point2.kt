package com.mygdx.game.util.geometry

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import kotlin.random.Random

class Point2(val x: Double, val y: Double) {
    companion object Factory {
        fun create(x: Int, y: Int): Point2 = Point2(x.toDouble(), y.toDouble())

        fun random(ux: Int, uy: Int): Point2 =
                create(Random.nextInt(0, ux), Random.nextInt(0, uy))
    }

    fun minus(point: Vec2): Point2 {
        return Point2(x - point.x, y - point.y)
    }

    fun plus(point: Vec2): Point2 {
        return Point2(x + point.x, y + point.y)
    }

    fun asVector(): Vec2 {
        return Vec2(x, y)
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    fun div(d: Double): Point2 = Point2(x / d, y / d)
    fun mul(xMul: Double, yMul: Double): Point2 = Point2(x * xMul, y * yMul)
    fun asGdxVector(): Vector3 = Vector3(x.toFloat(), y.toFloat(), 0f)
    fun asGdxVector2(): Vector2 = Vector2(x.toFloat(), y.toFloat())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point2

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}