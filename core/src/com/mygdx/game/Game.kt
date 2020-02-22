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
import com.mygdx.game.input.readKey
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

    override fun create() {
        batch = SpriteBatch()

        prototypes = Prototypes(DefaultTextures())

        mobSpawner = SpawnMobs(prototypes)

        // Set up camera
        cam = OrthographicCamera(500f, 500f)
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0f)
        cam.update()

        // Set up window
        Gdx.graphics.setWindowedMode(500, 500)

        // Set up World
        val randomTerrain = RandomAllocator(listOf(prototypes.grass, prototypes.mud, prototypes.rocks))
        val terrain = generateTerrain(20, 20) { r: Int, c: Int -> randomTerrain.allocate(r, c) }
        val player = WorldObj(prototypes.player,
                Attributes(),
                Point2(240, 240), DrawState(0f), Angle.create(0))
        world = World(50, WorldObjs(player, emptyList()), terrain, Rect2(0, 0, 500, 500))
    }

    var stateTime: Float = 0.0f

    override fun render() {
        stateTime += Gdx.graphics.deltaTime;
        processInput(world)
        spawnState = mobSpawner.spawnMobs(world)(spawnState)((stateTime * 1000).toLong())

        Gdx.gl.glClearColor(1.0f, 1.0f, 1f, 1f)
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

