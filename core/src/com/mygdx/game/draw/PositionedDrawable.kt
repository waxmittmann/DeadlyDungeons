package com.mygdx.game.draw

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion


val singlePixel: TextureRegion = TextureRegion(Texture("singlepixel.png"))

class PositionedDrawable(val drawable: Drawable, val width: Float, val height: Float, val x: Float, val y: Float, val rotation: Float, val drawState: DrawState) {

    fun draw(sb: SpriteBatch) {
//        val shapeDrawer = ShapeDrawer(sb, singlePixel)
//        drawable.draw(sb, width, height, x, y, rotation + 90.0f, drawState)
        drawable.draw(sb, width, height, x, y, rotation, drawState)
//        shapeDrawer.rectangle(x, y, width, height)
    }

}
