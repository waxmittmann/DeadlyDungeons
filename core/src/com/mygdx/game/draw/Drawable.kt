package com.mygdx.game.draw

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.util.geometry.Dims2


class DrawState(val delta: Float)


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
                when (drawable) {
                    is Drawable.DrawableTextureRegion -> batch.draw(
                            drawable.texture, -size.width / 2f,
                            -size.height / 2f, size.width / 2f,
                            size.height / 2f)

                    is Drawable.DrawableTexture -> batch.draw(drawable.texture,
                            -size.width / 2f, -size.height / 2f,
                            size.width / 2f, size.height / 2f)

                    is Drawable.DrawableAnimation -> {
                        drawable.timeAt += deltaTime
                        val frame =
                                drawable.animation.getKeyFrame(drawable.timeAt,
                                        true)
                        batch.draw(frame, -size.width / 2f, -size.height / 2f,
                                size.width / 2f, size.height / 2f)
                    }
                }
            }

    fun create(v: TextureRegion, ratio: Float = 1f): Drawable = Drawable
            .DrawableTextureRegion(v, ratio)
    fun create(v: Texture, ratio: Float = 1f): Drawable = Drawable
            .DrawableTexture(v, ratio)

//    fun draw(sb: Batch, width: Float, height: Float, x: Float, y: Float) {
//        val frame = animation.getKeyFrame(animTime, true)
//        draw(sb, frame, x, y, width, height, rotation = rotation,
//                originX = width / 2, originY = height / 2)
//    }

}

//
//interface Drawable {
//    val dims: Dims2
//    fun draw(sb: Batch, width: Float, height: Float, x: Float, y: Float,
//             rotation: Float, drawData: DrawState)
//}
//
//fun draw(sb: Batch, texture: TextureRegion, x: Float, y: Float, width: Float,
//         height: Float, originX: Float = 0.0f, originY: Float = 0.0f,
//         scaleX: Float = 1.0f, scaleY: Float = 1.0f, rotation: Float = 0.0f,
//         clockwise: Boolean = true) {
//    sb.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY,
//            90 - rotation, clockwise)
//}
//
//class TextureDrawable(private val texture: TextureRegion,
//                      override val dims: Dims2) : Drawable {
//    override fun draw(sb: Batch, width: Float, height: Float, x: Float,
//                      y: Float, rotation: Float, drawData: DrawState) {
//        draw(sb, texture, x, y, width, height, rotation = rotation,
//                originX = width / 2, originY = height / 2)
//    }
//}
//
//class AnimationDrawable(val animation: Animation<TextureRegion>,
//                        var animTime: Float,
//                        override val dims: Dims2) : Drawable {
//    override fun draw(sb: Batch, width: Float, height: Float, x: Float,
//                      y: Float, rotation: Float, drawData: DrawState) {
//        // TODO: DrawData should have the actual delta.
//        animTime += drawData.delta
//        animTime += 0.0025f
//        val frame = animation.getKeyFrame(animTime, true)
//        draw(sb, frame, x, y, width, height, rotation = rotation,
//                originX = width / 2, originY = height / 2)
//    }
//}

