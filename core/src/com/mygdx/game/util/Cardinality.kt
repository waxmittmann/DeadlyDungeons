package com.mygdx.game.util

enum class Cardinality {
    UP, DOWN, LEFT, RIGHT;

    operator fun invoke(function: () -> Unit) {

    }
}