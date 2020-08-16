package com.mygdx.game.util.geometry

interface HasDims2 {
    fun dims(): Dims2
}

interface MaybeHasDims2 {
    fun dims(): Dims2?
}

class Dims2(val width: Float, val height: Float) {
    val asRect: Rect2 by lazy {
        Rect2(0.0, 0.0, width.toDouble(), height.toDouble())
    }

    override fun toString(): String {
        return "($width, $height)"
    }

    fun div(d: Float): Dims2 = Dims2(width / d, height / d)

    fun asVector(): Vec2 = Vec2(width.toDouble(), height.toDouble())
}