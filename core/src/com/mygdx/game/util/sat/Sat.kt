// See http://www.dyn4j.org/2010/01/sat/
package com.mygdx.game.util.sat

import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Polygon2
import com.mygdx.game.util.geometry.Vec2

class Projection(val min: Double, val max: Double)

fun sat(poly1: Polygon2, poly2: Polygon2,
        touchIsIntersect: Boolean = false): Boolean {
    if (poly1.samePolygon(poly2)) return true

    return (poly1.axes + poly2.axes).all { axis ->
        val p1: Projection = project(poly1, axis)
        val p2: Projection = project(poly2, axis)
        overlap(p1, p2, touchIsIntersect)
    }
}

// I assume that using >= and <= would mean they count as overlapping
// when they touch. This could be an input param to toggle.
private fun overlap(p1: Projection, p2: Projection,
                    touchIsIntersect: Boolean): Boolean =
        if (touchIsIntersect) (p1.min <= p2.min && p1.max >= p2.min) || (p2.min <= p1.min && p2.max >= p2.min)
        else (p1.min < p2.min && p1.max > p2.min) || (p2.min < p1.min && p2.max > p2.min)

private fun project(poly1: Polygon2, axis: Vec2): Projection {
    var min = dotProduct(axis, poly1.vertices[0])
    var max = min

    for (vertex in poly1.vertices) {
        val dp = dotProduct(axis, vertex)
        if (dp < min) min = dp
        if (dp > max) max = dp
    }

    return Projection(min, max)
}

private fun dotProduct(vec: Vec2, point: Point2): Double {
    return vec.x * point.x + vec.y * point.y
}
