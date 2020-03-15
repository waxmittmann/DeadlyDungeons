package com.mygdx.game.input

import com.mygdx.game.util.*
import kotlin.math.min

val debug = false
fun debugln(str: String) {
    if (debug)
        println(str)
}
fun debugS(str: String) {
    if (debug)
        print(str)
}

fun <S> movePlayer(player: Rect2, terrainSize: Int, terrain: List<List<S>>, accessFn: (S) -> (Boolean), amount: Int, dir: Cardinality): Vec2 {
    return when (dir) {
        Cardinality.UP ->
            Vec2(0, movePlayer(player, terrainSize, terrain, accessFn, upFn, amount))

        Cardinality.DOWN ->
            Vec2(0, -movePlayer(player, terrainSize, terrain, accessFn, downFn, amount))

        Cardinality.LEFT ->
            Vec2(-movePlayer(player, terrainSize, terrain, accessFn, leftFn, amount), 0)

        Cardinality.RIGHT ->
           Vec2(movePlayer(player, terrainSize, terrain, accessFn, rightFn, amount), 0)
    }
}

val upFn: (Int, Int, Rect2, Int) -> Pair<Rect2, Int> = { curAmount: Int, maxAmount: Int, player: Rect2, tileSize: Int ->
    val amountFromStart = (player.uy() + curAmount) % tileSize
    var newAmount = tileSize - amountFromStart
    newAmount = min(maxAmount, curAmount + newAmount)
    val newPlayerRect = player.plus(Vec2(0, newAmount))
    debugln("$amountFromStart, $newAmount")
    Pair(newPlayerRect, newAmount)
}

val downFn: (Int, Int, Rect2, Int) -> Pair<Rect2, Int> = { curAmount: Int, maxAmount: Int, player: Rect2, tileSize: Int ->
    val amountFromStart = tileSize - (player.ly - curAmount) % tileSize
    debugln("(${player.ly} - $curAmount) % $tileSize")
    val newAmount = min(maxAmount, curAmount + amountFromStart)
    val newPlayerRect = player.minus(Vec2(0, newAmount))
    debugln("$amountFromStart, $newAmount")
    Pair(newPlayerRect, newAmount)
}

val rightFn: (Int, Int, Rect2, Int) -> Pair<Rect2, Int> = { curAmount: Int, maxAmount: Int, player: Rect2, tileSize: Int ->
    val amountFromStart = (player.ux() + curAmount) % tileSize
    var newAmount = tileSize - amountFromStart
    newAmount = min(maxAmount, curAmount + newAmount)
    val newPlayerRect = player.plus(Vec2( newAmount, 0))
    debugln("$amountFromStart, $newAmount")
    Pair(newPlayerRect, newAmount)
}

val leftFn: (Int, Int, Rect2, Int) -> Pair<Rect2, Int> = { curAmount: Int, maxAmount: Int, player: Rect2, tileSize: Int ->
    val amountFromStart = tileSize - (player.lx  - curAmount) % tileSize
    debugln("(${player.lx} - $curAmount) % $tileSize")
    val newAmount = min(maxAmount, curAmount + amountFromStart)
    val newPlayerRect = player.minus(Vec2(newAmount, 0))
    debugln("$amountFromStart, $newAmount")
    Pair(newPlayerRect, newAmount)
}

fun <S> movePlayer(player: Rect2, terrainSize: Int, terrain: List<List<S>>, accessFn: (S) -> (Boolean),
                   calcNewAmountFn: (Int, Int, Rect2, Int) -> Pair<Rect2, Int>, amount: Int): Int {
    val utils = Utils(50)
    var curAmount = 0
    while (curAmount < amount) {
        debugln("At $curAmount")
        val (newPlayerRect, newAmount) = calcNewAmountFn(curAmount, amount, player, terrainSize)
        val indices = utils.rectToIndex(newPlayerRect)

        debugS("NewRect: $newPlayerRect\nNewAmount: $newAmount\nIndices: $indices\n")

        if (indices.xr.first < 0 || indices.yr.first < 0 || indices.xr.last >= terrain[0].size || indices.yr.last >= terrain.size) {
            debugln("Outside range")
            break
        }

        val slicedTerrain = slice(indices, terrain)
        val accessible = slicedTerrain.flatten().all { s: S -> accessFn(s) }

        debugln("Sliced:\n$slicedTerrain")

        if (!accessible) {
            debugln("Not accessible")
            break
        } else {
            debugln("Continuing")
            curAmount = newAmount
        }
    }

    return curAmount
}
