package com.mygdx.game.util


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
    fun pointToIndex(point2: Point2): Pair<Int, Int> {
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
        return Pair(x, y)
    }

    fun rectToIndex(rect: Rect2): Indices {
        val l = pointToIndex(rect.lowerLeft())
        val u = pointToIndex(rect.upperRight().minus(Vec2(1, 1)))
        return Indices(l.first .. u.first, l.second .. u.second)
    }
}

fun <S> slice(indices: Indices, terrain: List<List<S>>): List<List<S>> {
    return terrain.slice(IntRange(indices.yr.first, indices.yr.last)).map { a -> a.slice(IntRange(indices.xr.first, indices.xr.last)) }
}

