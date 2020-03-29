package com.mygdx.game.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Vec2Test {

    @Test
    fun rotate0() {
        val result = Vec2(0, 1).rotate(Angle(0))
        assertEquals(Vec2(0, 1), result)
    }

    @Test
    fun rotate90() {
        val result = Vec2(0, 1).rotate(Angle(90))
        assertEquals(Vec2(1, 0), result)
    }

    @Test
    fun rotate180() {
        val result = Vec2(0, 1).rotate(Angle(180))
        assertEquals(Vec2(0, -1), result)
    }

    @Test
    fun rotate270() {
        val result = Vec2(0, 1).rotate(Angle(270))
        assertEquals(Vec2(-1, 0), result)
    }

    @Test
    fun rotate360() {
        val result = Vec2(0, 1).rotate(Angle(360))
        assertEquals(Vec2(0, 1), result)
    }

}