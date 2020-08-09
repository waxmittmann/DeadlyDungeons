package com.mygdx.game.util.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AngleTest {

    @Test
    fun at0_0() {
        val angle = Angle.create(Point2.create(0, 0))

        assertEquals(Angle(0f), angle)
    }

    @Test
    fun at0_1() {
        val angle = Angle.create(Point2.create(0, 1))

        assertEquals(Angle(0f), angle)
    }

    @Test
    fun at1_0() {
        val angle = Angle.create(Point2.create(1, 0))

        assertEquals(Angle(90f), angle)
    }

    @Test
    fun at_minus_1_0() {
        val angle = Angle.create(Point2.create(-1, 0))

        assertEquals(Angle(270f), angle)
    }

    @Test
    fun at_1_1() {
        val angle = Angle.create(Point2.create(1, 1))

        assertEquals(Angle(45f), angle)
    }


    @Test
    fun at_minus_1_minus_1() {
        val angle = Angle.create(Point2.create(-1, -1))

        assertEquals(Angle(225f), angle)
    }

}