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

class TextureDrawable(val texture: Texture, override val dims: Dims2) : Drawable {
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

