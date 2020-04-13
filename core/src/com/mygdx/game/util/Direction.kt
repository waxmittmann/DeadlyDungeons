package com.mygdx.game.util

enum class Direction {
    UP, DOWN, LEFT, RIGHT;
}

object FullDirectionFns {
    private val values = FullDirection.values()

    fun random(): FullDirection {
        return values[globalRandom.nextInt(values.size)]
    }

}

enum class FullDirection {
    SOUTH, SOUTH_WEST, WEST, NORTH_WEST, NORTH, NORTH_EAST, EAST, SOUTH_EAST
}

val S = FullDirection.SOUTH
val W = FullDirection.WEST
val N = FullDirection.NORTH
val E = FullDirection.EAST
val SE = FullDirection.SOUTH_EAST
val NE = FullDirection.NORTH_EAST
val SW = FullDirection.SOUTH_WEST
val NW = FullDirection.NORTH_WEST
