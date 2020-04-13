package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.kotcrab.vis.ui.VisUI
import com.mygdx.game.util.geometry.Dims2

enum class ScreenId {
    TITLE, MAIN_GAME
}

class ScreenChanger(val main: Main) {
    fun changeScreen(screen: ScreenId) {
        when (screen) {
            ScreenId.TITLE -> main.toTitleScreen()
            ScreenId.MAIN_GAME -> main.toGameScreen()
        }
    }
}

// Run via KotlinLauncher.
class Main : Game() {
    private lateinit var batch: Batch
    lateinit var generator: FreeTypeFontGenerator
    val windowWidth = 1200
    val windowHeight = 800

    lateinit var screenChanger: ScreenChanger

    override fun create() {
        VisUI.load(VisUI.SkinScale.X1)
        Gdx.graphics.setWindowedMode(windowWidth, windowHeight)
        batch = SpriteBatch()
        generator = FreeTypeFontGenerator(Gdx.files.internal("core/assets/fonts/OpenSans-Regular.ttf"))
        screenChanger = ScreenChanger(this)
        toTitleScreen()
    }

    override fun dispose() {
        generator.dispose()
    }

    fun toTitleScreen() = setScreen(TitleScreen(TitleScreenParams(generator, Dims2(windowWidth.toFloat(), windowHeight.toFloat()), batch, screenChanger)))

    fun toGameScreen() = setScreen(GameScreen(GameScreenParams(Dims2(windowWidth.toFloat(), windowHeight.toFloat()), batch, screenChanger)))
}
