package com.mygdx.game.drawing

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2

sealed class Drawable

class DrawableTextureRegion(val texture: TextureRegion,
                            val ratio: Float) : Drawable()

class DrawableTexture(val texture: Texture, val ratio: Float = 1f) : Drawable()


class DrawableAnimation(val animation: Animation<TextureRegion>,
                        val ratio: Float, var timeAt: Float) : Drawable()

class SizedDrawable(val drawable: Drawable, val size: Dims2)

object DrawableFns {
    fun create(v: TextureRegion, heightToWidth: Float = 1f): Drawable =
            DrawableTextureRegion(v,
                    heightToWidth)

    fun create(v: Texture, ratio: Float = 1f): Drawable =
            DrawableTexture(v, ratio)

    fun drawCentered(
            drawable: SizedDrawable): (batch: Batch, deltaTimeSeconds: Float) -> Unit =
            drawCentered(
                    drawable.drawable, drawable.size)


    fun drawCentered(drawable: SizedDrawable,
                     centerPos: Point2): (batch: Batch, deltaTimeSeconds: Float) -> Unit =
            drawCentered(
                    drawable.drawable, drawable.size, centerPos)

    // Draw centered with center centerPos.
    fun drawCentered(drawable: Drawable, size: Dims2,
                     centerPos: Point2): (batch: Batch, deltaTimeSeconds: Float) -> Unit =
            { batch, deltaTime ->
                when (drawable) {
                    is DrawableTextureRegion -> batch.draw(drawable.texture,
                            centerPos.x.toFloat() - size.width / 2f,
                            centerPos.y.toFloat() - size.height / 2f,
                            size.width, size.height)

                    is DrawableTexture -> batch.draw(drawable.texture,
                            centerPos.x.toFloat() - size.width / 2f,
                            centerPos.y.toFloat() - size.height / 2f,
                            size.width, size.height)

                    is DrawableAnimation -> {
                        drawable.timeAt += deltaTime
                        val frame =
                                drawable.animation.getKeyFrame(drawable.timeAt,
                                        true)
                        batch.draw(frame,
                                centerPos.x.toFloat() - size.width / 2f,
                                centerPos.y.toFloat() - size.height / 2f,
                                size.width, size.height)
                    }
                }
            }

    // Draw centered at origin. Transform matrix before calling this.
    fun drawCentered(drawable: Drawable,
                     size: Dims2): (batch: Batch, deltaTimeSeconds: Float) -> Unit =
            { batch, deltaTime ->
                when (drawable) {
                    is DrawableTextureRegion -> batch.draw(drawable.texture,
                            -size.width / 2f, -size.height / 2f, size.width,
                            size.height)

                    is DrawableTexture -> batch.draw(drawable.texture,
                            -size.width / 2f, -size.height / 2f, size.width,
                            size.height)

                    is DrawableAnimation -> {
                        drawable.timeAt += deltaTime
                        val frame =
                                drawable.animation.getKeyFrame(drawable.timeAt,
                                        true)
                        batch.draw(frame, -size.width / 2f, -size.height / 2f,
                                size.width, size.height)
                    }
                }
            }


}
