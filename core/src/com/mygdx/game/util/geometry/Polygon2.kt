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

class Polygon2_4(val p1: Point2, val p2: Point2, val p3: Point2,
        val p4: Point2) : Polygon2(listOf(p1, p2, p3, p4)) {}

open class Polygon2(val vertices: List<Point2>) {

    companion object Factory {
        fun _4(p1: Point2, p2: Point2, p3: Point2, p4: Point2): Polygon2_4 {
            return Polygon2_4(p1, p2, p3, p4)
        }
    }

    val asAabb: Rect2 by lazy {
        Rect2.aabb(vertices)
    }

    val edges: List<Vec2> by lazy {
        val edges = mutableListOf<Vec2>()
        for (i in vertices.indices) {
            edges += Vec2(vertices[(i + 1) % vertices.size].x - vertices[i].x,
                    vertices[(i + 1) % vertices.size].y - vertices[i].y)
        }
        edges
    }

    val axes: List<Vec2> by lazy { edges.k().map { it.perpendicular() } }

    private val vertexSet: Set<Point2> by lazy { vertices.toSet() }

    // Don't override equals because the polygons could have different internal
    // representations and that's not traditionally 'equals'.
    // Could be made faster by caching a hash value of the set.
    fun samePolygon(other: Polygon2): Boolean = vertexSet == other.vertexSet

    override fun toString(): String {
        return "(${vertices.map { it.toString() }.joinToString()})"
    }
}

