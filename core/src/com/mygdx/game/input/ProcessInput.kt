package com.mygdx.game.input

import arrow.core.compose
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.mygdx.game.entities.World
import com.mygdx.game.util.geometry.Point2

val moveActions = MoveActions(amount = 5)

class InputData(val keys: Set<Key>, val mouseX: Int, val mouseY: Int)

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

    InputData(keys, Gdx.input.x, Gdx.input.y)
}

val processKeys: (InputData) -> (Set<Action>) = {

    val keys: Set<Key> = it.keys
    val mouseX = it.mouseX
    val mouseY = it.mouseY
    val actions = emptySet<Action>().toMutableSet()

    // Movement
    if (keys.contains(PressedSpecial.UP) && keys.contains(
                    PressedSpecial.LEFT)) actions.add(moveActions.LEFT_UP)
    else if (keys.contains(PressedSpecial.UP) && keys.contains(
                    PressedSpecial.RIGHT)) actions.add(moveActions.RIGHT_UP)
    else if (keys.contains(PressedSpecial.DOWN) && keys.contains(
                    PressedSpecial.LEFT)) actions.add(moveActions.LEFT_DOWN)
    else if (keys.contains(PressedSpecial.DOWN) && keys.contains(
                    PressedSpecial.RIGHT)) actions.add(moveActions.RIGHT_DOWN)
    else if (keys.contains(PressedSpecial.UP)) actions.add(moveActions.UP)
    else if (keys.contains(PressedSpecial.DOWN)) actions.add(moveActions.DOWN)
    else if (keys.contains(PressedSpecial.LEFT)) actions.add(moveActions.LEFT)
    else if (keys.contains(PressedSpecial.RIGHT)) actions.add(moveActions.RIGHT)

    // Attack
    if (keys.contains(PressedSpecial.SPACE)) actions.add(AttackAction)

    actions.add(ChangeOrientation(
            Point2(it.mouseX.toDouble(), it.mouseY.toDouble())))

    actions
}

val processActions: (World) -> (Set<Action>) -> Unit =
        { world -> { it.forEach { it(world) } } }

val processInput: (World) -> Unit = { world ->
    (processActions(world) compose (processKeys compose readKey))(Unit)
}
