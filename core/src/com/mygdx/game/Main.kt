package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.util.geometry.Dims2

// Run via KotlinLauncher.
class Main : Game() {
    private lateinit var batch: Batch



    override fun create() {
        val windowWidth = 1200
        val windowHeight = 800

        // Set up window.
        Gdx.graphics.setWindowedMode(windowWidth, windowHeight)
        batch = SpriteBatch()
        setScreen(GameScreen(Dims2(windowWidth.toFloat(), windowHeight.toFloat()), batch))
    }

    override fun dispose() {
    }
}

