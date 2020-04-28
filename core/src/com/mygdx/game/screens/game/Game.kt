package com.mygdx.game.screens.game


import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.mygdx.game.actions.old.GameState
import com.mygdx.game.collision.processCollisions
import com.mygdx.game.entities.*
import com.mygdx.game.input.processInput
import com.mygdx.game.textures.Textures
import com.mygdx.game.util.borderCircle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.linear.ProjectionSaver
import space.earlygrey.shapedrawer.ShapeDrawer

class Game(private val batch: Batch, windowDims: Dims2,
           val textures: Textures) {
    private val prototypes: Prototypes = Prototypes(textures)
    private val mobSpawner: SpawnMobs = SpawnMobs(prototypes)
    private val debugCam: OrthographicCamera =
            OrthographicCamera(windowDims.width, windowDims.height)
    private var stateTime: Float = 0.0f
    private var spawnState: SpawnMobState = SpawnMobState(0)
    val shapeDrawer = ShapeDrawer(batch, textures.singlePixel)

    init {
        // Set up debug cam. Will break on resize.
        debugCam.translate(windowDims.width / 2.0f, windowDims.height / 2.0f)
        debugCam.update()

        val it = textures.debugCollection.iterator()
        val t = textures.debugCollection
        it.reset()
    }

    fun drawScene(world: World) {
        // Draw scene.
        batch.enableBlending()
        batch.begin()
        drawWorld(batch, world)
        drawCenterPoint(batch, world.view.getViewRect().asDims)
        batch.end()
    }

    private fun drawWorld(batch: Batch, world: World) =
            (ProjectionSaver.doThenRestore<Unit>(batch)) {
                // Set view to world camera.
                world.view.setProjectionMatrix(batch)

                // Draw terrain
                for (worldObjV2 in WorldFns.terrainWorldObjects(world)) {
                    worldObjV2.worldDrawable().draw(batch, 0.0f)
                }

                // Draw player
                val playerWo = world.worldObjects.player
                batch.transformMatrix.mul(playerWo.transformMatrix().get())
                playerWo.originTransformDrawables.forEach { it.draw(batch, 0f) }
            }

    private fun drawCenterPoint(batch: Batch, viewDims: Dims2) {
        // Set view to debug cam.
        batch.projectionMatrix = debugCam.combined

        // Draw point at center.
        borderCircle(shapeDrawer, Color.GREEN, Color.BLACK,
                Point2(viewDims.width / 2.0, viewDims.height / 2.0), 5.0f)
    }

    fun updateState(delta: Float, gameState: GameState) {
        stateTime += delta
        val world = gameState.world

        world.timeNow = (stateTime * 1000).toLong()
        spawnState = mobSpawner.spawnMobs(world)(spawnState)(
                (stateTime * 1000).toLong())
        processInput(gameState)
        moveProjectiles(world.worldObjects.projectiles)
        processCollisions(world)
    }

    fun resize(world: World, width: Int, height: Int) {
        // Update world view
        WorldFns.updateWindowSize(world, Dims2(width.toFloat(), height
                .toFloat()))
    }

    fun dispose() {}
}
