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
        keys += PressedSpecial.SPACE
    keys
}

val processKeys: (Set<Key>) -> (Set<Action>) = { keys: Set<Key> ->
    val actions = emptySet<Action>().toMutableSet()

    // Movement
    if (keys.contains(PressedSpecial.UP) && keys.contains(PressedSpecial.LEFT))
        actions.add(moveActions.LEFT_UP)
    else if (keys.contains(PressedSpecial.UP) && keys.contains(PressedSpecial.RIGHT))
        actions.add(moveActions.RIGHT_UP)
    else if (keys.contains(PressedSpecial.DOWN) && keys.contains(PressedSpecial.LEFT))
        actions.add(moveActions.LEFT_DOWN)
    else if (keys.contains(PressedSpecial.DOWN) && keys.contains(PressedSpecial.RIGHT))
        actions.add(moveActions.RIGHT_DOWN)
    else if (keys.contains(PressedSpecial.UP))
        actions.add(moveActions.UP)
    else if (keys.contains(PressedSpecial.DOWN))
        actions.add(moveActions.DOWN)
    else if (keys.contains(PressedSpecial.LEFT))
        actions.add(moveActions.LEFT)
    else if (keys.contains(PressedSpecial.RIGHT))
        actions.add(moveActions.RIGHT)

    // Attack
    if (keys.contains(PressedSpecial.SPACE))
       actions.add(AttackAction)

    actions
}

val processActions: (World) -> (Set<Action>) -> Unit = { world -> { it.forEach { it(world) } } }

val processInput: (World) -> Unit = { world ->
    (processActions(world) compose (processKeys compose readKey))(Unit)
}
