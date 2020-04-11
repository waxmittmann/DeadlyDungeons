package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.*
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

// Run via KotlinLauncher.
class Game : ApplicationAdapter() {
    private lateinit var prototypes: Prototypes
    private lateinit var worldObjFactory: WorldObjFactory
    private lateinit var batch: SpriteBatch
    private lateinit var world: World
    private var spawnState: SpawnMobState = SpawnMobState(0)
    private lateinit var mobSpawner: SpawnMobs
    private var stateTime: Float = 0.0f

    private val windowWidth = 1200 //cameraWidth
    private val windowHeight = 800 //cameraHeight

    // Debug cam for 'true' coordinates.
    lateinit var debugCam: OrthographicCamera

    override fun create() {
        prototypes = Prototypes(DefaultTextures())
        worldObjFactory = WorldObjFactory(prototypes)
        mobSpawner = SpawnMobs(prototypes)

        // Set up window.
        Gdx.graphics.setWindowedMode(windowWidth, windowHeight)

        // Creating the batch before setting up windowed mode mean it would inherit the initial screen resolution of
        // 640x480.
        batch = SpriteBatch()

        // Set up World.
        val randomTerrain = WeightedAllocator(listOf(Pair(80, prototypes.grass), Pair(80, prototypes.mud), Pair(10, prototypes.rocks)))
        val terrain = generateTerrain(100, 100, prototypes.rocks) { _: Int, _: Int -> randomTerrain.allocate() }

        // Set up debug cam.
        debugCam = OrthographicCamera(windowWidth.toFloat(), windowHeight.toFloat())
        debugCam.translate(windowWidth / 2.0f, windowHeight / 2.0f)
        debugCam.update()

        world = World(Point2(500.0, 500.0), Dims2(windowWidth.toFloat(), windowHeight.toFloat()), emptyList(),
                0, worldObjFactory, 50, terrain, Dims2(windowWidth.toFloat(), windowHeight.toFloat()))
    }

    override fun render() {
        // Update state.
        stateTime += Gdx.graphics.deltaTime
        world.setTime((stateTime * 1000).toLong())
        spawnState = mobSpawner.spawnMobs(world)(spawnState)((stateTime * 1000).toLong())
        processInput(world)
        moveProjectiles(world.worldObjects.projectiles)
        processCollisions(world)

        // Clear.
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Draw scene.
        val drawer = ObjectDrawer()
        batch.enableBlending()
        batch.begin()
        val shapeDrawer = ShapeDrawer(batch, singlePixel)
        world.view.setProjectionMatrix(batch)
        drawer.draw(batch, worldPositionedDrawables(world))
        borderCircle(shapeDrawer, Color(0f, 1f, 0f, 1f), Color(0f, 0f, 0f, 1f),
                Point2(windowWidth / 2.0, windowHeight / 2.0), 5.0f)

        // Draw debug circle at true (0, 0)
        batch.projectionMatrix = debugCam.combined
        borderCircle(shapeDrawer, Color(1f, 1f, 0f, 1f), Color(0f, 0f, 0f, 1f),
                Point2(windowWidth / 2.0, windowHeight / 2.0), 5.0f)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }
}
