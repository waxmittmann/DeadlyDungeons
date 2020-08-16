package com.mygdx.game.scenegraph

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.headless.*
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.MaybeHasDims2
import com.mygdx.game.util.geometry.Rect2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

object CalcBoundingBoxTest {
    val error: Double = 0.001

    @BeforeAll
    @JvmStatic
    fun setup() {
        // Set up headless application to avoid 'linker' errors.
        HeadlessApplication(object : ApplicationAdapter() {
            override fun create() {}
        }, HeadlessApplicationConfiguration())
    }

    class Data(val name: String, val dims: Dims2?) : MaybeHasDims2 {
        override fun dims(): Dims2? = dims
    }

    @Test
    fun noTransformShouldBeCentered() {
        // Setup
        val builder = SceneGraphBuilder(Data("root", null), { Data("na", null) })
        builder.leaf(Data("l", Dims2(50f, 50f)))
        val root = builder.build()

        // Run
        val result = calcBoundingBox(root)

        // Check
        Assertions.assertEquals(Rect2(-25.0, -25.0, 50.0, 50.0), result.v)
    }

    @Test
    fun translateShouldWork() {
        // Setup
        val builder = SceneGraphBuilder(Data("root", null), { Data("na", null) })
        builder.translate(10.0, -10.0) { leaf(Data("l", Dims2(50f, 50f))) }
        val root = builder.build()

        // Run
        val result = calcBoundingBox(root)

        // Check
        Assertions.assertEquals(Rect2(-15.0, -35.0, 50.0, 50.0), result.v)
    }

    @Test
    fun rotateShouldWork() {
        // Setup
        val builder = SceneGraphBuilder(Data("root", null), { Data("na", null) })
        builder.rotate(90f) { leaf(Data("l", Dims2(20f, 40f))) }
        val root = builder.build()

        // Run
        val result = calcBoundingBox(root)

        // Check
        Assertions.assertEquals(-20.0, result.v!!.lx, error)
        Assertions.assertEquals(-10.0, result.v!!.ly, error)
        Assertions.assertEquals(40.0, result.v!!.width, error)
        Assertions.assertEquals(20.0, result.v!!.height, error)
    }

    @Test
    fun rotateTranslateShouldWork() {
        // Setup
        // (0, 0), N -> (0, 0), E -> (20, -10), E
        val builder = SceneGraphBuilder(Data("root", null), { Data("na", null) })
        builder.rotate(90f) {
            translate(10.0, 20.0) {
                leaf(Data("l", Dims2(100f, 500f)))
            }
        }
        val root = builder.build()

        // Run
        val result = calcBoundingBox(root)

        // Check
        Assertions.assertEquals(-230.0, result.v!!.lx, error)
        Assertions.assertEquals(-60.0, result.v!!.ly, error)
        Assertions.assertEquals(500.0, result.v!!.width, error)
        Assertions.assertEquals(100.0, result.v!!.height, error)
    }

    @Test
    fun translateRotateShouldWork() {
        // Setup
        // (0, 0), N -> (0, 0), E -> (20, -10), E
        val builder = SceneGraphBuilder(Data("root", null), { Data("na", null) })
        builder.translate(10.0, 20.0) {
            rotate(90f) {
                leaf(Data("l", Dims2(100f, 500f)))
            }
        }
        val root = builder.build()

        // Run
        val result = calcBoundingBox(root)

        // Check
        Assertions.assertEquals(-240.0, result.v!!.lx, error)
        Assertions.assertEquals(-30.0, result.v!!.ly, error)
        Assertions.assertEquals(500.0, result.v!!.width, error)
        Assertions.assertEquals(100.0, result.v!!.height, error)
    }
}