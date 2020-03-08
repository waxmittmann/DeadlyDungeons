package com.mygdx.game.draw

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.util.Dims2
import com.mygdx.game.util.Point2
import space.earlygrey.shapedrawer.ShapeDrawer


val singlePixel: TextureRegion = TextureRegion(Texture("singlepixel.png"))

class PositionedDrawable(val drawable: Drawable, val width: Float, val height: Float, val x: Float, val y: Float, val drawState: DrawState) {

    fun draw(sb: SpriteBatch) {
//        val shapeDrawer = ShapeDrawer(sb, singlePixel)
        drawable.draw(sb, width, height, x, y, drawState)
//        shapeDrawer.rectangle(x, y, width, height)
    }

}
