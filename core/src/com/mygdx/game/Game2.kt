//package com.mygdx.game
//
//import com.badlogic.gdx.Gdx
//import com.badlogic.gdx.graphics.Color
//import com.badlogic.gdx.graphics.GL20
//import com.badlogic.gdx.graphics.OrthographicCamera
//import com.badlogic.gdx.graphics.g2d.*
//import com.badlogic.gdx.scenes.scene2d.Stage
//import com.badlogic.gdx.utils.viewport.ScreenViewport
//import com.kotcrab.vis.ui.VisUI
//import com.kotcrab.vis.ui.widget.Menu
//import com.kotcrab.vis.ui.widget.MenuBar
//import com.kotcrab.vis.ui.widget.MenuItem
//import com.kotcrab.vis.ui.widget.VisTable
//import com.mygdx.game.collision.processCollisions
//import com.mygdx.game.draw.*
//import com.mygdx.game.entities.*
//import com.mygdx.game.entities.terrain.WeightedAllocator
//import com.mygdx.game.entities.terrain.generateTerrain
//import com.mygdx.game.entities.worldobj.WorldObjFactory
//import com.mygdx.game.input.processInput
//import com.mygdx.game.util.borderCircle
//import com.mygdx.game.util.geometry.Dims2
//import com.mygdx.game.util.geometry.Point2
//import space.earlygrey.shapedrawer.ShapeDrawer
//import kotlin.math.min
//
//// Run via KotlinLauncher.
//class Game2 : OldGame() {
//    private lateinit var prototypes: Prototypes
//    private lateinit var worldObjFactory: WorldObjFactory
//    private lateinit var world: World
//    private var spawnState: SpawnMobState = SpawnMobState(0)
//    private lateinit var mobSpawner: SpawnMobs
//    private var stateTime: Float = 0.0f
//
//    private val windowWidth = 1200 //cameraWidth
//    private val windowHeight = 800 //cameraHeight
//
//    // Debug cam for 'true' coordinates.
//    lateinit var debugCam: OrthographicCamera
//
//    private var stage: Stage? = null
//
//    override fun create() {
//        // Set up window.
//        Gdx.graphics.setWindowedMode(windowWidth, windowHeight)
//
//        prototypes = Prototypes(DefaultTextures())
//        worldObjFactory = WorldObjFactory(prototypes)
//        mobSpawner = SpawnMobs(prototypes)
//
//        configUi()
//        configGame()
//    }
//
//    private fun configGame() {
//        // Set up World.
//        val randomTerrain = WeightedAllocator(listOf(Pair(80, prototypes.grass), Pair(80, prototypes.mud), Pair(10, prototypes.rocks)))
//        val terrain = generateTerrain(100, 100, prototypes.rocks) { _: Int, _: Int -> randomTerrain.allocate() }
//
//        // Set up debug cam.
//        debugCam = OrthographicCamera(windowWidth.toFloat(), windowHeight.toFloat())
//        debugCam.translate(windowWidth / 2.0f, windowHeight / 2.0f)
//        debugCam.update()
//
//        world = World(Point2(500.0, 500.0), Dims2(windowWidth.toFloat(), windowHeight.toFloat()), emptyList(),
//                0, worldObjFactory, 50, terrain, Dims2(windowWidth.toFloat(), windowHeight.toFloat()))
//    }
//
//    private fun configUi() {
//        // Set up UI.
//        VisUI.load(VisUI.SkinScale.X1)
//        stage = Stage(ScreenViewport())
//        val root = VisTable()
//        root.setFillParent(true)
//        stage!!.addActor(root)
//
//        // Set up menu.
//        val menuBar = MenuBar()
//        val fileMenu = Menu("File")
//        fileMenu.addItem(MenuItem("menuitem #1"))
//        fileMenu.addItem(MenuItem("menuitem #2").setShortcut("f1"))
//        fileMenu.addItem(MenuItem("menuitem #3").setShortcut("f2"))
//        fileMenu.addItem(MenuItem("menuitem #4").setShortcut("alt + f4"))
//        menuBar.addMenu(fileMenu)
//        root.add(menuBar.table).expandX().fillX().row()
//        root.add().expand().fill().row()
//
//        // Set inputProcessor to be stage.
//        Gdx.input.inputProcessor = stage
//    }
//
//    override fun render() {
//        stateTime += Gdx.graphics.deltaTime
//
//        updateState()
//
//        // Clear screen.
//        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f)
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
//
//        drawScene(stage!!.batch)
//        drawUi()
//    }
//
//    private fun updateState() {
//        world.setTime((stateTime * 1000).toLong())
//        spawnState = mobSpawner.spawnMobs(world)(spawnState)((stateTime * 1000).toLong())
//        processInput(world)
//        moveProjectiles(world.worldObjects.projectiles)
//        processCollisions(world)
//    }
//
//    private fun drawScene(batch: Batch) {
//        // Draw scene.
//        val drawer = ObjectDrawer()
//        batch.enableBlending()
//        batch.begin()
//        val shapeDrawer = ShapeDrawer(batch, singlePixel)
//        world.view.setProjectionMatrix(batch)
//        drawer.draw(batch, worldPositionedDrawables(world))
//        borderCircle(shapeDrawer, Color(0f, 1f, 0f, 1f), Color(0f, 0f, 0f, 1f),
//                Point2(windowWidth / 2.0, windowHeight / 2.0), 5.0f)
//
//        // Draw debug circle at true (0, 0)
//        batch.projectionMatrix = debugCam.combined
//        borderCircle(shapeDrawer, Color(1f, 1f, 0f, 1f), Color(0f, 0f, 0f, 1f),
//                Point2(windowWidth / 2.0, windowHeight / 2.0), 5.0f)
//        batch.end()
//    }
//
//    private fun drawUi() {
//        stage!!.act(min(Gdx.graphics.deltaTime, 1 / 30f))
//        stage!!.draw()
//    }
//
//    override fun dispose() {
//        stage!!.dispose()
//    }
//}
