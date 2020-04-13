package com.mygdx.game.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.mygdx.game.ScreenChanger
import com.mygdx.game.draw.Textures
import com.mygdx.game.util.geometry.Dims2


class GameScreenParams(val windowDims: Dims2, val batch: Batch,
                       val screenChanger: ScreenChanger, val textures: Textures)

class GameScreen(params: GameScreenParams) : Screen {
    private val batch: Batch = params.batch

    private val ui: Ui
    private val game: Game

    init {
        ui = Ui(batch, params.screenChanger, params.textures)
        game = Game(batch, params.windowDims, params.textures)
    }

    override fun render(delta: Float) {
        // Clear screen.
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Update state.
        game.updateState(delta)

        // Draw scene and UI.
        game.drawScene()
        ui.drawUi()
    }

    override fun resize(width: Int, height: Int) {
        game.resize(width, height)
        ui.resize(width, height)
    }

    override fun dispose() {
        game.dispose()
        ui.dispose()
    }

    override fun hide() {}
    override fun show() {}
    override fun pause() {}
    override fun resume() {}
}
