package com.mygdx.game.functions

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.mygdx.game.entities.World
import com.mygdx.game.util.Point2

interface Key

enum class PressedSpecial : Key {
    UP, DOWN, LEFT, RIGHT
}

class CharKey(char: Char) : Key

object UnknownKey : Key

fun readKey(): Key {
    if (Gdx.input.isKeyPressed(Input.Keys.UP))
        return PressedSpecial.UP
    else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
        return PressedSpecial.DOWN
    else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        return PressedSpecial.LEFT
    else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        return PressedSpecial.RIGHT
    return UnknownKey
}

fun processInput(input: Key, world: World) {
    when (input) {
        PressedSpecial.UP ->
            world.worldObjects.player.position =  world.worldObjects.player.position.plus(Point2(0, 10))
        PressedSpecial.DOWN ->
            world.worldObjects.player.position =  world.worldObjects.player.position.minus(Point2(0, 10))
        PressedSpecial.LEFT ->
            world.worldObjects.player.position =  world.worldObjects.player.position.minus(Point2(10, 0))
        PressedSpecial.RIGHT ->
            world.worldObjects.player.position =  world.worldObjects.player.position.plus(Point2(10, 0))
    }
}

