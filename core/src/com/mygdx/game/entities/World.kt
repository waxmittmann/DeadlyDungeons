package com.mygdx.game.entities

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.extensions.list.foldable.foldLeft
import arrow.core.getOrElse
import com.mygdx.game.input.moveAmount
import com.mygdx.game.util.Cardinality
import com.mygdx.game.util.Point2
import com.mygdx.game.util.Rect2
import com.mygdx.game.util.Vec2

class World(val tileSize: Int, val worldObjects: WorldObjs, val terrain: Array<Array<Terrain>> = arrayOf(), var view: Rect2) {
    val width: Int = terrain[0].size * tileSize
    val height: Int = terrain.size * tileSize

    fun updateView(vec: Vec2) {
        view = view.plus(vec)
    }

    fun getTerrainAt(newPosition: Point2): Option<Terrain> {
        val xt = newPosition.x / tileSize
        val yt = newPosition.y / tileSize

        return if (xt >= 0 && xt < terrain[0].size && yt >= 0 && yt < terrain.size)
            Some(terrain[yt][xt])
        else
            None
    }

    private fun getTerrainAt(newRect: Rect2): List<Terrain> {
        val xl = newRect.lx / tileSize
        val yl = newRect.ly / tileSize

        val xu = (newRect.lx + newRect.width) / tileSize
        val yu = (newRect.ly + newRect.height) / tileSize

        println("Getting terrain $newRect, $xl, $yl, $xu, $yu")
        val r = (yl..yu).flatMap { y: Int ->
            (xl..xu).flatMap { x: Int ->
                println("At $xl $yl")
                if (y >= 0 && y < terrain.size && x >= 0 && x < terrain[0].size) {
                    println("Found " + terrain[x][y].prototype.name + " at $x $y")
                    listOf(terrain[y][x])
                } else {
                    emptyList()
                }
            }
        }
        println("Got terrain $newRect, $xl, $yl, $xu, $yu")
        return r
    }


    // Does not check validity of move
    private fun movePlayerWithoutCheck(moveBy: Vec2) {
        worldObjects.player.position = worldObjects.player.position.plus(moveBy)
        view = view.plus(moveBy)
    }

    fun movePlayer(moveBy: Vec2): Boolean {
        val newRect: Rect2 = worldObjects.player.rect().plus(moveBy)
        val performMove = getTerrainAt(newRect).foldLeft(true, { soFar: Boolean, terrain: Terrain -> soFar && terrain.prototype.attributes.passable })

        return if (performMove) {
            movePlayerWithoutCheck(moveBy)
            print("After move player at: " + worldObjects.player.rect())
            true
        } else {
            print("After no move player at: " + worldObjects.player.rect())
            false
        }
    }

    fun movePlayer(by: Int, cardinality: Cardinality) {
        when (cardinality) {
            Cardinality.UP -> {
                var moveBy = Vec2(0, by)
                val newRect: Rect2 = worldObjects.player.rect().plus(moveBy)
                val performMove = getTerrainAt(newRect).foldLeft(true, { soFar: Boolean, terrain: Terrain -> soFar && terrain.prototype.attributes.passable })
                if (performMove) {
                    println("Full move")
                    movePlayerWithoutCheck(moveBy)
                } else {
                    val cappedAmount = ((by + worldObjects.player.rect().uy()) / tileSize) * tileSize - worldObjects.player.rect().uy()
                    moveBy = Vec2(0, cappedAmount).plus(0, -1)
                    println("Capped move: $moveBy, $by, ${worldObjects.player.rect().uy()} $tileSize")
                    movePlayerWithoutCheck(moveBy)
                }
            }

            Cardinality.DOWN -> {
                var moveBy = Vec2(0, -by)
                val newRect: Rect2 = worldObjects.player.rect().plus(moveBy)
                val performMove = getTerrainAt(newRect).foldLeft(true, { soFar: Boolean, terrain: Terrain -> soFar && terrain.prototype.attributes.passable })
                if (performMove) {
                    println("Full move")
                    movePlayerWithoutCheck(moveBy)
                } else {
                    val cappedAmount = (-(by + worldObjects.player.rect().ly) / tileSize) * tileSize + worldObjects.player.rect().ly
                    moveBy = Vec2(0, cappedAmount).plus(0, 1)
                    println("Capped move: $moveBy, $by, ${worldObjects.player.rect().ly} $tileSize")
                    movePlayerWithoutCheck(moveBy)
                }
            }

            Cardinality.LEFT -> {
                var moveBy = Vec2( -by, 0)
                val newRect: Rect2 = worldObjects.player.rect().plus(moveBy)
                val performMove = getTerrainAt(newRect).foldLeft(true, { soFar: Boolean, terrain: Terrain -> soFar && terrain.prototype.attributes.passable })
                if (performMove) {
                    println("Full move")
                    movePlayerWithoutCheck(moveBy)
                } else {
                    val cappedAmount = (-(by + worldObjects.player.rect().lx) / tileSize) * tileSize + worldObjects.player.rect().lx
                    moveBy = Vec2( cappedAmount, 0).plus(1, 0)
                    println("Capped move: $moveBy, $by, ${worldObjects.player.rect().uy()} $tileSize")
                    movePlayerWithoutCheck(moveBy)
                }

            }

            Cardinality.RIGHT -> {
                var moveBy = Vec2( by, 0)
                val newRect: Rect2 = worldObjects.player.rect().plus(moveBy)
                val performMove = getTerrainAt(newRect).foldLeft(true, { soFar: Boolean, terrain: Terrain -> soFar && terrain.prototype.attributes.passable })
                if (performMove) {
                    println("Full move")
                    movePlayerWithoutCheck(moveBy)
                } else {
                    val cappedAmount = ((by + worldObjects.player.rect().ux()) / tileSize) * tileSize + worldObjects.player.rect().ux()
                    moveBy = Vec2(cappedAmount, 0).plus(-1, 0)
                    println("Capped move: $moveBy, $by, ${worldObjects.player.rect().uy()} $tileSize")
                    movePlayerWithoutCheck(moveBy)
                }
            }
        }
    }
}

