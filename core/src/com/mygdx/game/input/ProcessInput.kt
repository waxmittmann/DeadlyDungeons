package com.mygdx.game.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.mygdx.game.entities.World
import com.mygdx.game.util.Point2

import arrow.core.compose
import com.mygdx.game.entities.Terrain
import com.mygdx.game.util.Cardinality
import com.mygdx.game.util.Rect2
import com.mygdx.game.util.Vec2

const val moveAmount = 5
//const val moveAmount = 1

val readKey: (Unit) -> (Set<Key>) = {
    val keys = emptySet<Key>().toMutableSet()
    if (Gdx.input.isKeyPressed(Input.Keys.UP))
        keys += PressedSpecial.UP
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
        keys += PressedSpecial.DOWN
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        keys += PressedSpecial.LEFT
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        keys += PressedSpecial.RIGHT
    keys
}

val processKeys: (Set<Key>) -> (Set<Action>) = { keys: Set<Key> ->
    val actions = emptySet<Action>().toMutableSet()
    if (keys.contains(PressedSpecial.UP))
        actions.add(Action.MOVE_UP)
    if (keys.contains(PressedSpecial.DOWN))
        actions.add(Action.MOVE_DOWN)
    if (keys.contains(PressedSpecial.LEFT))
        actions.add(Action.MOVE_LEFT)
    if (keys.contains(PressedSpecial.RIGHT))
        actions.add(Action.MOVE_RIGHT)
    actions
}

//fun isMoveCanPassTerrain(): (World) -> (Point2) -> Boolean = { world ->
//    { newPosition: Point2 ->
//        world.getTerrainAt(newPosition).map { terrain -> terrain.prototype.attributes.passable }.getOrElse { false }
//    }
//}
//
//fun doMove

val processActions: (World) -> (Set<Action>) -> Unit = { world ->
    { actions ->
        for (action in actions) {
            when (action) {
                Action.MOVE_UP -> {
                    val moveAmount = movePlayer(world.worldObjects.player.rect(), 50, world.terrain, { t: Terrain -> t.prototype.attributes.passable }, moveAmount, Cardinality.UP)
                    world.movePlayer(moveAmount)

//                    world.movePlayer(Vec2(0, moveAmount))
//                    world.movePlayer(moveAmount, Cardinality.UP)
//                    val newPos: Point2 = world.worldObjects.player.position.plus(moveVec)
//                    if (isMoveCanPassTerrain()(world)(newPos)) {
//                        world.movePlayer(moveVec)
////                        world.worldObjects.player.position = world.worldObjects.player.position.plus(newPos)
////                        world.view = world.view.plus(0, moveAmount)
//                    }
                }
                Action.MOVE_DOWN -> {
                    val moveAmount = movePlayer(world.worldObjects.player.rect(), 50, world.terrain, { t: Terrain -> t.prototype.attributes.passable }, moveAmount, Cardinality.DOWN)
                    world.movePlayer(moveAmount)

//                    world.movePlayer(Vec2(0, -moveAmount))
//                    world.movePlayer(moveAmount, Cardinality.DOWN)
//                    val newPos: Point2 = world.worldObjects.player.position.minus(Point2(0, moveAmount))
//                    world.worldObjects.player.position = world.worldObjects.player.position.minus(newPos)
//                    world.view = world.view.minus(0, moveAmount)
                }
                Action.MOVE_LEFT -> {
                    val moveAmount = movePlayer(world.worldObjects.player.rect(), 50, world.terrain, { t: Terrain -> t.prototype.attributes.passable }, moveAmount, Cardinality.LEFT)
                    world.movePlayer(moveAmount)

//                    world.movePlayer(Vec2(-moveAmount, 0))
//                    world.movePlayer(moveAmount, Cardinality.LEFT)
//                    val newPos: Point2 = world.worldObjects.player.position.minus(Point2( moveAmount, 0))
//                    world.worldObjects.player.position = world.worldObjects.player.position.minus(newPos)
//                    world.updateView(-moveAmount, 0)
                }
                Action.MOVE_RIGHT -> {
                    val moveAmount = movePlayer(world.worldObjects.player.rect(), 50, world.terrain, { t: Terrain -> t.prototype.attributes.passable }, moveAmount, Cardinality.RIGHT)
                    world.movePlayer(moveAmount)

//                    world.movePlayer(Vec2(moveAmount, 0))
//                    world.movePlayer(moveAmount, Cardinality.RIGHT)
//                    val newPos: Point2 = world.worldObjects.player.position.plus(Point2(moveAmount, 0))
//                    world.worldObjects.player.position = world.worldObjects.player.position.plus(newPos)
//                    world.updateView(moveAmount, 0)
                }
            }
        }
    }
}

val processInput: (World) -> Unit = { world ->
    (processActions(world) compose (processKeys compose readKey))(Unit)
}

