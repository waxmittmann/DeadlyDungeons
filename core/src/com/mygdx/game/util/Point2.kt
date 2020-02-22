package com.mygdx.game.util

import kotlin.random.Random

class Point2(val x: Int, val y: Int) {

    companion object Factory {
        fun random(ux: Int, uy: Int): Point2 =
                Point2(Random.nextInt(0, ux), Random.nextInt(0, uy))
    }

    fun minus(point: Point2): Point2 {
        return Point2(x - point.x, y - point.y)
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    fun plus(point: Point2): Point2 {
        return Point2(x + point.x, y + point.y)
    }
}