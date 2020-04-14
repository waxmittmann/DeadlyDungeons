package com.mygdx.game.input

import arrow.core.compose
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.mygdx.game.actions.Action
import com.mygdx.game.actions.AttackAction
import com.mygdx.game.actions.MoveActionFactory
import com.mygdx.game.actions.changeOrientation
import com.mygdx.game.entities.World
import com.mygdx.game.util.geometry.Point2

val moveActions =
        MoveActionFactory(amount = 5)

class InputData(val keys: Set<Key>, val mousePos: Point2)

var debugA = 0

val readKey: (Unit) -> InputData = {
    val keys = emptySet<Key>().toMutableSet()

    if (Gdx.input.isKeyPressed(Input.Keys.valueOf("!"))) debugA += 5
    if (Gdx.input.isKeyPressed(Input.Keys.valueOf("@"))) debugA -= 5

    if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) keys += PressedSpecial.SPACE
    if (Gdx.input.isKeyPressed(Input.Keys.S)) keys += PressedSpecial.DOWN
    if (Gdx.input.isKeyPressed(Input.Keys.A)) keys += PressedSpecial.LEFT
    if (Gdx.input.isKeyPressed(Input.Keys.D)) keys += PressedSpecial.RIGHT
    if (Gdx.input.isKeyPressed(Input.Keys.W)) keys += PressedSpecial.UP

    InputData(keys, Point2.create(Gdx.input.x, Gdx.input.y))
}

val processKeys: (InputData) -> (Set<Action>) = {
    val keys: Set<Key> = it.keys
    val actions = emptySet<Action>().toMutableSet()

    // Movement
    if (keys.contains(PressedSpecial.UP) && keys.contains(
                    PressedSpecial.LEFT)) actions.add(moveActions.leftUp)
    else if (keys.contains(PressedSpecial.UP) && keys.contains(
                    PressedSpecial.RIGHT)) actions.add(moveActions.rightUp)
    else if (keys.contains(PressedSpecial.DOWN) && keys.contains(
                    PressedSpecial.LEFT)) actions.add(moveActions.leftDown)
    else if (keys.contains(PressedSpecial.DOWN) && keys.contains(
                    PressedSpecial.RIGHT)) actions.add(moveActions.rightDown)
    else if (keys.contains(PressedSpecial.UP)) actions.add(moveActions.up)
    else if (keys.contains(PressedSpecial.DOWN)) actions.add(moveActions.down)
    else if (keys.contains(PressedSpecial.LEFT)) actions.add(moveActions.left)
    else if (keys.contains(PressedSpecial.RIGHT)) actions.add(moveActions.right)

    // Update orientation based on mouse direction.
    actions.add(changeOrientation(
            it.mousePos))

    // Attack
    if (keys.contains(PressedSpecial.SPACE)) actions.add(
            AttackAction)

    actions
}

val processActions: (World) -> (Set<Action>) -> Unit =
        { world -> { it.forEach { it(world) } } }

val processInput: (World) -> Unit = { world ->
    (processActions(world) compose (processKeys compose readKey))(Unit)
}
