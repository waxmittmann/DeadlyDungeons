//package com.mygdx.game.util
//
//import com.badlogic.gdx.ApplicationAdapter
//import com.badlogic.gdx.backends.headless.HeadlessApplication
//import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration
//import com.badlogic.gdx.graphics.Pixmap
//import com.badlogic.gdx.graphics.Texture
//import com.mygdx.game.drawing.DebugDrawable
//import com.mygdx.game.drawing.DrawableTexture
//import com.mygdx.game.drawing.DrawableTextureRegion
//import com.mygdx.game.textures.DefaultTextures
//import com.mygdx.game.util.geometry.Dims2
//import com.mygdx.game.util.geometry.Rect2
//import com.mygdx.game.util.linear.WrappedMatrix
//
//
////import com.badlogic.gdx.backends.lwjgl.LwjglApplication
////import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
//
//val sum: Int.(Int) -> Int = { other -> plus(other) }
//
//val sum2 = fun Int.(other: Int): Int = this + other
//
//
//fun main() {
////    println(sum(1, 2))
////    println(sum2(1, 2))
//
//    val aa = object : ApplicationAdapter() {
//        override fun create() {
////            val pixmap = Pixmap(64, 64, Pixmap.Format.RGBA8888)
////            pixmap.setColor(0f, 1f, 0f, 0.75f)
////            pixmap.fillCircle(32, 32, 32)
////            val t = Texture(pixmap)
////            pixmap.dispose()
////            val d = DrawableTexture(t)
//
////            val d = fakeDrawable()
////            val d = DrawableTextureRegion(DefaultTextures().singlePixel, 1f)
//            val d = DebugDrawable()
//            val x = SceneGraphBuilder {}
//            val sg = x
//                    .leaf(d, Dims2(50f, 50f), "l1")
//                    .translate(10.0, 0.0, "t1")
//                    .leaf(d, Dims2(50f, 50f), "l2")
//                    .rotate(90, "r1")
//                    .leaf(d, Dims2(50f, 50f), "l3")
//                    .translate(10.0, 0.0, "t2")
//                    .leaf(d, Dims2(50f, 50f), "l4")
//                    .build()
//
////            val aabb = sg.aabb.center
//
//
//
//            println("Aabb: " + sg.aabb)
//
//            println(sg.print(WrappedMatrix()))
//        }
//    }
//
//    val config = HeadlessApplicationConfiguration()
//    val ha = HeadlessApplication(aa, config)
//
//    // I think I need to center it first BEFORE rotating. So:
//    // 1) Find center
//    // 2) Translate center to origin
//    // 3) Perform all ops
//    // 4) Translate back
//    val wm = WrappedMatrix().rotate(90)//.translate(10f, 0f)
//    val r = Rect2(0.0, 0.0, 50.0, 50.0)
//
//    val rt = wm.transform(r).asAabb
//
//    println(r)
//    println(rt)
//
//
//    ha.exit()
//}
