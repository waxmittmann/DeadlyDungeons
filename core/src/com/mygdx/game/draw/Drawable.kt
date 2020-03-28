package com.mygdx.game.draw

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.util.Dims2


class DrawState(val delta: Float)

interface Drawable {
    val dims: Dims2
    fun draw(sb: SpriteBatch, width: Float, height: Float, x: Float, y: Float, drawData: DrawState)
}

fun draw2(sb: SpriteBatch, texture: TextureRegion, x: Float, y: Float, width: Float, height: Float, originX: Float = 0.0f,
         originY: Float = 0.0f, scaleX: Float = 1.0f, scaleY: Float = 1.0f, rotation: Float = 0.0f,
         clockwise: Boolean = false) {
    sb.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, clockwise)

//    public void draw (TextureRegion region, float x, float y, float originX, float originY, float width, float height,
//            float scaleX, float scaleY, float rotation, boolean clockwise) {
}

//class TextureDrawable(val texture: Texture, override val dims: Dims2) : Drawable {
class TextureDrawable(val texture: TextureRegion, override val dims: Dims2) : Drawable {
    override fun draw(sb: SpriteBatch, width: Float, height: Float, x: Float, y: Float, drawData: DrawState) {
        sb.draw(texture, x, y, width, height)
    }
}

class AnimationDrawable(val animation: Animation<TextureRegion>, var animTime: Float, override val dims: Dims2) : Drawable {
    override fun draw(sb: SpriteBatch, width: Float, height: Float,  x: Float, y: Float, drawData: DrawState) {
        animTime += drawData.delta
        val frame = animation.getKeyFrame(animTime, true)
        sb.draw(frame, x, y, width, height)
    }
}

