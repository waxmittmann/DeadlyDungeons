package com.mygdx.game.drawing

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.drawing.scenegraph.SceneGraphBuilder
import com.mygdx.game.util.geometry.Dims2
import org.junit.jupiter.api.Test


internal class TransformDrawableTest {

    @Test
    fun testIt() {

        val config = HeadlessApplicationConfiguration()
        config.renderInterval = 1f/60

        val l = object: ApplicationAdapter() {}
        HeadlessApplication(l, config)

        val drawable = DrawableTextureRegion(TextureRegion(), 1f)
        val node = SceneGraphBuilder()
                .leaf(drawable, Dims2(10f, 10f), "1")
                .translate(100.0, 0.0, "2")
                .rotate(90, "2.1")
                .leaf(drawable, Dims2(11f, 11f), "2.1.1")
                .leaf(drawable, Dims2(11f, 11f), "2.1.2")
                .build()

       val transformed = doTransform(node)

        println(transformed.joinToString { it.toHierarchyString() +
                "\n" })

    }

}