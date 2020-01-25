package com.mygdx.game

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class ObjectDrawer {
    fun draw(batch: SpriteBatch, items: Iterable<SceneObject>) {
        for (item in items) {
            println("Drawing $item")
            draw(batch, item)
        }
    }

    fun draw(batch: SpriteBatch, items: SceneObject) {
        batch.draw(items.objectType.texture, items.xc + 0.0f, items.yc + 0.0f, items.objectType.width + 0.0f,
                items.objectType.height + 0.0f)
    }

    fun draw(batch: SpriteBatch, sprite: Sprite) {
//        batch.draw(sprite, 50, 50, 50, 50)
        sprite.draw(batch)
    }

    fun draw(batch: SpriteBatch, animation: Animation<TextureRegion>, stateTime: Float) {
        val currentFrame: TextureRegion = animation.getKeyFrame(stateTime, true)
        batch.draw(currentFrame, 50f, 50f, 50f, 15f)
    }
}
