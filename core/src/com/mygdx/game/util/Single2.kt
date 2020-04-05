package com.mygdx.game.util

import kotlin.random.Random

open class Single2(val x: Int, val y: Int) {
    companion object Factory {
        fun random(ux: Int, uy: Int): Point2 = Point2.create(Random.nextInt(0, ux), Random.nextInt(0, uy))
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    fun minus(point: Point2): Point2 {
        return Point2(x - point.x, y - point.y)
    }

    fun plus(point: Point2): Point2 {
        return Point2(x + point.x, y + point.y)
    }
}

