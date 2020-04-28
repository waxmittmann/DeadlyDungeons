package com.mygdx.game.util

import com.mygdx.game.Main

val sum: Int.(Int) -> Int = { other -> plus(other) }

val sum2 = fun Int.(other: Int): Int = this + other


fun main() {
    println(sum(1, 2))
    println(sum2(1, 2))

}
