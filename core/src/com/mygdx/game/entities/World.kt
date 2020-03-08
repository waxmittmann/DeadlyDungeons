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
            true
        } else {
            false
        }
    }

    fun movePlayer(by: Int, cardinality: Cardinality) {
        when (cardinality) {
            Cardinality.UP -> {
                var moveBy = Vec2(0, by)
                var newRect: Rect2 = worldObjects.player.rect().plus(moveBy)

                var performMove = getTerrainAt(newRect).foldLeft(true, { soFar: Boolean, terrain: Terrain -> soFar && terrain.prototype.attributes.passable })
                if (performMove) {
                    println("Full move")
                    movePlayerWithoutCheck(moveBy)
                } else {
//                    moveBy = Vec2(0, ((by + worldObjects.player.rect().uy()) / tileSize) * tileSize)
//                    moveBy = Vec2(0, ((by + worldObjects.player.rect().uy()) % tileSize))
                    moveBy = Vec2(0, by - ((by + worldObjects.player.rect().uy()) % tileSize)).plus(0, -1)
//                    moveBy = Vec2(0, by - ((by + worldObjects.player.rect().uy()) % tileSize))
//                    moveBy = Vec2(0, tileSize - ((by + worldObjects.player.rect().uy()) % tileSize))
                    newRect = worldObjects.player.rect().plus(moveBy).shrink(1, 1)
//                    newRect = newRect.moduloY(tileSize).add(0, -1)
//                    performMove = getTerrainAt(newRect).foldLeft(true, { soFar: Boolean, terrain: Terrain ->
//                        println("Checking $terrain")
//                        soFar && terrain.prototype.attributes.passable
//                    })
//                    if (performMove) {
                        println("Capped move: $moveBy, $by, ${worldObjects.player.rect().uy()} $tileSize")
                        movePlayerWithoutCheck(moveBy)
//                    } else {
//                        println("No move: $moveBy, $by, ${worldObjects.player.rect().uy()} $tileSize")
//                    }
                }
            }

            Cardinality.DOWN -> {
                var moveBy = Vec2(0, -by)
                var newRect: Rect2 = worldObjects.player.rect().plus(moveBy)

                var performMove = getTerrainAt(newRect).foldLeft(true, { soFar: Boolean, terrain: Terrain -> soFar && terrain.prototype.attributes.passable })
                if (performMove) {
                    println("Full move")
                    movePlayerWithoutCheck(moveBy)
                } else {
                    moveBy = Vec2(0, -by + ((by + worldObjects.player.rect().ly) % tileSize)).plus(0, 1)
//                    newRect = worldObjects.player.rect().plus(moveBy).shrink(1, 1)
                    println("Capped move: $moveBy, $by, ${worldObjects.player.rect().ly} $tileSize")
                    movePlayerWithoutCheck(moveBy)
                }


//                val moveBy = Vec2(0, -by)
//                var newRect: Rect2 = worldObjects.player.rect().plus(moveBy)
//
//                val performMove = getTerrainAt(newRect).foldLeft(true, { soFar: Boolean, terrain: Terrain -> soFar && terrain.prototype.attributes.passable })
//                if (performMove) {
//                    movePlayerWithoutCheck(moveBy)
//                } else {
//                    newRect = newRect.moduloY(tileSize)
//                    val performMove = getTerrainAt(newRect).foldLeft(true, { soFar: Boolean, terrain: Terrain -> soFar && terrain.prototype.attributes.passable })
//
//                }
            }

            Cardinality.LEFT -> {
                var moveBy = Vec2( -by, 0)
                var newRect: Rect2 = worldObjects.player.rect().plus(moveBy)

                var performMove = getTerrainAt(newRect).foldLeft(true, { soFar: Boolean, terrain: Terrain -> soFar && terrain.prototype.attributes.passable })
                if (performMove) {
                    println("Full move")
                    movePlayerWithoutCheck(moveBy)
                } else {
                    moveBy = Vec2( -by + ((by + worldObjects.player.rect().lx) % tileSize), 0).plus(1, 0)
//                    newRect = worldObjects.player.rect().plus(moveBy).shrink(1, 1)
                    println("Capped move: $moveBy, $by, ${worldObjects.player.rect().uy()} $tileSize")
                    movePlayerWithoutCheck(moveBy)
                }

            }

            Cardinality.RIGHT -> {
                var moveBy = Vec2(by, 0)
                var newRect: Rect2 = worldObjects.player.rect().plus(moveBy)

                var performMove = getTerrainAt(newRect).foldLeft(true, { soFar: Boolean, terrain: Terrain -> soFar && terrain.prototype.attributes.passable })
                if (performMove) {
                    println("Full move")
                    movePlayerWithoutCheck(moveBy)
                } else {
                    moveBy = Vec2(by - ((by + worldObjects.player.rect().ux()) % tileSize), 0).plus(-1, 0)
//                    newRect = worldObjects.player.rect().plus(moveBy).shrink(1, 1)
                    println("Capped move: $moveBy, $by, ${worldObjects.player.rect().uy()} $tileSize")
                    movePlayerWithoutCheck(moveBy)
                }
            }
        }
    }
}

