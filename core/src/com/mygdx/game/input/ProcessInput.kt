package com.mygdx.game.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.mygdx.game.entities.World

import arrow.core.compose

val moveActions = MoveActions(amount = 5)

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
    if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        keys += PressedSpecial.ATTACK
    keys
}

val processKeys: (Set<Key>) -> (Set<Action>) = { keys: Set<Key> ->
    val actions = emptySet<Action>().toMutableSet()
    if (keys.contains(PressedSpecial.UP))
        actions.add(moveActions.UP)
    if (keys.contains(PressedSpecial.DOWN))
        actions.add(moveActions.DOWN)
    if (keys.contains(PressedSpecial.LEFT))
        actions.add(moveActions.LEFT)
    if (keys.contains(PressedSpecial.RIGHT))
        actions.add(moveActions.RIGHT)
    actions
}

val processActions: (World) -> (Set<Action>) -> Unit = { world -> { it.forEach { it(world) } } }

val processInput: (World) -> Unit = { world ->
    (processActions(world) compose (processKeys compose readKey))(Unit)
}
