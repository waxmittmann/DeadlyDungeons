package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.*
import com.mygdx.game.collision.CollisionDetector
import com.mygdx.game.draw.DefaultTextures
import com.mygdx.game.draw.DrawState
import com.mygdx.game.draw.ObjectDrawer
import com.mygdx.game.entities.*
import com.mygdx.game.functions.*
import com.mygdx.game.input.processInput
import com.mygdx.game.util.Angle
import com.mygdx.game.util.Point2
import com.mygdx.game.util.Rect2


// Run via KotlinLauncher.
class Game : ApplicationAdapter() {
    private lateinit var prototypes: Prototypes
    private val collisionDetector = CollisionDetector()
    private lateinit var batch: SpriteBatch
    private lateinit var world: World
    private lateinit var cam: OrthographicCamera
    private var spawnState: SpawnMobState = SpawnMobState(0)
    private lateinit var mobSpawner: SpawnMobs
    private var stateTime: Float = 0.0f

    private val cameraWidth = 1200
    private val cameraHeight = 800
    private val windowWidth = cameraWidth
    private val windowHeight = cameraHeight

    override fun create() {
        batch = SpriteBatch()
        prototypes = Prototypes(DefaultTextures())
        mobSpawner = SpawnMobs(prototypes)

        // Set up camera.
        cam = OrthographicCamera(cameraWidth.toFloat(), cameraHeight.toFloat())
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0f)
        cam.update()

        // Set up window.
        Gdx.graphics.setWindowedMode(windowWidth, windowHeight)

        // Set up World.
        val randomTerrain = WeightedAllocator(listOf(Pair(80, prototypes.grass), Pair(80, prototypes.mud), Pair(10, prototypes.rocks)))
        val terrain = generateTerrain(100, 100, prototypes.rocks) { r: Int, c: Int -> randomTerrain.allocate() }
        terrain[2][2] = Terrain(prototypes.rocks, DrawState(0f))

        val player = WorldObj(prototypes.player,
                Attributes(),
                Point2(250, 250), DrawState(0f), Angle.create(0))
        val view = Rect2(0, 0, cameraWidth, cameraHeight)
        world = World(50, WorldObjs(player, emptyList()), terrain, view)
    }

    override fun render() {
        // Update state.
        stateTime += Gdx.graphics.deltaTime
        processInput(world)
        spawnState = mobSpawner.spawnMobs(world)(spawnState)((stateTime * 1000).toLong())

        // Draw.
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val drawer = ObjectDrawer()
        batch.projectionMatrix = cam.combined
        batch.enableBlending()
        batch.begin()
        drawer.draw(batch, worldPositionedDrawables(world))
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }
}
