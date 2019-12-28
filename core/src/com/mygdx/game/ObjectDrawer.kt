package com.mygdx.game

import com.badlogic.gdx.graphics.g2d.SpriteBatch

class ObjectDrawer {
    fun draw(batch: SpriteBatch, items: Iterable<SceneObject>) {
        for (item in items) {
            draw(batch, item)
        }
    }

    private fun draw(batch: SpriteBatch, items: SceneObject) {
        batch.draw(items.objectType.texture, items.x + 0.0f, items.y + 0.0f, items.objectType.width + 0.0f,
                items.objectType.height + 0.0f)
    }
}
