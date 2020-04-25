package com.mygdx.game.entities.terrain

import com.mygdx.game.draw.DrawableV2

class Terrain(val prototype: TerrainPrototype) {
    val drawable: DrawableV2.Drawable = prototype.drawable

    override fun toString(): String {
        return prototype.toString()
    }
}
