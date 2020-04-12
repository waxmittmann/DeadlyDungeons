package com.mygdx.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.entities.Prototypes
import com.mygdx.game.entities.SpawnMobState
import com.mygdx.game.entities.SpawnMobs
import com.mygdx.game.entities.World
import com.mygdx.game.entities.worldobj.WorldObjFactory


// Run via KotlinLauncher.
class Main : Game() {
    private lateinit var batch: Batch

    private val windowWidth = 1200 //cameraWidth
    private val windowHeight = 800 //cameraHeight

    override fun create() {
        // Set up window.
        Gdx.graphics.setWindowedMode(windowWidth, windowHeight)
        batch = SpriteBatch()
//        shapeRenderer = ShapeRenderer()
//        font = BitmapFont()
        setScreen(GameScreen(batch))
    }

    override fun dispose() {
    }
}
