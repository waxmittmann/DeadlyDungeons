package com.mygdx.game.draw

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class ObjectDrawer {
    class DebugRect(val x: Float, val y: Float, val width: Float, val height: Float)

    val debug = true
    val shapeRenderer = {
        val sr = ShapeRenderer()
        sr.setAutoShapeType(true)
        sr
    }()

    fun draw(batch: SpriteBatch, drawables: List<PositionedDrawable>) {
//        println("Drawing: " + drawables.size)

        var debugRects = emptyList<DebugRect>()
        drawables.forEach { drawable ->
            if (debug)
                debugRects += DebugRect(drawable.x, drawable.y, drawable.width, drawable.height)

            drawable.draw(batch)
        }

//        shapeRenderer.begin()
//        debugRects.forEach { dr ->
//            shapeRenderer.rect(dr.x, dr.y, dr.width, dr.height)
//        }
//        shapeRenderer.end()
    }

}
