package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.kotcrab.vis.ui.VisUI
import com.mygdx.game.textures.DefaultTextures
import com.mygdx.game.screens.game.GameScreen
import com.mygdx.game.screens.game.GameScreenParams
import com.mygdx.game.screens.TitleScreen
import com.mygdx.game.screens.TitleScreenParams
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

interface ChangeScreen {
    fun changeScreen(screen: Screen)
}

class Main : ChangeScreen, Game() {
    private lateinit var textures: DefaultTextures
    private lateinit var batch: Batch
    private lateinit var generator: FreeTypeFontGenerator
    private lateinit var screenChanger: ScreenChanger

    override fun create() {
        println(1)
        val windowWidth = 1200
        val windowHeight = 800

        VisUI.load(VisUI.SkinScale.X1)

        println(2)
        Gdx.graphics.setWindowedMode(windowWidth, windowHeight)
        batch = SpriteBatch()

        println(3)
        generator = FreeTypeFontGenerator(
                Gdx.files.internal("core/assets/fonts/OpenSans-Regular.ttf"))

        println(4)
        screenChanger = ScreenChanger(this)

        println(5)
        textures = DefaultTextures()

        println(6)
        toTitleScreen()
    }

    override fun dispose() = generator.dispose()

    fun toTitleScreen() = setScreen(TitleScreen(
            TitleScreenParams(generator, getCurDims(), batch, screenChanger)))

    fun toGameScreen() = setScreen(
            GameScreen(
                    GameScreenParams(
                            getCurDims(), batch, screenChanger, textures)))

    private fun getCurDims(): Dims2 =
            Dims2(Gdx.graphics.width.toDouble(), Gdx.graphics.height.toDouble())

    override fun changeScreen(screen: Screen) = setScreen(screen)
}
