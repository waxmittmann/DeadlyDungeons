package com.mygdx.game.screens.game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.mygdx.game.actions.old.GameState
import com.mygdx.game.collision.processCollisions
import com.mygdx.game.draw.ObjectDrawer
import com.mygdx.game.draw.Textures
import com.mygdx.game.draw.singlePixel
import com.mygdx.game.draw.worldPositionedDrawables
import com.mygdx.game.entities.*
import com.mygdx.game.entities.worldobj.HierarchicalPrototypeBuilder
import com.mygdx.game.entities.worldobj.TransformedPrototype
import com.mygdx.game.entities.worldobj.WrappedMatrix
import com.mygdx.game.entities.worldobj.resolveHierarchicalPrototype
import com.mygdx.game.input.processInput
import com.mygdx.game.util.borderCircle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import space.earlygrey.shapedrawer.ShapeDrawer

class Game(private val batch: Batch, windowDims: Dims2,
           val textures: Textures) {
    private val prototypes: Prototypes = Prototypes(textures)
    private val mobSpawner: SpawnMobs = SpawnMobs(prototypes)
    private val debugCam: OrthographicCamera

    private var stateTime: Float = 0.0f
    private var spawnState: SpawnMobState = SpawnMobState(0)

    private val hierarchicalPrototypeTest: List<TransformedPrototype>

    init {
        // Set up debug cam. Will break on resize.
        debugCam = OrthographicCamera(windowDims.width, windowDims.height)
        debugCam.translate(windowDims.width / 2.0f, windowDims.height / 2.0f)
        debugCam.update()

//        val it = textures.itemsCollection.iterator()
        val it = textures.debugCollection.iterator()
//        val leaf1 = PrototypeLeaf(it.next(),
//                Dims2(100f, 100f))
//        val leaf2 = PrototypeLeaf(it.next(),
//                Dims2(50f, 50f))

//        val rotate1: PrototypeRotate = PrototypeRotate(Angle.create(90))
//                .add(leaf1)
//                .add(PrototypeTranslate(Vec2(100.0, 100.0))
//                        .add(leaf2))
//
//        val rotate2 = PrototypeTranslate(Vec2(200.0, 200.0))
//                .add(leaf1)
//
//        val rotate3 = PrototypeTranslate(Vec2(0.0, 500.0))
//                .add(PrototypeRotate(Angle.create(90)).add(leaf1))
//
//        val rotate4 =
//                PrototypeRotate(Angle.create(-90)).add(
//                PrototypeTranslate(Vec2(0.0, 200.0))
//                .add(leaf1))

        val hp1 = HierarchicalPrototypeBuilder().translate(300.0, 300.0)
                .leaf(it.next(), Dims2(100.0f, 100.0f)) // 1
                .translate(0.0, 100.0)
                .leaf(it.next(), Dims2(100.0f, 100.0f)) // 2
                .rotate(90).leaf(it.next(), Dims2(100.0f, 100.0f)) // 3
                .pop().rotate(-90).leaf(it.next(), Dims2(100.0f, 100.0f)) // 4
                .translate(100.0, 0.0)
//                .leaf(it.next(), Dims2(100.0f, 100.0f)) // 5
                .pop().translate(0.0, 100.0)
//                .leaf(it.next(), Dims2(100.0f, 100.0f)) // 6
                .translate(0.0, 100.0)
//                .leaf(it.next(), Dims2(100.0f, 100.0f)) // 7
                .build()

        val t = textures.debugCollection
        it.reset()
        val hp2_1 = HierarchicalPrototypeBuilder()
//                .translate(100.0,100.0)
                .translate(300.0, 300.0)
                .leaf(t.get(0), Dims2(20.0f, 20.0f)) // 1

                .rotate(90)
                .leaf(t.get(2), Dims2(20.0f, 20.0f)) // 3
                .translate(100.0, 0.0)
                .leaf(t.get(3), Dims2(20.0f, 20.0f)) // )
                .pop(2)

                .rotate(-90)
                .leaf(t.get(4), Dims2(20.0f, 20.0f)) // 3
                .translate(100.0, 0.0)
                .leaf(t.get(5), Dims2(20.0f, 20.0f)) // 4
                .build()


//        fun translate(x: Float, y: Float): MatrixWrapper =
//                MatrixWrapper(Matrix4(rawMatrix).translate(x, y, 0f))

//        hierarchicalPrototypeTest = resolveHierarchicalPrototype(rotate1)
//        hierarchicalPrototypeTest = resolveHierarchicalPrototype(rotate4)
        hierarchicalPrototypeTest = resolveHierarchicalPrototype(hp2_1)
    }

    fun drawScene(world: World) {
        // Draw scene.
        val drawer = ObjectDrawer()
        batch.enableBlending()
        batch.begin()
        val baseWrappedMatrix: WrappedMatrix =
                WrappedMatrix.from(batch.projectionMatrix)

        val shapeDrawer = ShapeDrawer(batch, singlePixel)
        world.view.setProjectionMatrix(batch)
        drawer.draw(batch, worldPositionedDrawables(world))
        borderCircle(shapeDrawer, Color(0f, 1f, 0f, 1f), Color(0f, 0f, 0f, 1f),
                Point2(world.view.getWindowDims().width / 2.0,
                        world.view.getWindowDims().height / 2.0), 5.0f)

//        val pre = Matrix4(batch.projectionMatrix)
        for (tp in hierarchicalPrototypeTest) {
//            batch.projectionMatrix = baseWrappedMatrix.mul(tp.matrix).getInternals()
            batch.projectionMatrix =
                    baseWrappedMatrix.mul(tp.matrix).getInternals()
            batch.draw(tp.textureRegion, 0f, 0f, tp.rect.widthF,
                    tp.rect.heightF)
        }


//        val m = MatrixWrapper.from(baseMatrix)
//                .rotate(30)
//                .translate(200f, 0f)
        val it = textures.debugCollection.iterator()

        val m = WrappedMatrix.from(baseWrappedMatrix).trn(200f, 0f).rotate(30)
        batch.projectionMatrix = m.getInternals()
        batch.draw(it.next(), 0f, 0f, 100f, 100f)

        val m2 = WrappedMatrix.from(m).trn(0f, 100f)
        batch.projectionMatrix = m2.getInternals()
        batch.draw(it.next(), 0f, 0f, 100f, 100f)

        val m3 = WrappedMatrix.from(m2).rotate(60)
//                .translate(0f, 100f)
        batch.projectionMatrix = m3.getInternals()
        batch.draw(it.next(), 0f, 0f, 100f, 100f)


        // Draw debug circle at true (0, 0)
        batch.projectionMatrix = debugCam.combined
        borderCircle(shapeDrawer, Color(1f, 1f, 0f, 1f), Color(0f, 0f, 0f, 1f),
                Point2(world.view.getWindowDims().width / 2.0,
                        world.view.getWindowDims().height / 2.0), 5.0f)
        batch.end()
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
