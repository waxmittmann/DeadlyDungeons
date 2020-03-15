package com.mygdx.game.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.mygdx.game.entities.World

import arrow.core.compose
import com.mygdx.game.entities.Terrain
import com.mygdx.game.util.Cardinality

const val moveAmount = 5

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


val processActions: (World) -> (Set<Action>) -> Unit = { world ->
    { actions ->
        for (action in actions) {
            val cardinality = when (action) {
                Action.MOVE_UP -> Cardinality.UP
                Action.MOVE_DOWN -> Cardinality.DOWN
                Action.MOVE_LEFT -> Cardinality.LEFT
                Action.MOVE_RIGHT -> Cardinality.RIGHT
            }
            val moveBy = movePlayer(world.worldObjects.player.rect(), 50, world.terrain,
                    { t: Terrain -> t.prototype.attributes.passable }, moveAmount, cardinality)
            world.movePlayer(moveBy)
        }
    }
}

val processInput: (World) -> Unit = { world ->
    (processActions(world) compose (processKeys compose readKey))(Unit)
}

