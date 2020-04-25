package com.mygdx.game.draw

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Matrix4
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.linear.WrappedMatrix

object DrawableV2 {
    sealed class Drawable {
        class DrawableTextureRegion(val texture: TextureRegion,
                                    val ratio: Float) : Drawable()

        class DrawableTexture(val texture: Texture,
                              val ratio: Float) : Drawable()


        class DrawableAnimation(val animation: Animation<TextureRegion>,
                                val ratio: Float,
                                var timeAt: Float) : Drawable()
    }

    class SizedDrawable(val drawable: Drawable, val size: Dims2)

    fun drawCentered(
            drawable: SizedDrawable): (batch: Batch, deltaTimeSeconds: Float) -> Unit =
            drawCentered(drawable.drawable, drawable.size)

    fun drawCentered(drawable: Drawable,
                     size: Dims2): (batch: Batch, deltaTimeSeconds: Float) -> Unit =
            { batch, deltaTime ->
                println("Inside draw:\n${batch.projectionMatrix}\n")


                val wm = WrappedMatrix(batch.projectionMatrix)
//                val wm2 = wm.trn(200f, 200f)
//                batch.projectionMatrix = wm2.get()

                println("Projection. Translate: ${wm.toTranslate()}, Rotate: ${wm.toAngle()}")

//                batch.projectionMatrix = WrappedMatrix().getInternals()
//                batch.projectionMatrix = WrappedMatrix().get()

                when (drawable) {
                    is Drawable.DrawableTextureRegion -> batch.draw(
                            drawable.texture, -size.width / 2f,
                            -size.height / 2f, size.width, size.height)

                    is Drawable.DrawableTexture -> batch.draw(drawable.texture,
                            -size.width / 2f, -size.height / 2f, size.width,
                            size.height)

                    is Drawable.DrawableAnimation -> {
                        drawable.timeAt += deltaTime
                        val frame =
                                drawable.animation.getKeyFrame(drawable.timeAt,
                                        true)
                        batch.draw(frame, -size.width / 2f, -size.height / 2f,
                                size.width, size.height)
                    }
                }
            }

    fun create(v: TextureRegion, ratio: Float = 1f): Drawable =
            Drawable.DrawableTextureRegion(v, ratio)

    fun create(v: Texture, ratio: Float = 1f): Drawable =
            Drawable.DrawableTexture(v, ratio)
}
