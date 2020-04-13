package com.mygdx.game.screens.game

import com.badlogic.gdx.Gdx
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
import kotlin.math.min

class Ui(batch: Batch, private val screenChanger: ScreenChanger) {
    private var stage: Stage = Stage(ScreenViewport(), batch)
    private var root: VisTable = VisTable()

    init {
        // Set up UI.
        root.setFillParent(true)
        stage.addActor(root)

        // Set up menu.
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
        root.add(menuBar.table).expandX().fillX().row()
        root.add().expand().fill().row()

        // Set inputProcessor to be stage.
        Gdx.input.inputProcessor = stage
    }

    fun drawUi() {
        stage.act(min(Gdx.graphics.deltaTime, 1 / 30f))
        stage.draw()
    }

    fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    fun dispose() = stage.dispose()
}