package com.mygdx.game.util

import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2


class Indices(val xr: IntRange, val yr: IntRange) {
    override fun toString(): String {
        return "([$xr], [$yr])";
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Indices

        if (xr != other.xr) return false
        if (yr != other.yr) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xr.hashCode()
        result = 31 * result + yr.hashCode()
        return result
    }
}

class Utils(private val tileSize: Int) {
    fun pointToIndex(point2: Point2, floor: Boolean): Pair<Int, Int> {
        val x = if (point2.x >= 0) {
            point2.x / tileSize
        } else {
            (point2.x - tileSize + 1) / tileSize
        }

        val y = if (point2.y >= 0) {
            point2.y / tileSize
        } else {
            (point2.y - tileSize + 1) / tileSize
        }
        return if (floor) Pair(floor(x + 0.005), floor(y + 0.005)) else Pair(
                ceil(x - 0.005), ceil(y - 0.005))
    }

    fun rectToIndex(rect: Rect2): Indices {
        val l = pointToIndex(rect.lowerLeft(), floor = true)
//        val u = pointToIndex(rect.upperRight().minus(Vec2(1.0, 1.0)), floor = false)
//        val l = pointToIndex(rect.lowerLeft(), floor =false)
        val u = pointToIndex(rect.upperRight().minus(Vec2(1.0, 1.0)),
                floor = true)
        return Indices(l.first..u.first, l.second..u.second)
    }
}

fun <S> slice(indices: Indices, terrain: List<List<S>>): List<List<S>> {
    return terrain.slice(IntRange(indices.yr.first, indices.yr.last))
            .map { a -> a.slice(IntRange(indices.xr.first, indices.xr.last)) }
}

fun floor(v: Double): Int = Math.floor(v).toInt()

fun ceil(v: Double): Int = Math.ceil(v).toInt()


