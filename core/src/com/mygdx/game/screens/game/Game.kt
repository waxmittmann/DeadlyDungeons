package com.mygdx.game.screens.game


import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.mygdx.game.actions.old.GameState
import com.mygdx.game.collision.processCollisions
import com.mygdx.game.drawing.DrawableFns
import com.mygdx.game.drawing.SceneNodeDrawable
import com.mygdx.game.drawing.doTransform
import com.mygdx.game.drawing.scenegraph.SceneGraphBuilder
import com.mygdx.game.entities.*
import com.mygdx.game.input.processInput
import com.mygdx.game.textures.Textures
import com.mygdx.game.util.borderCircle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.linear.ProjectionSaver
import com.mygdx.game.util.linear.WrappedMatrix
import com.mygdx.game.util.singlePixel
import space.earlygrey.shapedrawer.ShapeDrawer

class Game(private val batch: Batch, windowDims: Dims2,
           val textures: Textures) {
    private val prototypes: Prototypes = Prototypes(textures)
    private val mobSpawner: SpawnMobs = SpawnMobs(prototypes)
    private val debugCam: OrthographicCamera

    private var stateTime: Float = 0.0f
    private var spawnState: SpawnMobState = SpawnMobState(0)

    private val hierarchicalPrototypeTest: List<SceneNodeDrawable>

    init {
        // Set up debug cam. Will break on resize.
        debugCam = OrthographicCamera(windowDims.width, windowDims.height)
        debugCam.translate(windowDims.width / 2.0f, windowDims.height / 2.0f)
        debugCam.update()

        val it = textures.debugCollection.iterator()
        val t = textures.debugCollection
        it.reset()

        val hp2_1 = SceneGraphBuilder()
                .translate(300.0, 300.0)
                .leaf(DrawableFns.create(t.get(0)), Dims2(20.0f, 20.0f))

                .rotate(90)
                .leaf(DrawableFns.create(t.get(1)), Dims2(20.0f, 20.0f)) // 3
                .translate(100.0, 0.0)
                .leaf(DrawableFns.create(t.get(2)), Dims2(20.0f, 20.0f)) // )
                .pop(2)

                .rotate(-90)
                .leaf(DrawableFns.create(t.get(3)), Dims2(20.0f, 20.0f)) // 3
                .translate(100.0, 0.0)
                .leaf(DrawableFns.create(t.get(4)), Dims2(20.0f, 20.0f)) // 4
                .build()

        hierarchicalPrototypeTest = doTransform(hp2_1)
    }

    fun drawScene(world: World) {
        // Draw scene.
        batch.enableBlending()
        world.view.setProjectionMatrix(batch)
        batch.begin()
        val shapeDrawer = ShapeDrawer(batch, singlePixel)

        (ProjectionSaver.doThenRestore<Unit>(batch)) {
            draw(batch, world)

            // Draw center where player is.
            borderCircle(shapeDrawer, Color(0f, 1f, 0f, 1f),
                    Color(0f, 0f, 0f, 1f),
                    Point2(world.view.getWindowDims().width / 2.0,
                            world.view.getWindowDims().height / 2.0), 5.0f)

            for (tp: SceneNodeDrawable in hierarchicalPrototypeTest) {
                tp.draw(batch, 0f)
            }
        }

        (ProjectionSaver.doThenRestoreWithTransformProjection<Unit>(
                batch)) { transform, _ ->
            val it = textures.debugCollection.iterator()

            val m = WrappedMatrix().translate(200f, 0f).rotate(30)
            batch.projectionMatrix = transform.mul(m).get()
            batch.draw(it.next(), 0f, 0f, 100f, 100f)

            val m2 = WrappedMatrix.from(m).translate(0f, 100f)
            batch.projectionMatrix = m2.get()
            batch.draw(it.next(), 0f, 0f, 100f, 100f)

            val m3 = WrappedMatrix.from(m2).rotate(60)
            batch.projectionMatrix = m3.get()
            batch.draw(it.next(), 0f, 0f, 100f, 100f)

            // Draw debug circle at true (0, 0)
            batch.projectionMatrix = debugCam.combined
            borderCircle(shapeDrawer, Color(1f, 1f, 0f, 1f),
                    Color(0f, 0f, 0f, 1f),
                    Point2(world.view.getWindowDims().width / 2.0,
                            world.view.getWindowDims().height / 2.0), 5.0f)
            batch.end()
        }
    }

    private fun draw(batch: Batch, world: World) =
            (ProjectionSaver.doThenRestore<Unit>(batch)) {
                // Should never call draw like this on a single one... akes no sense
                for (worldObjV2 in world.terrainWorldObjects()) {
                    worldObjV2.worldDrawable().draw(batch, 0.0f)
                }

                val playerWo = world.worldObjects.player
                batch.transformMatrix.mul(playerWo.transformMatrix().get())
                playerWo.originTransformDrawables.forEach { it.draw(batch, 0f) }
            }

    fun updateState(delta: Float, gameState: GameState) {
        stateTime += delta
        val world = gameState.world

        world.setTime((stateTime * 1000).toLong())
        spawnState = mobSpawner.spawnMobs(world)(spawnState)(
                (stateTime * 1000).toLong())
        processInput(gameState)
        moveProjectiles(world.worldObjects.projectiles)
        processCollisions(world)
    }

    fun resize(world: World, width: Int, height: Int) {
        // Update world view
        world.updateWindowSize(Dims2(width.toFloat(), height.toFloat()))
    }

    fun dispose() {}
}
