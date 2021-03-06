package com.mygdx.game.draw

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.util.geometry.Dims2


class DrawState(val delta: Float)

interface Drawable {
    val dims: Dims2
    fun draw(sb: Batch, width: Float, height: Float, x: Float, y: Float,
             rotation: Float, drawData: DrawState)
}

fun draw(sb: Batch, texture: TextureRegion, x: Float, y: Float, width: Float,
         height: Float, originX: Float = 0.0f, originY: Float = 0.0f,
         scaleX: Float = 1.0f, scaleY: Float = 1.0f, rotation: Float = 0.0f,
         clockwise: Boolean = true) {
    sb.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY,
            90 - rotation, clockwise)
}

class TextureDrawable(private val texture: TextureRegion,
                      override val dims: Dims2) : Drawable {
    override fun draw(sb: Batch, width: Float, height: Float, x: Float,
                      y: Float, rotation: Float, drawData: DrawState) {
        draw(sb, texture, x, y, width, height, rotation = rotation,
                originX = width / 2, originY = height / 2)
    }
}

class AnimationDrawable(val animation: Animation<TextureRegion>,
                        var animTime: Float,
                        override val dims: Dims2) : Drawable {
    override fun draw(sb: Batch, width: Float, height: Float, x: Float,
                      y: Float, rotation: Float, drawData: DrawState) {
        // TODO: DrawData should have the actual delta.
        animTime += drawData.delta
        animTime += 0.0025f
        val frame = animation.getKeyFrame(animTime, true)
        draw(sb, frame, x, y, width, height, rotation = rotation,
                originX = width / 2, originY = height / 2)
    }
}

