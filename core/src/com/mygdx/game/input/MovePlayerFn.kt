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

fun <S> movePlayer(player: Rect2, terrainSize: Int, terrain: List<List<S>>, accessFn: (S) -> (Boolean), amount: Int, dir: Direction): Vec2 {
    return when (dir) {
        Direction.UP ->
            Vec2(0.0, movePlayer(player, terrainSize.toDouble(), terrain, accessFn, upFn, amount))

        Direction.DOWN ->
            Vec2(0.0, -movePlayer(player, terrainSize.toDouble(), terrain, accessFn, downFn, amount))

        Direction.LEFT ->
            Vec2(-movePlayer(player, terrainSize.toDouble(), terrain, accessFn, leftFn, amount), 0.0)

        Direction.RIGHT ->
           Vec2(movePlayer(player, terrainSize.toDouble(), terrain, accessFn, rightFn, amount), 0.0)
    }
}

fun <S> movePlayer(player: Rect2, terrainSize: Double, terrain: List<List<S>>, accessFn: (S) -> (Boolean),
                   calcNewAmountFn: (Double, Double, Rect2, Double) -> Pair<Rect2, Double>, amount: Int): Double {
    val utils = Utils(50)
    var curAmount = 0.0
    while (curAmount < amount) {
        debugln("At $curAmount")
        val (newPlayerRect, newAmount) = calcNewAmountFn(curAmount, amount.toDouble(), player, terrainSize)
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

val upFn: (Double, Double, Rect2, Double) -> Pair<Rect2, Double> = { curAmount: Double, maxAmount: Double, player: Rect2, tileSize: Double->
    val amountFromStart = (player.uy() + curAmount) % tileSize
    var newAmount = tileSize - amountFromStart
    newAmount = min(maxAmount.toDouble(), curAmount + newAmount)
    val newPlayerRect = player.plus(Vec2(0.0, newAmount))
    debugln("$amountFromStart, $newAmount")
    Pair(newPlayerRect, newAmount)
}

val downFn: (Double, Double, Rect2, Double) -> Pair<Rect2, Double> = { curAmount: Double, maxAmount: Double, player: Rect2, tileSize: Double ->
    val amountFromStart = tileSize - (player.ly - curAmount) % tileSize
    debugln("(${player.ly} - $curAmount) % $tileSize")
    val newAmount = min(maxAmount.toDouble(), curAmount + amountFromStart)
    val newPlayerRect = player.minus(Vec2(0.0, newAmount))
    debugln("$amountFromStart, $newAmount")
    Pair(newPlayerRect, newAmount)
}

val rightFn: (Double, Double, Rect2, Double) -> Pair<Rect2, Double> = { curAmount: Double, maxAmount: Double, player: Rect2, tileSize: Double ->
    val amountFromStart = (player.ux() + curAmount) % tileSize
    var newAmount = tileSize - amountFromStart
    newAmount = min(maxAmount.toDouble(), curAmount + newAmount)
    val newPlayerRect = player.plus(Vec2( newAmount, 0.0))
    debugln("$amountFromStart, $newAmount")
    Pair(newPlayerRect, newAmount)
}

val leftFn: (Double, Double, Rect2, Double) -> Pair<Rect2, Double> = { curAmount: Double, maxAmount: Double, player: Rect2, tileSize: Double ->
    val amountFromStart = tileSize - (player.lx  - curAmount) % tileSize
    debugln("(${player.lx} - $curAmount) % $tileSize")
    val newAmount = min(maxAmount.toDouble(), curAmount + amountFromStart)
    val newPlayerRect = player.minus(Vec2(newAmount, 0.0))
    debugln("$amountFromStart, $newAmount")
    Pair(newPlayerRect, newAmount)
}

