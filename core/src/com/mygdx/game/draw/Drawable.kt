package com.mygdx.game.draw

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.util.Dims2
import com.mygdx.game.util.Point2



//class DrawState(val delta: Float, val scaleMult: Float)
class DrawState(val delta: Float, val scaleMult: Float) {
}

//interface DrawState {
//    val delta: Float
//    val scaleMult: Float
//}
//
//class AnimDrawState(override var delta: Float, override var scaleMult: Float) : DrawState
//class StaticDrawState(override var scaleMult: Float) : DrawState {
//    override val delta: Float = 0f
//}

interface Drawable {
//    val width: Int
//    val height: Int
//    val widthToHeight: Float
    val dims: Dims2
//    val widthRatio: Int
//    val heightRatio: Int
//    fun draw(sb: SpriteBatch, delta: Float, x: Int, y: Int)
//    fun draw(sb: SpriteBatch, delta: Float, x: Int, y: Int, scaleMult: Float)
    fun draw(sb: SpriteBatch, width: Float, height: Float, x: Float, y: Float, drawData: DrawState)
}

//class TextureDrawable(val texture: Texture, override val width: Int, override val height: Int) : Drawable {
class TextureDrawable(val texture: Texture, override val dims: Dims2) : Drawable {
    override fun draw(sb: SpriteBatch, width: Float, height: Float, x: Float, y: Float, drawData: DrawState) {
//        val mult = scaleMult *

//        val width = size.width
//        val height = size.height
//        println("Drawing at " + position)
//        println("Dimensions: " +  (width * scale) + ", " +  widthToHeight))
        sb.draw(texture, x.toFloat(), y.toFloat(), width, height)
    }
}

//class AnimationDrawable(val animation: Animation<TextureRegion>, var animTime: Float, override val width: Int, override val height: Int) : Drawable {
class AnimationDrawable(val animation: Animation<TextureRegion>, var animTime: Float, override val dims: Dims2) : Drawable {
    override fun draw(sb: SpriteBatch, width: Float, height: Float,  x: Float, y: Float, drawData: DrawState) {
        animTime += drawData.delta
        val frame = animation.getKeyFrame(animTime, true)
//        val width = size.width
//        val height = size.height
        sb.draw(frame, x.toFloat(), y.toFloat(), width, height)
    }
}

