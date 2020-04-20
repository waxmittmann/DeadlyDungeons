package com.mygdx.game.util.geometry

class Dims2(val width: Float, val height: Float) {
    val asRect: Rect2 by lazy {
        Rect2(0.0, 0.0, width.toDouble(), height.toDouble())
    }

    override fun toString(): String {
        return "($width, $height)"
    }
}