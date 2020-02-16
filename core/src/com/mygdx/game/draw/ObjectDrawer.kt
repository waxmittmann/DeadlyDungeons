package com.mygdx.game.draw

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.entities.SceneObject

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



    fun draw(batch: SpriteBatch, drawables: List<PositionedDrawable>) {
        println("Drawing: " + drawables.size)

        drawables.forEach { drawable -> drawable.draw(batch) }
    }

//    fun draw(batch: SpriteBatch, delta: Float, sceneObjects: Set<SceneObject>) {
//        sceneObjects.forEach { so -> draw(batch, delta, so) }
//    }
}
