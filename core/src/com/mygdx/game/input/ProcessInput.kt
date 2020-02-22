package com.mygdx.game.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.mygdx.game.entities.World
import com.mygdx.game.util.Point2

import arrow.core.compose

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
    println("Read keys: $keys")
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

val processActions: (World) -> (Set<Action>) -> Unit = { world -> { actions ->
    println("Actions are: $actions")
    for (action in actions) {
        when (action) {
            Action.MOVE_UP -> {
                world.worldObjects.player.position = world.worldObjects.player.position.plus(Point2(0, moveAmount))
                world.view = world.view.plus(0, moveAmount)
            }
            Action.MOVE_DOWN -> {
                world.worldObjects.player.position = world.worldObjects.player.position.minus(Point2(0, moveAmount))
                world.view = world.view.minus(0, moveAmount)
            }
            Action.MOVE_LEFT -> {
                world.worldObjects.player.position = world.worldObjects.player.position.minus(Point2(moveAmount, 0))
                world.updateView( -moveAmount, 0)
            }
            Action.MOVE_RIGHT -> {
                world.worldObjects.player.position = world.worldObjects.player.position.plus(Point2(moveAmount, 0))
                world.updateView(moveAmount, 0)
            }
        }
    }
}}

val processInput: (World) -> Unit =  { world ->
    (processActions(world) compose (processKeys compose readKey))(Unit)
}

