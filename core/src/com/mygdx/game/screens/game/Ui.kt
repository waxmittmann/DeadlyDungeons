package com.mygdx.game.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.widget.Menu
import com.kotcrab.vis.ui.widget.MenuBar
import com.kotcrab.vis.ui.widget.MenuItem
import com.kotcrab.vis.ui.widget.VisTable
import com.mygdx.game.ScreenChanger
import com.mygdx.game.ScreenId
import com.mygdx.game.draw.TextureDrawable
import com.mygdx.game.draw.Textures
import kotlin.math.min

class Ui(batch: Batch, private val screenChanger: ScreenChanger,
         private val textures: Textures) {
    private val menuStage: Stage = Stage(ScreenViewport(), batch)
    private var menuRoot: VisTable = VisTable()

    private val hudStage: Stage = Stage(ScreenViewport(), batch)
    private var hudRoot: VisTable = VisTable()

    private val stages: List<Stage> = listOf(menuStage, hudStage)

    init {
        // Set up UI.
        setupMenu()
//        setupHud()

        // Set inputProcessor to be stages.
        Gdx.input.inputProcessor = InputMultiplexer(*(stages.toTypedArray()))
    }

    private fun setupMenu() {
        menuRoot.setFillParent(true)
        menuStage.addActor(menuRoot)
        val menuBar = MenuBar()
        val fileMenu = Menu("File")
        val menuItem1 = MenuItem("menuitem #1")
        fileMenu.addItem(menuItem1)
        menuItem1.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                println("To Menu pressed.")
                screenChanger.changeScreen(ScreenId.TITLE)
            }
        })
        fileMenu.addItem(MenuItem("menuitem #2").setShortcut("f1"))
        fileMenu.addItem(MenuItem("menuitem #3").setShortcut("f2"))
        fileMenu.addItem(MenuItem("menuitem #4").setShortcut("alt + f4"))
        menuBar.addMenu(fileMenu)
        menuRoot.add(menuBar.table).expandX().fillX().row()
        menuRoot.add().expand().fill().row()
    }

    private fun setupHud() {
        val inventoryTable = VisTable()
        inventoryTable.width = 300f
        inventoryTable.height = 400f
        inventoryTable.x = 30f
        inventoryTable.y = 30f
        inventoryTable.background = TextureRegionDrawable(textures.bagTexture)
        hudStage.addActor(inventoryTable)
    }

    fun drawUi() = stages.forEach { stage ->
        stage.act(min(Gdx.graphics.deltaTime, 1 / 30f))
        stage.draw()
    }

    fun resize(width: Int, height: Int) =
            stages.forEach { it.viewport.update(width, height, true) }

    fun dispose() = menuStage.dispose()
}