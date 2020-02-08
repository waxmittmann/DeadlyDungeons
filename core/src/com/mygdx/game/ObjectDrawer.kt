package com.mygdx.game

import com.badlogic.gdx.graphics.g2d.SpriteBatch

class ObjectDrawer {
    fun draw(batch: SpriteBatch, delta: Float, sceneObject: SceneObject) {
        sceneObject.drawable.draw(batch, delta, sceneObject.getX(), sceneObject.getY())
    }

    fun draw(batch: SpriteBatch, delta: Float, sceneObjects: List<SceneObject>) {
        sceneObjects.forEach { so -> draw(batch, delta, so) }
    }

    fun draw(batch: SpriteBatch, delta: Float, sceneObjects: Set<SceneObject>) {
        sceneObjects.forEach { so -> draw(batch, delta, so) }
    }
}
