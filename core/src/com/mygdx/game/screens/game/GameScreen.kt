package com.mygdx.game.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.mygdx.game.ScreenChanger
import com.mygdx.game.actions.old.GameState
import com.mygdx.game.draw.Textures
import com.mygdx.game.entities.UiState
import com.mygdx.game.entities.createWorld
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2


class GameScreenParams(val windowDims: Dims2, val batch: Batch,
                       val screenChanger: ScreenChanger, val textures: Textures)

class GameScreen(params: GameScreenParams) : Screen {
    private val batch: Batch = params.batch

    private val ui: Ui
    private val game: Game
    private val state: GameState

    // TODO: Hmm probably create the world in game
    init {
//        val world = createWorld(params.textures, Point2(300.0, 300.0),
//        val world = createWorld(params.textures, Point2(1200.0, 300.0),
        val world = createWorld(params.textures, Point2(300.0, 300.0),
                params.windowDims)
        // -600 to 1200
        // -300 to
        state = GameState(world, UiState.create())
        ui = Ui(batch, params.screenChanger, params.textures)
        game = Game(batch, params.windowDims, params.textures)
    }

    override fun render(delta: Float) {
        // Clear screen.
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Update state.
        game.updateState(delta, state)

        // Draw scene and UI.
        game.drawScene(state.world)
        ui.drawUi(state.ui)
    }

    override fun resize(width: Int, height: Int) {
        game.resize(state.world, width, height)
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
