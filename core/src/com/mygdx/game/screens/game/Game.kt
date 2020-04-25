package com.mygdx.game.screens.game

//import com.mygdx.game.entities.worldobj.SceneNodeBuilder
//import com.mygdx.game.entities.worldobj.TransformedSceneLeaf
//import com.mygdx.game.entities.worldobj.getLeaves
//import com.mygdx.game.scenegraph.getLeaves
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.mygdx.game.actions.old.GameState
import com.mygdx.game.collision.processCollisions
import com.mygdx.game.draw.DrawableV2
import com.mygdx.game.draw.Textures
import com.mygdx.game.draw.singlePixel
import com.mygdx.game.entities.*
import com.mygdx.game.entities.terrain.Terrain
import com.mygdx.game.input.processInput
import com.mygdx.game.scenegraph.LeafDrawable
import com.mygdx.game.scenegraph.SceneNodeBuilder
import com.mygdx.game.scenegraph.SceneNodeDrawable
import com.mygdx.game.scenegraph.doTransform
import com.mygdx.game.util.borderCircle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.linear.ProjectionSaver
import com.mygdx.game.util.linear.WrappedMatrix
import space.earlygrey.shapedrawer.ShapeDrawer
//import java.lang.Math.floor
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class Game(private val batch: Batch, windowDims: Dims2,
           val textures: Textures) {
    private val prototypes: Prototypes = Prototypes(textures)
    private val mobSpawner: SpawnMobs = SpawnMobs(prototypes)
    private val debugCam: OrthographicCamera

    private var stateTime: Float = 0.0f
    private var spawnState: SpawnMobState = SpawnMobState(0)

    //    private val hierarchicalPrototypeTest: List<TransformedSceneLeaf>
    private val hierarchicalPrototypeTest: List<SceneNodeDrawable>

    init {
        // Set up debug cam. Will break on resize.
//        debugCam = OrthographicCamera(windowDims.width, windowDims.height)
        debugCam = OrthographicCamera(windowDims.width, windowDims.height)
        debugCam.translate(windowDims.width / 2.0f, windowDims.height / 2.0f)
        debugCam.update()

        val it = textures.debugCollection.iterator()
        val t = textures.debugCollection
        it.reset()

        val hp2_1 = SceneNodeBuilder()
//                .translate(100.0,100.0)
                .translate(300.0, 300.0)
                .leaf(DrawableV2.create(t.get(0)), Dims2(20.0f, 20.0f))
                // 1

                .rotate(90)
                .leaf(DrawableV2.create(t.get(1)), Dims2(20.0f, 20.0f)) // 3
                .translate(100.0, 0.0)
                .leaf(DrawableV2.create(t.get(2)), Dims2(20.0f, 20.0f)) // )
                .pop(2)

                .rotate(-90)
                .leaf(DrawableV2.create(t.get(3)), Dims2(20.0f, 20.0f)) // 3
                .translate(100.0, 0.0)
                .leaf(DrawableV2.create(t.get(4)), Dims2(20.0f, 20.0f)) // 4
                .build()

//       hierarchicalPrototypeTest = getLeaves(hp2_1)
        hierarchicalPrototypeTest = doTransform(hp2_1)
    }

    fun drawScene(world: World) {
        // Draw scene.
//        val drawer = ObjectDrawer()
        batch.enableBlending()
        world.view.setProjectionMatrix(batch)
        println("Originally, :\n${batch.projectionMatrix}\n")
        batch.begin()

        (ProjectionSaver.doThenRestore<Unit>(batch)) {
            val baseWrappedMatrix: WrappedMatrix =
                    WrappedMatrix.from(batch.projectionMatrix)

            val shapeDrawer = ShapeDrawer(batch, singlePixel)

            // TODO: Replace
            draw(batch, world)
//        drawer.draw(batch, worldPositionedDrawables(world))

            borderCircle(shapeDrawer, Color(0f, 1f, 0f, 1f),
                    Color(0f, 0f, 0f, 1f),
                    Point2(world.view.getWindowDims().width / 2.0,
                            world.view.getWindowDims().height / 2.0), 5.0f)

//        val pre = Matrix4(batch.projectionMatrix)
//        for (tp: TransformedSceneLeaf in hierarchicalPrototypeTest) {
            for (tp: SceneNodeDrawable in hierarchicalPrototypeTest) {
//            batch.projectionMatrix = baseWrappedMatrix.mul(tp.matrix).getInternals()
//            batch.projectionMatrix =
//                    baseWrappedMatrix.mul(tp.matrix).getInternals()
                // Not like this.
//            drawCentered(tp.drawable, tp.dims)

                tp.draw(batch, 0f)

//            batch.draw(tp.textureRegion, 0f, 0f, tp.rect.widthF,
//                    tp.rect.heightF)
            }


//        val m = MatrixWrapper.from(baseMatrix)
//                .rotate(30)
//                .translate(200f, 0f)
            val it = textures.debugCollection.iterator()

//        val m = WrappedMatrix.from(baseWrappedMatrix).trn(200f, 0f).rotate(30)
            val m = WrappedMatrix().trn(200f, 0f).rotate(30)
//            batch.projectionMatrix = baseWrappedMatrix.mul(m).getInternals()
            batch.projectionMatrix = baseWrappedMatrix.mul(m).get()
            batch.draw(it.next(), 0f, 0f, 100f, 100f)

            val m2 = WrappedMatrix.from(m).trn(0f, 100f)
//            batch.projectionMatrix = m2.getInternals()
            batch.projectionMatrix = m2.get()
            batch.draw(it.next(), 0f, 0f, 100f, 100f)

            val m3 = WrappedMatrix.from(m2).rotate(60)
//                .translate(0f, 100f)
//            batch.projectionMatrix = m3.getInternals()
            batch.projectionMatrix = m3.get()
            batch.draw(it.next(), 0f, 0f, 100f, 100f)

            println("10, 10 -> " + m.transform(Point2(10.0, 10.0)))
            println("0, 0 -> " + m.transform(Point2(0.0, 0.0)))
//        println("0, 0 -> " + m2.transform(Point2(10.0, 10.0)))
//        println("0, 0 -> " + m3.transform(Point2(10.0, 10.0)))


            // Draw debug circle at true (0, 0)
            batch.projectionMatrix = debugCam.combined
            borderCircle(shapeDrawer, Color(1f, 1f, 0f, 1f),
                    Color(0f, 0f, 0f, 1f),
                    Point2(world.view.getWindowDims().width / 2.0,
                            world.view.getWindowDims().height / 2.0), 5.0f)
            batch.end()
        }
    }
//    fun terrainPositionedDrawables(terrains: List<List<Terrain>>, tileSize: Int, view: Rect2): List<DrawableV2.SizedDrawable> {
//        return (max(floor(view.ly / tileSize).toInt() - 1,
//                0)..min(floor(view.uy() / tileSize).toInt() + 1, terrains.size - 1)).flatMap { c ->
//            (max(floor(view.lx / tileSize).toInt() - 1, 0)..min(
//                    floor(view.ux() / tileSize).toInt() + 1,
//                    terrains[0].size - 1)).map { r ->
//
//                val terrain = terrains[c][r]
//                val p = Point2(r * tileSize - view.lx, c * tileSize - view.ly)
//                DrawableV2.SizedDrawable(terrain.drawable, Dims2(tileSize.toFloat(),
//                        tileSize.toFloat()))
////                PositionedDrawable(terrain.prototype.drawable, tileSize.toFloat(),
////                        tileSize.toFloat(), p.x.toFloat(), p.y.toFloat(), 0.0f,
////                        terrain.drawState)
//            }
//        }
//    }

    private fun draw(batch: Batch, world: World) =
        (ProjectionSaver.doThenRestore<Unit>(batch)) {


            // Should never call draw like this on a single one... akes no sense
            world.terrainDrawableAsLeaf().forEach {  it.draw(batch, 0f) }

            val playerWo = world.worldObjects.player
            batch.transformMatrix.mul(playerWo.transformMatrix().get())
            playerWo.originTransformDrawables.forEach { it.draw(batch, 0f) }


//            terrainPositionedDrawables(world.terrain)

//            world.terrain.
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
