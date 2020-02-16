package com.mygdx.game.util

class Rect2(val lx: Int, val ly: Int, val width: Int, val height: Int) {
    fun ux(): Int = lx + width
    fun uy(): Int = ly + height

    fun lowerLeft(): Point2 {
        return Point2(lx, ly)
    }

    companion object Factory {
        fun FromLowerUpper(lx: Int, ly: Int, ux: Int, uy: Int): Rect2? {
            return if (lx >= ux || ly >= uy)
                null
            else
                Rect2(lx, ly, ux - lx, uy - ly)
        }
    }
}
