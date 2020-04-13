package com.mygdx.game.screens.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.mygdx.game.collision.processCollisions
import com.mygdx.game.draw.DefaultTextures
import com.mygdx.game.draw.ObjectDrawer
import com.mygdx.game.draw.singlePixel
import com.mygdx.game.draw.worldPositionedDrawables
import com.mygdx.game.entities.*
import com.mygdx.game.entities.terrain.WeightedAllocator
import com.mygdx.game.entities.terrain.generateTerrain
import com.mygdx.game.entities.worldobj.WorldObjFactory
import com.mygdx.game.input.processInput
import com.mygdx.game.util.borderCircle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import space.earlygrey.shapedrawer.ShapeDrawer

class Game(private val batch: Batch, windowDims: Dims2) {
    private val prototypes: Prototypes = Prototypes(DefaultTextures())
    private val worldObjFactory: WorldObjFactory = WorldObjFactory(prototypes)
    private val world: World
    private val mobSpawner: SpawnMobs = SpawnMobs(prototypes)
    private val debugCam: OrthographicCamera

    private var stateTime: Float = 0.0f
    private var spawnState: SpawnMobState = SpawnMobState(0)

    init {
        // Set up World.
        val randomTerrain = WeightedAllocator(
                listOf(Pair(80, prototypes.grass), Pair(80, prototypes.mud),
                        Pair(10, prototypes.rocks)))
        val terrain = generateTerrain(100, 100,
                prototypes.rocks) { _: Int, _: Int -> randomTerrain.allocate() }

        // Set up debug cam. Will break on resize.
        debugCam = OrthographicCamera(windowDims.width, windowDims.height)
        debugCam.translate(windowDims.width / 2.0f, windowDims.height / 2.0f)
        debugCam.update()

        world = World(Point2(500.0, 500.0), emptyList(), 0, worldObjFactory, 50,
                terrain, windowDims)
    }

    fun drawScene() {
        // Draw scene.
        val drawer = ObjectDrawer()
        batch.enableBlending()
        batch.begin()
        val shapeDrawer = ShapeDrawer(batch, singlePixel)
        world.view.setProjectionMatrix(batch)
        drawer.draw(batch, worldPositionedDrawables(world))
        borderCircle(shapeDrawer, Color(0f, 1f, 0f, 1f), Color(0f, 0f, 0f, 1f),
                Point2(world.view.getWindowDims().width / 2.0,
                        world.view.getWindowDims().height / 2.0), 5.0f)

        // Draw debug circle at true (0, 0)
        batch.projectionMatrix = debugCam.combined
        borderCircle(shapeDrawer, Color(1f, 1f, 0f, 1f), Color(0f, 0f, 0f, 1f),
                Point2(world.view.getWindowDims().width / 2.0,
                        world.view.getWindowDims().height / 2.0), 5.0f)
        batch.end()
    }

    fun updateState(delta: Float) {
        stateTime += delta

        world.setTime((stateTime * 1000).toLong())
        spawnState = mobSpawner.spawnMobs(world)(spawnState)(
                (stateTime * 1000).toLong())
        processInput(world)
        moveProjectiles(world.worldObjects.projectiles)
        processCollisions(world)
    }

    fun resize(width: Int, height: Int) {
        // Update world view
        world.updateWindowSize(Dims2(width.toFloat(), height.toFloat()))
    }

    fun dispose() {}
}
