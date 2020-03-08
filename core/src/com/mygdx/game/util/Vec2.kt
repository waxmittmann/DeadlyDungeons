package com.mygdx.game.util

import kotlin.random.Random

//class Vec2(_x: Int, _y: Int) : Single2(_x, _y)

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
}