package com.mygdx.game.draw

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion


class WrappingIterator(private val textureCollection: TextureCollection) {
    var colAt = 0
    var rowAt = 0

    fun <T>thenNext(fn: () -> (T)): T {
        val t: T = fn()
        next()
        return t
    }

    fun reset() {
        colAt = 0
        rowAt = 0
    }

    fun cur(): TextureRegion =  textureCollection.get(textureCollection.rows - rowAt - 1, colAt)

    fun next(): TextureRegion {
        val r = cur()
        colAt += 1

        if (colAt >= textureCollection.cols) {
            rowAt += 1
            colAt = 0
        }

        if (rowAt >= textureCollection.rows) {
            rowAt = 0
            colAt = 0
        }
        return r
    }
}

class TextureCollection(texture: Texture, val cols: Int, val rows: Int) {
    private val subTextures: Array<Array<TextureRegion>> =
            TextureRegion.split(texture, texture.width / cols,
                    texture.height / rows)

    fun get(r: Int, c: Int): TextureRegion {
        if (c < 0 || c >= cols || r < 0 || r >= rows)
            throw RuntimeException("$r $c is out of bounds (${cols-1}, " +
                    "${rows-1})")
//        println("Returning $r $c")
        return subTextures[r][c]
    }

    fun get(at: Int): TextureRegion {
        if (at >= cols * rows) {
            throw RuntimeException("$at is out of bounds (${cols * rows})")
        }
        return subTextures[at / cols][at % cols]
    }

    fun iterator(): WrappingIterator = WrappingIterator(this)
}

