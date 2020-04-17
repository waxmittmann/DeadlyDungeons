package com.mygdx.game.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.widget.*
import com.mygdx.game.ScreenChanger
import com.mygdx.game.ScreenId
import com.mygdx.game.draw.Textures
import com.mygdx.game.entities.UiState
import com.mygdx.game.ui.dragdrop.ActorFactory
import com.mygdx.game.ui.dragdrop.DragDrop
import com.mygdx.game.util.geometry.Rect2
import kotlin.math.min


class Ui(batch: Batch, private val screenChanger: ScreenChanger,
         private val textures: Textures) {
    private val menuStage: Stage = Stage(ScreenViewport(), batch)
    private var menuRoot: VisTable = VisTable()

    private val hudStage: Stage = Stage(ScreenViewport(), batch)
//    private var hudRoot: VisTable = VisTable()

    private val testDragAndDropStage: Stage = Stage(ScreenViewport(), batch)


    private val stages: List<Stage> = listOf(menuStage, hudStage, testDragAndDropStage)

    init {
        // Set up UI.
        setupMenu()
        setupHud()
//        setupDragAndDropTest()
        setupDragAndDropTest2()

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

    private fun setupDragAndDropTest() {
        val it = textures.debugCollection.iterator()

        val sourceImage = Image(it.next())
        sourceImage.setBounds(50f, 125f, 100f, 100f)
        testDragAndDropStage.addActor(sourceImage)

        val validTargetImage = Image(it.next())
        validTargetImage.setBounds(200f, 50f, 100f, 100f)
        testDragAndDropStage.addActor(validTargetImage)

        val invalidTargetImage = Image(it.next())
        invalidTargetImage.setBounds(200f, 200f, 100f, 100f)
        testDragAndDropStage.addActor(invalidTargetImage)


        val invalidTargetImage2 = Image(it.next())
        invalidTargetImage2.setBounds(400f, 200f, 100f, 100f)
        testDragAndDropStage.addActor(invalidTargetImage2)

        val dragAndDrop = DragAndDrop()
        dragAndDrop.addSource(object : DragAndDrop.Source(sourceImage) {
            override fun dragStart(event: InputEvent?, x: Float, y: Float,
                                   pointer: Int): Payload? {
                val payload = Payload()
                payload.setObject("Some payload!")
                payload.dragActor = VisLabel("payload 1")
                val validLabel = VisLabel("payload 2")
                validLabel.setColor(0f, 1f, 0f, 1f)
                payload.validDragActor = validLabel
                val invalidLabel = VisLabel("payload 3")
                invalidLabel.setColor(1f, 0f, 0f, 1f)
                payload.invalidDragActor = invalidLabel
                return payload
            }
        })

        dragAndDrop.addTarget(object : DragAndDrop.Target(validTargetImage) {
            override fun drag(source: DragAndDrop.Source, payload: Payload?,
                              x: Float, y: Float, pointer: Int): Boolean {
                this.actor.color = Color.GREEN
                return true
            }

            override fun reset(source: DragAndDrop.Source, payload: Payload?) {
                this.actor.color = Color.WHITE
            }

            override fun drop(source: DragAndDrop.Source, payload: Payload,
                              x: Float, y: Float, pointer: Int) {
                println("Accepted: " + payload.getObject() + " " + x + ", " + y)
            }
        })
        dragAndDrop.addTarget(object : DragAndDrop.Target(invalidTargetImage) {
            override fun drag(source: DragAndDrop.Source, payload: Payload?,
                              x: Float, y: Float, pointer: Int): Boolean {

//                println("This: " + this.actor.javaClass)
//                println("This: " + this.actor.javaClass)
//                println("Source: " + source.actor.javaClass)
//                println("Source: " + source.actor.javaClass)
//                this.actor.color = Color.RED
                this.actor.color = Color.BLUE
                return false
//                return true
            }

            override fun reset(source: DragAndDrop.Source, payload: Payload?) {
                this.actor.color = Color.WHITE
            }

            override fun drop(source: DragAndDrop.Source, payload: Payload,
                              x: Float, y: Float, pointer: Int) {

                println("Accepted 2: " + payload.getObject() + " " + x + ", "
                        + y)
            }
        })

        dragAndDrop.addTarget(object : DragAndDrop.Target(invalidTargetImage2) {
            override fun drag(source: DragAndDrop.Source, payload: Payload?,
                              x: Float, y: Float, pointer: Int): Boolean {
                this.actor.color = Color.PINK
//                return false
                return true
            }

            override fun reset(source: DragAndDrop.Source, payload: Payload?) {
                this.actor.color = Color.WHITE
            }

            override fun drop(source: DragAndDrop.Source, payload: Payload,
                              x: Float, y: Float, pointer: Int) {

                println("Accepted 3: " + payload.getObject() + " " + x + ", "
                        + y)
            }
        })

//         // Basic DnD.
//        val box = VisImage(it.next())
//        box.addListener(object : ClickListener() {
//            override fun clicked(event: InputEvent, x: Float, y: Float) {
//                println("Pressed the thing")
//            }
//        })
//        box.width = 150f
//        box.height = 100f
//        box.x = 30f
//        box.y = 30f
//        testDragAndDropStage.addActor(box)
    }


    private fun setupDragAndDropTest2() {
        val it = textures.itemsCollection.iterator()

        val dragDrop = DragDrop<String>()

        /*
            inner class Source(val actor: Actor, val payload: S, val dragActor: Actor,
                       val validActor: Actor, val invalidActor: Actor)

    inner class Target(val actor: Actor, val dragFn: (S) -> (Boolean),
                       val resetFn: (S) -> Unit, val acceptedFn: (S) -> Unit)
         */

        val actorFactory = ActorFactory(testDragAndDropStage)

        val source = dragDrop.Source(
                actorFactory.image(it.next(), Rect2(100.0, 100.0, 100.0, 100.0)),
                "Source",
                actorFactory.image(it.next(), Rect2(0.0, 0.0, 100.0, 100.0),
                        false),
                actorFactory.image(it.next(), Rect2(0.0, 0.0, 100.0, 100.0),
                        false),
                actorFactory.image(it.next(), Rect2(0.0, 0.0, 100.0, 100.0),
                        false)
        )

        val target = dragDrop.Target(
                actorFactory.image(it.next(), Rect2(200.0, 100.0, 100.0, 100.0)),
                { println(it); true; },
                {},
                {}
        )

        val target2 = dragDrop.Target(
                actorFactory.image(it.next(), Rect2(200.0, 200.0, 100.0, 100.0)),
                { println(it); false; },
                {},
                {}
        )

        dragDrop.addSource(source)
        dragDrop.addTarget(target)
        dragDrop.addTarget(target2)
    }

    private fun drawAndActStage(stage: Stage) {
        stage.act(min(Gdx.graphics.deltaTime, 1 / 30f))
        stage.draw()
    }

    fun drawUi(uiState: UiState) {
        drawAndActStage(menuStage)
//        menuStage.act(min(Gdx.graphics.deltaTime, 1 / 30f))
//        menuStage.draw()

        if (uiState.showInventory) {
//            hudStage.act(min(Gdx.graphics.deltaTime, 1 / 30f))
//            hudStage.draw()
//            drawAndActStage(hudStage)
//            drawAndActStage(testDragAndDropStage)
            drawAndActStage(testDragAndDropStage)
        }
    }

    fun resize(width: Int, height: Int) =
            stages.forEach { it.viewport.update(width, height, true) }

    fun dispose() = menuStage.dispose()
}