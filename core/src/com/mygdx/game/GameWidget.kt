package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
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
//class GameWidget : Widget {
class GameWidget : Actor() {
    private val world: World
    private var spawnState: SpawnMobState = SpawnMobState(0)
    private val mobSpawner: SpawnMobs
    private var stateTime: Float = 0.0f

    private val windowWidth = 1200 //cameraWidth
    private val windowHeight = 800 //cameraHeight

    // Debug cam for 'true' coordinates.
    val debugCam: OrthographicCamera

    init {
        val prototypes = Prototypes(DefaultTextures())
        val worldObjFactory = WorldObjFactory(prototypes)
        mobSpawner = SpawnMobs(prototypes)

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

    private fun updateCamera() {
        println("$x, $y, $width, $height")
        world.view.updateCamera(x, y, width, height)
    }

//    @Override
//    public void update (boolean updateFrustum) {
//        projection.setToOrtho(zoom * -viewportWidth / 2, zoom * (viewportWidth / 2), zoom * -(viewportHeight / 2), zoom
//                * viewportHeight / 2, near, far);
//        view.setToLookAt(position, tmp.set(position).add(direction), up);
//        combined.set(projection);
//        Matrix4.mul(combined.val, view.val);
//
//        if (updateFrustum) {
//            invProjectionView.set(combined);
//            Matrix4.inv(invProjectionView.val);
//            frustum.update(invProjectionView);
//        }
//    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
//        val prevProjectionMatrix = batch!!.projectionMatrix
//        val prevTransformMatrix = batch.transformMatrix

//        batch!!.

        val oldProjectionMatrix = batch!!.getProjectionMatrix()
        val oldTransformMatrix = batch.getTransformMatrix()
//        val oldViewMatrix = batch.get
//        super.draw(batch, parentAlpha)

//        updateCamera()

        val camera = OrthographicCamera(width, height)
        val viewport = FitViewport(width, height, camera)
        viewport.setScreenBounds(0, 100, width.toInt(), height.toInt() - 100)
        viewport.update(width.toInt(), height.toInt() - 100)
        camera.update()


        // Update state.
//        stateTime += Gdx.graphics.deltaTime
//        world.setTime((stateTime * 1000).toLong())
//        spawnState = mobSpawner.spawnMobs(world)(spawnState)((stateTime * 1000).toLong())
//        processInput(world)
//        moveProjectiles(world.worldObjects.projectiles)
//        processCollisions(world)

        // Clear.
//        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f)
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Draw scene.
//        val drawer = ObjectDrawer()
//        val shapeDrawer = ShapeDrawer(batch, singlePixel)

//        world.view.setProjectionMatrix(batch)
//        drawer.draw(batch!!, worldPositionedDrawables(world))
//        borderCircle(shapeDrawer, Color(0f, 1f, 0f, 1f), Color(0f, 0f, 0f, 1f),
//                Point2(windowWidth / 2.0, windowHeight / 2.0), 5.0f)

//        batch.projectionMatrix = prevProjectionMatrix
//        batch.transformMatrix = prevTransformMatrix

        batch.setTransformMatrix(oldTransformMatrix)

//
//        // Draw debug circle at true (0, 0)
//        batch.projectionMatrix = debugCam.combined
//        borderCircle(shapeDrawer, Color(1f, 1f, 0f, 1f), Color(0f, 0f, 0f, 1f),
//                Point2(windowWidth / 2.0, windowHeight / 2.0), 5.0f)
    }

//    override fun dispose() {
//        batch.dispose()
//    }
}
