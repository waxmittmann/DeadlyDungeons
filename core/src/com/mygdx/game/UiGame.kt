package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.Menu
import com.kotcrab.vis.ui.widget.MenuBar
import com.kotcrab.vis.ui.widget.MenuItem
import com.kotcrab.vis.ui.widget.VisTable
import com.mygdx.game.collision.processCollisions
import com.mygdx.game.draw.*
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

// Run via KotlinLauncher.
class UiGame : ApplicationAdapter() {

    private var stage: Stage? = null
//    private var menuBar: MenuBar? = null

    val windowWidth = 1200
    val windowHeight = 800
    override fun create() {
        // Set up window.
//        Gdx.graphics.setWindowedMode(windowWidth, windowHeight)

        // Set up stage
        VisUI.load(VisUI.SkinScale.X1)
        stage = Stage(ScreenViewport())
        val root = VisTable()
        root.setFillParent(true)
        stage!!.addActor(root)
        Gdx.input.inputProcessor = stage

        // Set up menu
        val menuBar = MenuBar()
        root.add(menuBar.table).expandX().fillX().row()
        val fileMenu = Menu("File")
//        fileMenu.addItem(createTestsMenu())
        fileMenu.addItem(MenuItem("menuitem #1"))
        fileMenu.addItem(MenuItem("menuitem #2").setShortcut("f1"))
        fileMenu.addItem(MenuItem("menuitem #3").setShortcut("f2"))
        fileMenu.addItem(MenuItem("menuitem #4").setShortcut("alt + f4"))
        menuBar.addMenu(fileMenu)

        val game = GameWidget()
        root.add(game).expand().fill().row()
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage!!.act(min(Gdx.graphics.deltaTime, 1 / 30f))
        stage!!.draw()
    }

    override fun dispose() {
        VisUI.dispose()
        stage!!.dispose()
    }
}
