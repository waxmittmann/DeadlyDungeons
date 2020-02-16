package com.mygdx.game.util

class Point2(val x: Int, val y: Int) {
    fun minus(point: Point2): Point2 {
        return Point2(x - point.x, y - point.y)
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}