package com.mygdx.game.draw

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.util.Dims2
import com.mygdx.game.util.Point2

class PositionedDrawable(val drawable: Drawable, val width: Float, val height: Float, val x: Float, val y: Float, val drawState: DrawState) {

    fun draw(sb: SpriteBatch) {
        drawable.draw(sb, width, height, x, y, drawState)
    }

}
