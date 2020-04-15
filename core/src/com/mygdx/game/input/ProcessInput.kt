package com.mygdx.game.input

import arrow.core.compose
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.mygdx.game.actions.*
import com.mygdx.game.actions.old.GameState
import com.mygdx.game.util.geometry.Point2

val moveActions =
        MoveMutationFactory(amount = 5)

class InputData(val keys: Set<Key>, val mousePos: Point2)

var debugA = 0

//fun insensitivePressed(s: String): Boolean =
//    (Gdx.input.isKeyPressed(Input.Keys.valueOf(s.toLowerCase()))) ||
//                (Gdx.input.isKeyPressed(Input.Keys.valueOf(s.toUpperCase())))
//
//fun insensitiveJustPressed(s: String): Boolean =
//        (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(s.toLowerCase()))) ||
//                (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(s.toUpperCase
//                ())))

val readKey: (Unit) -> InputData = {
    val keys = emptySet<Key>().toMutableSet()

    if (Gdx.input.isKeyPressed(Input.Keys.valueOf("!"))) debugA += 5
    if (Gdx.input.isKeyPressed(Input.Keys.valueOf("@"))) debugA -= 5
//    if (insensitiveJustPressed("i")) keys += PressedSpecial.ToggleInventory
    if (Gdx.input.isKeyJustPressed(Input.Keys.I)) keys += PressedSpecial
            .ToggleInventory
    if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) keys += PressedSpecial.Attack
    if (Gdx.input.isKeyPressed(Input.Keys.S)) keys += PressedSpecial.MoveDown
    if (Gdx.input.isKeyPressed(Input.Keys.A)) keys += PressedSpecial.MoveLeft
    if (Gdx.input.isKeyPressed(Input.Keys.D)) keys += PressedSpecial.MoveRight
    if (Gdx.input.isKeyPressed(Input.Keys.W)) keys += PressedSpecial.MoveUp

    InputData(keys, Point2.create(Gdx.input.x, Gdx.input.y))
}

val processKeys: (InputData) -> (Set<Mutation>) = {
    val keys: Set<Key> = it.keys
    val actions = emptySet<Mutation>().toMutableSet()

    // Movement
    if (keys.contains(PressedSpecial.MoveUp) && keys.contains(
                    PressedSpecial.MoveLeft)) actions.add(moveActions.leftUp)
    else if (keys.contains(PressedSpecial.MoveUp) && keys.contains(
                    PressedSpecial.MoveRight)) actions.add(moveActions.rightUp)
    else if (keys.contains(PressedSpecial.MoveDown) && keys.contains(
                    PressedSpecial.MoveLeft)) actions.add(moveActions.leftDown)
    else if (keys.contains(PressedSpecial.MoveDown) && keys.contains(
                    PressedSpecial.MoveRight)) actions.add(moveActions.rightDown)
    else if (keys.contains(PressedSpecial.MoveUp)) actions.add(moveActions.up)
    else if (keys.contains(PressedSpecial.MoveDown)) actions.add(moveActions.down)
    else if (keys.contains(PressedSpecial.MoveLeft)) actions.add(moveActions.left)
    else if (keys.contains(PressedSpecial.MoveRight)) actions.add(moveActions.right)

    // Update orientation based on mouse direction.
    actions.add(fromWorld(changeOrientation(it.mousePos)))

    // Attack
    if (keys.contains(PressedSpecial.Attack)) actions.add(
            attackMutation)

    // Ui mutations
    if (keys.contains(PressedSpecial.ToggleInventory)) {
        println("Toggle")
        actions.add(toggleInventory())
    }

    actions
}

val processActions: (GameState) -> (Set<Mutation>) -> Unit =
        { gameState -> { it.forEach { it(gameState) } } }

val processInput: (GameState) -> Unit = { world ->
    (processActions(world) compose (processKeys compose readKey))(Unit)
}
