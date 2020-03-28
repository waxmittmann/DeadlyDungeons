package com.mygdx.game.draw

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import space.earlygrey.shapedrawer.ShapeDrawer

class ObjectDrawer {
    val debug = false

    fun draw(batch: SpriteBatch, drawables: List<PositionedDrawable>) {
        val shapeDrawer = ShapeDrawer(batch, singlePixel)
        drawables.forEach { drawable ->
            drawable.draw(batch)
            if (debug)
                shapeDrawer.rectangle(drawable.x, drawable.y, drawable.width, drawable.height)
        }
    }
}
