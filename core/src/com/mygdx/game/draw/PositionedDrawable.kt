package com.mygdx.game.draw

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.util.Point2

class PositionedDrawable(val drawable: Drawable, val position: Point2, val drawState: DrawState) {

    fun draw(sb: SpriteBatch) {
        drawable.draw(sb, position, drawState)
    }

}
