package com.mygdx.game.input

import arrow.core.extensions.list.foldable.exists
import arrow.core.extensions.list.foldable.foldLeft
import com.mygdx.game.entities.Terrain
import com.mygdx.game.entities.WorldObj
import com.mygdx.game.util.*
import kotlin.math.abs
import kotlin.math.min

fun <S> movePlayer(player: Rect2, terrainSize: Int, terrain: List<List<S>>, accessFn: (S) -> (Boolean), amount: Int, dir: Cardinality): Vec2 {
    val utils = Utils(50)
    var curAmount = 0

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

fun toNext(cur: Int, tileSize: Int): Int {
    val amountFromStart = cur % tileSize // 25 % 50 = 25
    return tileSize - amountFromStart // 50 - 25 = 25
}

//fun <S>movePlayer(player: Rect2, terrainSize: Int, terrain: List<List<S>>, accessFn: (S) -> (Boolean), amount: Int, dir: Cardinality): Vec2 {
//    return when(dir) {
//        Cardinality.UP -> Vec2(0, movePlayer(player, terrainSize, terrain, accessFn, amount))
//        Cardinality.DOWN ->  Vec2(0, -movePlayer(player, terrainSize, terrain.reversed(), accessFn, amount))
//        Cardinality.LEFT -> TODO()
//        Cardinality.RIGHT -> TODO()
//    }
//}

//fun <S>movePlayer(player: Rect2, terrainSize: Int, terrain: List<List<S>>, accessFn: (S) -> (Boolean), amount: Int): Int {
//    val utils = Utils(50)
//    var curAmount = 0
//    while (curAmount < amount) {
//        println("At $curAmount")
//        val newAmount = min(amount, curAmount + toNext(player.uy() + curAmount, terrainSize))
//        val newPlayerRect = player.plus(Vec2(0, newAmount))
//        val indices = utils.rectToIndex(newPlayerRect)
//
//        print("NewRect: $newPlayerRect\nNewAmount: $newAmount\nIndices: $indices\n")
//
//        if (indices.yr.last >= terrain.size) {
//            println("Outside range")
//            break
//        }
//
//        val slicedTerrain = slice(indices, terrain)
//        val accessible = slicedTerrain.flatten().all { s: S -> accessFn(s)}
//
//        println("Sliced:\n$slicedTerrain")
//
//        if (!accessible) {
//            println("Not accessible")
//            break
//        } else {
//            println("Continuing")
//            curAmount = newAmount
//        }
//    }
//
//    return curAmount
//}


val upFn: (Int, Int, Rect2, Int) -> Pair<Rect2, Int> = { curAmount: Int, maxAmount: Int, player: Rect2, tileSize: Int ->
    val amountFromStart = (player.uy() + curAmount) % tileSize
    var newAmount = tileSize - amountFromStart
    newAmount = min(maxAmount, curAmount + newAmount)
    val newPlayerRect = player.plus(Vec2(0, newAmount))
    println("$amountFromStart, $newAmount")
    Pair(newPlayerRect, newAmount)
}

val downFn: (Int, Int, Rect2, Int) -> Pair<Rect2, Int> = { curAmount: Int, maxAmount: Int, player: Rect2, tileSize: Int ->
    val amountFromStart = tileSize - (player.ly - curAmount) % tileSize
    println("(${player.ly} - $curAmount) % $tileSize")
    val newAmount = min(maxAmount, curAmount + amountFromStart)
    val newPlayerRect = player.minus(Vec2(0, newAmount))
    println("$amountFromStart, $newAmount")
    Pair(newPlayerRect, newAmount)
}

val rightFn: (Int, Int, Rect2, Int) -> Pair<Rect2, Int> = { curAmount: Int, maxAmount: Int, player: Rect2, tileSize: Int ->
    val amountFromStart = (player.ux() + curAmount) % tileSize
    var newAmount = tileSize - amountFromStart
    newAmount = min(maxAmount, curAmount + newAmount)
    val newPlayerRect = player.plus(Vec2( newAmount, 0))
    println("$amountFromStart, $newAmount")
    Pair(newPlayerRect, newAmount)
}

val leftFn: (Int, Int, Rect2, Int) -> Pair<Rect2, Int> = { curAmount: Int, maxAmount: Int, player: Rect2, tileSize: Int ->
    val amountFromStart = tileSize - (player.lx  - curAmount) % tileSize
    println("(${player.lx} - $curAmount) % $tileSize")
    val newAmount = min(maxAmount, curAmount + amountFromStart)
    val newPlayerRect = player.minus(Vec2(newAmount, 0))
    println("$amountFromStart, $newAmount")
    Pair(newPlayerRect, newAmount)
}

fun <S> movePlayer(player: Rect2, terrainSize: Int, terrain: List<List<S>>, accessFn: (S) -> (Boolean),
                   calcNewAmountFn: (Int, Int, Rect2, Int) -> Pair<Rect2, Int>, amount: Int): Int {
    val utils = Utils(50)
    var curAmount = 0
    while (curAmount < amount) {
        println("At $curAmount")
//        val newAmount = min(amount, curAmount + toNext(player.uy() + curAmount, terrainSize))
        val (newPlayerRect, newAmount) = calcNewAmountFn(curAmount, amount, player, terrainSize)
//        val newPlayerRect = player.plus(Vec2(0, newAmount))
        val indices = utils.rectToIndex(newPlayerRect)

        print("NewRect: $newPlayerRect\nNewAmount: $newAmount\nIndices: $indices\n")

        if (indices.xr.first < 0 || indices.yr.first < 0 || indices.xr.last >= terrain[0].size || indices.yr.last >= terrain.size) {
            println("Outside range")
            break
        }

        val slicedTerrain = slice(indices, terrain)
        val accessible = slicedTerrain.flatten().all { s: S -> accessFn(s) }

        println("Sliced:\n$slicedTerrain")

        if (!accessible) {
            println("Not accessible")
            break
        } else {
            println("Continuing")
            curAmount = newAmount
        }
    }

    return curAmount
}
