package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

interface Drawable {
    val width: Int
    val height: Int
    fun draw(sb: SpriteBatch, delta: Float, x: Int, y: Int)
}

class TextureDrawable(val texture: Texture, override val width: Int, override val height: Int) : Drawable {
    override fun draw(sb: SpriteBatch, delta: Float, x: Int, y: Int) {
        sb.draw(texture, x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
    }
}

class AnimationDrawable(val animation: Animation<TextureRegion>, var animTime: Float, override val width: Int, override val height: Int) : Drawable {
    override fun draw(sb: SpriteBatch, delta: Float, x: Int, y: Int) {
        animTime += delta
        val frame = animation.getKeyFrame(animTime, true)
        sb.draw(frame, x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
    }
}

