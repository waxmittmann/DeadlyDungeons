package com.mygdx.game.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.widget.Menu
import com.kotcrab.vis.ui.widget.MenuBar
import com.kotcrab.vis.ui.widget.MenuItem
import com.kotcrab.vis.ui.widget.VisTable
import com.mygdx.game.ScreenChanger
import com.mygdx.game.ScreenId
import com.mygdx.game.collision.processCollisions
import com.mygdx.game.draw.DefaultTextures
import com.mygdx.game.draw.ObjectDrawer
import com.mygdx.game.draw.singlePixel
import com.mygdx.game.draw.worldPositionedDrawables
import com.mygdx.game.entities.*
import com.mygdx.game.entities.terrain.WeightedAllocator
import com.mygdx.game.entities.terrain.generateTerrain
import com.mygdx.game.entities.worldobj.WorldObjFactory
import com.mygdx.game.input.processInput
import com.mygdx.game.util.borderCircle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import space.earlygrey.shapedrawer.ShapeDrawer
import kotlin.math.min


class GameScreenParams(val windowDims: Dims2, val batch: Batch,
                       val screenChanger: ScreenChanger)

class GameScreen(params: GameScreenParams) : Screen {
   private val batch: Batch = params.batch

    private val ui: Ui
    private val game: Game

    init {
        ui = Ui(batch, params.screenChanger)
        game = Game(batch, params.windowDims)
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
