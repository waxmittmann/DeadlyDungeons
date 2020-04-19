package com.mygdx.game.util.geometry

import arrow.core.k


class PolygonBuilder(firstPoint: Point2) {
    private val points: MutableList<Point2> = mutableListOf(firstPoint)

    fun plus(x: Double, y: Double): PolygonBuilder = plus(Point2(x, y))

    fun plus(point: Point2): PolygonBuilder {
        points.add(point)
        return this
    }

    fun moveX(x: Double): PolygonBuilder = move(x, 0.0);

    fun moveY(y: Double): PolygonBuilder = move(0.0, y);

    fun move(x: Int, y: Int): PolygonBuilder = move(x.toDouble(), y.toDouble())

    fun move(x: Double, y: Double): PolygonBuilder {
        points.add(Point2(points.last().x + x, points.last().y + y))
        return this
    }

    fun build(): Polygon2 {
        return Polygon2(points)
    }

    fun clear(firstPoint: Point2): PolygonBuilder {
        points.clear()
        points.add(firstPoint)
        return this
    }
}

class Polygon2(val vertices: List<Point2>) {
    val edges: List<Vec2>
    val axes: List<Vec2>
    val vertexSet: Set<Point2>

    init {
        edges = mutableListOf()
        for (i in vertices.indices) {
            edges += Vec2(vertices[(i + 1) % vertices.size].x - vertices[i].x,
                    vertices[(i + 1) % vertices.size].y - vertices[i].y)
        }

        axes = edges.k().map { it.perpendicular() }
        vertexSet = vertices.toSet()
    }

    // Don't override equals because the polygons could have different internal
    // representations and that's not traditionally 'equals'.
    // Could be made faster by caching a hash value of the set.
    fun samePolygon(other: Polygon2): Boolean = vertexSet == other.vertexSet
}


