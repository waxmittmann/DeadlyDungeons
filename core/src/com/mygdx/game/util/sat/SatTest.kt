package com.mygdx.game.util.sat

import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.PolygonBuilder
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


// @formatter:off
internal class SatTest {

    // This test fails for plain sat if not using >=/<=, but if we do use
    // >=/<= then we are forcing 'touching' to be 'overlapping'.
    // Instead, have added a 'is-same-polygon' condition test in sat, based
    // on two polys having exactly the same points.
    @Test
    fun selfOverlap() {
        val poly = PolygonBuilder(Point2.create(0, 0))
                .move(1, 0)
                .move(0, 1)
                .move(-1, 0).build()

        assertTrue(sat(poly, poly))
    }

    @Test
    fun simpleOverlap() {
        val poly1 = PolygonBuilder(Point2.create(0, 0))
                .move(1, 0)
                .move(0, 1)
                .move(-1, 0)
                .build()

        val poly2 = PolygonBuilder(Point2(0.5, 0.5))
                .move(1, 0)
                .move(0, 1)
                .move(-1, 0)
                .build()

        assertTrue(sat(poly1, poly2))
    }


    @Test
    fun containsOverlap() {
        val poly1 = PolygonBuilder(Point2.create(0, 0))
                .move(1, 0)
                .move(0, 1)
                .move(-1, 0).build()

        val poly2 = PolygonBuilder(Point2(0.25, 0.25))
                .move(0.5, 0.0)
                .move(0.0, 0.5)
                .move(-0.5, 0.0).build()

        assertTrue(sat(poly1, poly2))
        assertTrue(sat(poly2, poly1))
    }

    @Test
    fun simpleNonOverlap() {
        val poly1 = PolygonBuilder(Point2.create(0, 0))
                .move(1, 0)
                .move(0, 1)
                .move(-1, 0)
                .build()

        val poly2 = PolygonBuilder(Point2(1.0, 1.0))
                .move(1, 0)
                .move(0, 1)
                .move(-1, 0)
                .build()

        assertFalse(sat(poly1, poly2))
    }

    @Test
    fun diamondsOverlap() {
        val central = PolygonBuilder(Point2.create(0, 0))
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(-0.5, -0.5)
                .build()

        val ul= PolygonBuilder(Point2(-0.4, 0.5))
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(-0.5, -0.5)
                .build()

        val ur= PolygonBuilder(Point2(0.5, 0.4))
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(-0.5, -0.5)
                .build()

        val ll= PolygonBuilder(Point2(-0.5, -0.4))
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(-0.5, -0.5)
                .build()

        val lr= PolygonBuilder(Point2(0.4, -0.5))
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(-0.5, -0.5)
                .build()

        for (other in listOf(ul, ur, ll, lr)) {
            assertTrue(sat(central, other))
            assertTrue(sat(other, central))
        }
    }

    @Test
    fun diamondsNonOverlap() {
        val central = PolygonBuilder(Point2.create(0, 0))
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(-0.5, -0.5)
                .build()

        val ul= PolygonBuilder(Point2(-0.5, 0.5))
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(-0.5, -0.5)
                .build()

        val ur= PolygonBuilder(Point2(0.5, 0.5))
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(-0.5, -0.5)
                .build()

        val ll= PolygonBuilder(Point2(-0.5, -0.5))
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(-0.5, -0.5)
                .build()

        val lr= PolygonBuilder(Point2(0.5, -0.5))
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(-0.5, -0.5)
                .build()

        for (other in listOf(ul, ur, ll, lr)) {
            assertFalse(sat(central, other))
            assertFalse(sat(other, central))
        }
    }

    @Test
    fun complexPolygonsNonOverlap() {
        val lhs= PolygonBuilder(Point2.create(0, 0))
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .build()

        val rhs= PolygonBuilder(Point2(0.5, 0.5))
                .move(-0.5, 0.5)
                .move(0.5, 0.5)
                .move(-0.5, 0.5)
                .move(0.5, 0.5)
                .build()

        assertFalse(sat(lhs, rhs))
        assertFalse(sat(rhs, lhs))
    }


    // Leaving this for now to show what NOT to do. These are concave polygons,
    // so SAT won't work (they look like interlocking saw teeth).
//    @Test
//    fun complexPolygonsOverlap() {
//        val lhs= PolygonBuilder(Point2.create(0, 0))
//                .move(0.5, 0.5)
//                .move(-0.4, 0.5)
//                .move(0.4, 0.5)
//                .move(-0.5, 0.5)
//                .move(0.5, 0.5)
//                .move(-0.5, 0.5)
//                .build()
//
//        val rhs= PolygonBuilder(Point2(0.5, 0.5))
//                .move(-0.5, 0.5)
//                .move(0.5, 0.5)
//                .move(-0.5, 0.5)
//                .move(0.5, 0.5)
//                .build()
//
//        assertTrue(sat(lhs, rhs))
//        assertTrue(sat(rhs, lhs))
//    }
}
// @formatter:on
