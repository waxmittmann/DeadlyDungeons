//package com.mygdx.game.drawing
//
//import com.badlogic.gdx.graphics.g2d.TextureRegion
//import com.mygdx.game.drawing.scenegraph.Leaf
//import com.mygdx.game.drawing.scenegraph.SceneGraphBuilder
//import com.mygdx.game.drawing.scenegraph.SceneNode
//import com.mygdx.game.drawing.scenegraph.SceneParent
//import com.mygdx.game.util.geometry.Dims2
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.Test
//import java.lang.RuntimeException
//
//internal class SceneNodeDrawableTest {
//
//    fun asParent(n: SceneNode): SceneParent {
//        if (n is SceneParent)
//            return n
//        else
//            throw RuntimeException("Not a scene parent")
//    }
//
//    @Test
//    fun testIt() {
//        val drawable = DrawableTextureRegion(TextureRegion(), 1f)
//        val node = SceneGraphBuilder()
//                .leaf(drawable, Dims2(10f, 10f))
//                .translate(100.0, 0.0)
//                .leaf(drawable, Dims2(11f, 11f))
//                .build()
//
//        assertEquals(2, node.children.size )
//        assertTrue(node.children[0] is Leaf)
//        assertTrue(node.children[1] is SceneParent)
////        assertTrue(asParent(node.children[1]).children is SceneParent)
//    }
//
//}