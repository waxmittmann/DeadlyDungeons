package com.mygdx.game.entities.terrain

import com.mygdx.game.draw.Drawable

class Terrain(val prototype: TerrainPrototype) {
    val drawable: Drawable = prototype.drawable

    override fun toString(): String {
        return prototype.toString()
    }
}
