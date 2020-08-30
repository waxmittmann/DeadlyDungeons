package com.mygdx.game.scenegraph

import arrow.core.extensions.option.foldable.get
import arrow.core.getOrElse
import arrow.core.or
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.headless.*
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.newworld.BBFunctions
import com.mygdx.game.newworld.PositionedDrawable
import com.mygdx.game.newworld.calcBoundingBox
import com.mygdx.game.newworld.getValue
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.MaybeHasDims2
import com.mygdx.game.util.geometry.Rect2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

object CalcBoundingBoxTest {
    private const val error: Double = 0.001

    @BeforeAll
    @JvmStatic
    fun setup() {
        // Set up headless application to avoid 'linker' errors.
        HeadlessApplication(object : ApplicationAdapter() {
            override fun create() {}
        }, HeadlessApplicationConfiguration())
    }

    class Data(val name: String, val dims: Dims2)

    class PositionedData(val name: String, val rect: Rect2)

    fun getValue(n: Node<PositionedData, Rect2>): Rect2 = when (n) {
        is Rotation -> n.parentVal
        is RelativeTranslation -> n.parentVal
        is AbsoluteTranslation -> n.parentVal
        is Leaf -> n.leafVal.rect
    }

    private val fns = BBFunctions<Data, Unit, PositionedData, Rect2>(
            getValue = { n ->
                when (n) {
                    is Rotation -> n.parentVal
                    is RelativeTranslation -> n.parentVal
                    is AbsoluteTranslation -> n.parentVal
                    is Leaf -> n.leafVal.rect
                }
            },
            getDims = { it.leafVal.dims },
            createRot = { r, children, rect -> Rotation(r.degrees, children, rect) },
            createAbsTrans = { r, children, rect -> AbsoluteTranslation(r.vector, children, rect) },
            createRelTrans = { r, children, rect -> RelativeTranslation(r.vector, children, rect) },
            createLeaf = { l, r -> Leaf(PositionedData(l.leafVal.name, r)) }
    )

    @Test
    fun noTransformShouldBeCentered() {
        // Setup
        val builder = SceneGraphBuilder(Unit, { Data("na", Dims2(10.0, 10.0)) }, { Unit })
        builder.leaf(Data("l", Dims2(50.0, 50.0)))
        val root = builder.build()

        // Run
        val result = calcBoundingBox(root, fns)

        // Check
        Assertions.assertEquals(Rect2(-25.0, -25.0, 50.0, 50.0), getValue(result.orNull()!!))
    }

    @Test
    fun translateShouldWork() {
        // Setup
        val builder = SceneGraphBuilder(Unit, { Data("na", Dims2(10.0, 10.0)) }, { Unit })
//        val builder = SceneGraphBuilder(Data("root", null), { Data("na", null) }, { Data("na", null) })
        builder.translate(10.0, -10.0) { leaf(Data("l", Dims2(50.0, 50.0))) }
        val root = builder.build()

        // Run
        val result: Rect2 = getValue(calcBoundingBox(root, fns).orNull()!!)

        // Check
        Assertions.assertEquals(Rect2(-15.0, -35.0, 50.0, 50.0), result )
    }

    @Test
    fun rotateShouldWork() {
        // Setup
        val builder = SceneGraphBuilder(Unit, { Data("na", Dims2(10.0, 10.0)) }, { Unit })
        builder.rotate(90f) { leaf(Data("l", Dims2(20.0, 40.0))) }
        val root = builder.build()

        // Run
        val result = getValue(calcBoundingBox(root, fns).orNull()!!)

        // Check
        Assertions.assertEquals(-20.0, result.lx, error)
        Assertions.assertEquals(-10.0, result.ly, error)
        Assertions.assertEquals(40.0, result.width, error)
        Assertions.assertEquals(20.0, result.height, error)
    }

    @Test
    fun rotateTranslateShouldWork() {
        // Setup
        // (0, 0), N -> (0, 0), E -> (20, -10), E
        val builder = SceneGraphBuilder(Unit, { Data("na", Dims2(10.0, 10.0)) }, { Unit })
//        val builder = SceneGraphBuilder(Data("root", null), { Data("na", null) }, { Data("na", null) })
        builder.rotate(90f) {
            translate(10.0, 20.0) {
                leaf(Data("l", Dims2(100.0, 500.0)))
            }
        }
        val root = builder.build()

        // Run
        val result = getValue(calcBoundingBox(root, fns).orNull()!!)

        // Check
        Assertions.assertEquals(-230.0, result.lx, error)
        Assertions.assertEquals(-60.0, result.ly, error)
        Assertions.assertEquals(500.0, result.width, error)
        Assertions.assertEquals(100.0, result.height, error)
    }

    @Test
    fun translateRotateShouldWork() {
        // Setup
        // (0, 0), N -> (0, 0), E -> (20, -10), E
        val builder = SceneGraphBuilder(Unit, { Data("na", Dims2(10.0, 10.0)) }, { Unit })
//        val builder = SceneGraphBuilder(Data("root", null), { Data("na", null) }, { Data("na", null) })
        builder.translate(10.0, 20.0) {
            rotate(90f) {
                leaf(Data("l", Dims2(100.0, 500.0)))
            }
        }
        val root = builder.build()

        // Run
        val result = getValue(calcBoundingBox(root, fns).orNull()!!)

        // Check
        Assertions.assertEquals(-240.0, result.lx, error)
        Assertions.assertEquals(-30.0, result.ly, error)
        Assertions.assertEquals(500.0, result.width, error)
        Assertions.assertEquals(100.0, result.height, error)
    }

    @Test
    fun twoObjectsShouldWork() {
        // Setup

        val builder = SceneGraphBuilder(Unit, { Data("na", Dims2(10.0, 10.0)) }, { Unit })
//        val builder = SceneGraphBuilder(Data("root", null), { Data("na", null) }, { Data("na", null) })
        val x = builder
                .translate(-100.0, -100.0) {
                    leaf(Data("l", Dims2(50.0, 50.0)))
                }


        builder
                .translate(-100.0, -100.0) {
                    leaf(Data("l", Dims2(50.0, 50.0)))
                }
                .translate(100.0, 100.0) {
                    leaf(Data("l", Dims2(50.0, 50.0)))
                }


        val root = builder.build()

        // Run
        val result = getValue(calcBoundingBox(root, fns).orNull()!!)

        // Check
        Assertions.assertEquals(-125.0, result.lx, error)
        Assertions.assertEquals(-125.0, result.ly, error)
        Assertions.assertEquals(250.0, result.width, error)
        Assertions.assertEquals(250.0, result.height, error)
    }
}