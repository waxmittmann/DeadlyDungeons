package com.mygdx.game.screens.game


import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.mygdx.game.actions.old.GameState
import com.mygdx.game.collision.processCollisions
import com.mygdx.game.drawing.DrawableFns.drawCentered
//import com.mygdx.game.drawing.scenegraph.Leaf
//import com.mygdx.game.drawing.scenegraph.Rotate
//import com.mygdx.game.drawing.scenegraph.SceneNode
//import com.mygdx.game.drawing.scenegraph.Translate
import com.mygdx.game.entities.*
import com.mygdx.game.entities.worldobj.SceneNodeAttributes
import com.mygdx.game.input.processInput
import com.mygdx.game.scenegraph.*
import com.mygdx.game.textures.Textures
import com.mygdx.game.util.borderCircle
import com.mygdx.game.util.drawCoord
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.linear.ProjectionSaver.Factory.doThenRestore
import com.mygdx.game.util.linear.WrappedMatrix
import space.earlygrey.shapedrawer.ShapeDrawer


class Game(private val batch: Batch, windowDims: Dims2,
        val textures: Textures) {
    private val prototypes: Prototypes = Prototypes(textures)
    private val mobSpawner: SpawnMobs = SpawnMobs(prototypes)
    private val debugCam: OrthographicCamera =
            OrthographicCamera(windowDims.width.toFloat(), windowDims.height.toFloat())
    private var stateTime: Float = 0.0f
    private var spawnState: SpawnMobState = SpawnMobState(0)
    val shapeDrawer = ShapeDrawer(batch, textures.singlePixel)

    init {
        // Set up debug cam. Will break on resize.
        debugCam.translate((windowDims.width / 2.0f).toFloat(), (windowDims.height / 2.0f).toFloat())
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
        drawCenterPoint(batch, world.view.getViewRect().asDims())
        batch.end()
    }

    private fun drawWorld(batch: Batch, world: World) =
            (doThenRestore<Unit>(batch)) {
                // TODO(wittie): From param
                val delta = 0.0f

                // Set view to world camera.
                world.view.setProjectionMatrix(batch)

                // Draw terrain
                for (terrainObj in WorldFns.terrainWorldObjects(world)) {
                    drawSceneNode(batch, 0.0f,
                            terrainObj.worldPositionedSceneNode(
                                    SceneNodeAttributes()))
                }

                // Draw player
                val playerWo = world.worldObjects.player
                drawSceneNode(batch, delta, playerWo.worldPositionedSceneNode(
                        SceneNodeAttributes()), true)
                playerWo.debugDraw(shapeDrawer)

                // TODO(wittie): Replace old bounding-box drawing code
                /*
                (doThenRestore<Unit>(batch)) {
//                    batch.projectionMatrix = debugCam.combined
//                    batch.transformMatrix = Matrix4()
                    shapeDrawer.setColor(1f, 0f, 0f, 0.5f)
//                    shapeDrawer.set

                    shapeDrawer.filledRectangle(playerWo.aabbBox().lxF,
                            playerWo.aabbBox().lyF, playerWo.aabbBox().widthF,
                            playerWo.aabbBox().heightF)

                    shapeDrawer.setColor(1f, 1f, 0f, 0.5f)
                    playerWo.points().forEach { shapeDrawer.filledCircle(it
                            .x.toFloat(), it.y.toFloat(), 3f) }

                }
                 */
            }

    private fun drawSceneNode(batch: Batch, delta: Float,
            node: GameNode, debugDraw: Boolean = false) {
        when (node) {
            is GameRotation -> doThenRestore<Unit>(batch)() {
                batch.transformMatrix = batch.transformMatrix.mul(
                        WrappedMatrix().postRotate(node.degrees).matrix())
                if (debugDraw) drawCoord(shapeDrawer)
//                    drawMatrix(shapeDrawer, WrappedMatrix(batch
//                            .transformMatrix))
                node.children.forEach {
                    drawSceneNode(batch, delta, it, debugDraw)
                }
            }

            is RelativeGameTranslation -> doThenRestore<Unit>(batch)() {
                batch.transformMatrix = batch.transformMatrix.mul(
                        WrappedMatrix().postTranslate(node.vector).matrix())
                if (debugDraw) drawCoord(shapeDrawer)
//                    drawMatrix(shapeDrawer, WrappedMatrix(batch
//                            .transformMatrix))
                node.children.forEach {
                    drawSceneNode(batch, delta, it, debugDraw)
                }
            }

            is AbsoluteGameTranslation -> throw NotImplementedError("Not implemented yet.")

            is GameLeaf -> {
                drawCentered(node.leafVal.sizedDrawable!!, batch, delta)
            }
        }
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
        WorldFns.updateWindowSize(world,
                Dims2(width.toDouble(), height.toDouble()))
    }

    fun dispose() {}
}
