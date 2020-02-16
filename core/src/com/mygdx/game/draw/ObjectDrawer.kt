package com.mygdx.game.draw

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.util.Rect2

//class ObjectDrawer {
//    fun draw(batch: SpriteBatch, delta: Float, sceneObject: SceneObject) {
//        sceneObject.drawable.draw(batch, delta, sceneObject.getX(), sceneObject.getY())
//    }
//
//    fun draw(batch: SpriteBatch, delta: Float, sceneObjects: List<SceneObject>) {
//        sceneObjects.forEach { so -> draw(batch, delta, so) }
//    }
//
//    fun draw(batch: SpriteBatch, delta: Float, sceneObjects: Set<SceneObject>) {
//        sceneObjects.forEach { so -> draw(batch, delta, so) }
//    }
//}

class ObjectDrawer {
//    fun draw(batch: SpriteBatch, positionedDrawable: PositionedDrawable) {
//        positionedDrawable.
//        sceneObject.drawable.draw(batch, delta, sceneObject.getX(), sceneObject.getY())
//    }

    class DebugRect(val x: Float, val y: Float, val width: Float, val height: Float)

    val debug = true
    val shapeRenderer = {
        val sr = ShapeRenderer()
        sr.setAutoShapeType(true)
        sr
    }()

    fun draw(batch: SpriteBatch, drawables: List<PositionedDrawable>) {
        println("Drawing: " + drawables.size)

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

//    fun draw(batch: SpriteBatch, delta: Float, sceneObjects: Set<SceneObject>) {
//        sceneObjects.forEach { so -> draw(batch, delta, so) }
//    }
}
