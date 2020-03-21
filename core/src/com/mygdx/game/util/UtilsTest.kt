package com.mygdx.game.util

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class UtilsTest {

    @Test
    fun singlePoint() {
        val utils = Utils(10)
        val result = utils.pointToIndex(Point2(0, 0))
        assertEquals(Pair(0, 0), result)
    }

    @Test
    fun singlePointAtEdge() {
        val utils = Utils(10)
        val result = utils.pointToIndex(Point2(9, 9))
        assertEquals(Pair(0, 0), result)
    }

    @Test
    fun singlePointNextSquare() {
        val utils = Utils(10)
        val result = utils.pointToIndex(Point2(10, 10))
        assertEquals(Pair(1, 1), result)
    }

    @Test
    fun singlePointNextSquareAtEdge() {
        val utils = Utils(10)
        val result = utils.pointToIndex(Point2(19, 19))
        assertEquals(Pair(1, 1), result)
    }

    @Test
    fun rectToIndexZeroSquare() {
        val utils = Utils(10)
        val result = utils.rectToIndex(Rect2.fromLowerUpper(0, 0, 10, 10)!!)
        assertEquals(Indices(0 .. 0, 0 .. 0), result)
    }

    @Test
    fun rectToIndex2OneSquare() {
        val utils = Utils(10)
        val result = utils.rectToIndex(Rect2.fromLowerUpper(10, 10, 20, 20)!!)
        assertEquals(Indices(1 .. 1, 1 .. 1), result)
    }

    @Test
    fun rectToIndexTwoWidthSquares() {
        val utils = Utils(10)
        val result = utils.rectToIndex(Rect2.fromLowerUpper(0, 0, 11, 10)!!)
        assertEquals(Indices(0 .. 1, 0 .. 0), result)
    }

    @Test
    fun rectToIndexTwoHeightSquares() {
        val utils = Utils(10)
        val result = utils.rectToIndex(Rect2.fromLowerUpper(0, 0, 10, 11)!!)
        assertEquals(Indices(0 .. 0, 0 .. 1), result)
    }
}