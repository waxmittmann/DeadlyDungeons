package com.mygdx.game.util

enum class FourDirection {
    UP, DOWN, LEFT, RIGHT;
}

object EightDirectionFns {
    private val values: Array<EightDirection> = EightDirection.values()

    fun random(): EightDirection {
        return values[globalRandom.nextInt(values.size)]
    }
}

enum class EightDirection {
    SOUTH, SOUTH_WEST, WEST, NORTH_WEST, NORTH, NORTH_EAST, EAST, SOUTH_EAST
}

val S = EightDirection.SOUTH
val W = EightDirection.WEST
val N = EightDirection.NORTH
val E = EightDirection.EAST
val SE = EightDirection.SOUTH_EAST
val NE = EightDirection.NORTH_EAST
val SW = EightDirection.SOUTH_WEST
val NW = EightDirection.NORTH_WEST
