package com.mygdx.game.util

class Rect2(val lx: Int, val ly: Int, val width: Int, val height: Int) {
    fun ux(): Int = lx + width
    fun uy(): Int = ly + height

    fun lowerLeft(): Point2 {
        return Point2(lx, ly)
    }

    fun plus(vec2: Vec2): Rect2 {
        return Rect2(lx + vec2.x, ly + vec2.y, width, height)
    }

    fun minus(vec2: Vec2): Rect2 {
        return Rect2(lx - vec2.x, ly - vec2.y, width, height)
    }

    override fun toString(): String {
        return "($lx, $ly, " + (lx + width) + ", " + (ly + height) + ")"
    }

    fun moduloX(modX: Int): Rect2 {
        assert(modX != 0)
        return Rect2((lx % modX) * modX, ly, width, height)
    }

    fun moduloY(modY: Int): Rect2 {
        assert(modY != 0)
        return Rect2(lx, (ly % modY) * modY, width, height)
    }

    fun add(_x: Int, _y: Int): Rect2 {
        return Rect2(lx + _x, ly + _y, width, height)
    }

    fun shrink(xs: Int, ys: Int): Rect2 {
        return Rect2(lx + 1, ly + 1, width - 1, height - 1)
    }

    fun upperRight(): Point2 {
        return Point2(lx + width, ly + height)
    }

    fun overlaps(other: Rect2): Boolean {
        if (lx > other.ux())
            return false
        if (other.lx > ux())
            return false
        if (ly > other.uy())
            return false
        if (other.uy() > uy())
            return false
        return true
    }

    companion object Factory {
        fun fromUpperRight(ux: Int, uy: Int, width: Int, height: Int): Rect2 {
            return Rect2(ux - width, uy - height, width, height)
        }

        fun fromLowerUpper(lx: Int, ly: Int, ux: Int, uy: Int): Rect2? {
            return if (lx >= ux || ly >= uy)
                null
            else
                Rect2(lx, ly, ux - lx, uy - ly)
        }

        fun fromPoints(ll: Point2, ur: Point2) {
            assert(ll.x <= ur.x)
            assert(ll.y <= ur.y)
            Rect2(ll.x, ll.y, ur.x, ur.y)
        }
    }
}
