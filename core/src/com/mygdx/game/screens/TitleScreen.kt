package com.mygdx.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.widget.PopupMenu
import com.kotcrab.vis.ui.widget.VisImage
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.mygdx.game.ScreenChanger
import com.mygdx.game.ScreenId
import com.mygdx.game.util.geometry.Dims2
import kotlin.math.min


class TitleScreenParams(val fontGenerator: FreeTypeFontGenerator,
                        val windowDims: Dims2, val batch: Batch,
                        val screenChanger: ScreenChanger)

// Run via KotlinLauncher.
class TitleScreen(params: TitleScreenParams) : Screen {
    private lateinit var stage: Stage
    private lateinit var root: VisTable

    private lateinit var menuTable: VisTable
    private lateinit var background: VisImage

    private val screenChanger: ScreenChanger
    private val batch: Batch
    private val font: BitmapFont

    init {
        val parameter = FreeTypeFontParameter()
        parameter.size = 36
        font = params.fontGenerator.generateFont(
                parameter) // font size 12 pixels
        batch = params.batch
        screenChanger = params.screenChanger

        configUi()
    }

    private fun configUi() {
        // Set up UI.
        stage = Stage(ScreenViewport(), batch)
        root = VisTable()
        root.setFillParent(true)
        stage.addActor(root)

        background = VisImage()
        menuTable = VisTable()

        val style = VisTextButton.VisTextButtonStyle()
        style.font = font

        val startButton = VisTextButton("Start", style)

        startButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                screenChanger.changeScreen(ScreenId.MAIN_GAME)
                println("Start pressed.")
            }
        })

        val optionsButton = VisTextButton("Options", style)
        val quitButton = VisTextButton("Quit", style)
        menuTable.add(startButton).fillX().row()
        menuTable.add(optionsButton).fillX().row()
        menuTable.add(quitButton).fillX().row()

        root.add(menuTable).align(Align.center)

        // Set inputProcessor to be stage.
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        // Clear screen.
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(min(Gdx.graphics.deltaTime, 1 / 30f))
        stage.draw()

        background.draw(batch, 1.0f)
    }

    override fun resize(width: Int, height: Int) {
        if (width == 0 && height == 0) return  //see https://github.com/libgdx/libgdx/issues/3673#issuecomment-177606278
        stage.viewport.update(width, height, true)
        PopupMenu.removeEveryMenu(stage)
//        val resizeEvent = WindowResizeEvent()
//        for (actor in stage!!.actors) {
//            actor.fire(resizeEvent)
//        }
    }

    override fun hide() {}
    override fun show() {}
    override fun pause() {}
    override fun resume() {}

    override fun dispose() {
        stage.dispose()
    }
}

