package com.mygdx.game.util.geometry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Vec2Test {

    @Test
    fun rotate0() {
        val result = Vec2.create(0, 1).rotate(Angle(0f))
        assertEquals(Vec2.create(0, 1), result)
    }

    @Test
    fun rotate90() {
        val result = Vec2.create(0, 1).rotate(Angle(90f))
        assertEquals(Vec2.create(1, 0), result)
    }

    @Test
    fun rotate180() {
        val result = Vec2.create(0, 1).rotate(Angle(180f))
        assertEquals(Vec2.create(0, -1), result)
    }

    @Test
    fun rotate270() {
        val result = Vec2.create(0, 1).rotate(Angle(270f))
        assertEquals(Vec2.create(-1, 0), result)
    }

    @Test
    fun rotate360() {
        val result = Vec2.create(0, 1).rotate(Angle(360f))
        assertEquals(Vec2.create(0, 1), result)
    }

}