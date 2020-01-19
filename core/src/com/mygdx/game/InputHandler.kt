package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

class InputHandler {

    fun handleInput(player: SceneObject) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            player.yc += 10
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.yc -= 10
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            player.xc -= 10
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            player.xc += 10
    }

}