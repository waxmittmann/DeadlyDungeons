package com.mygdx.game.entities

import com.mygdx.game.draw.DrawState
import com.mygdx.game.draw.Drawable

class Terrain(val prototype: TerrainPrototype, val drawState: DrawState) {
    override fun toString(): String {
        return prototype.toString()
    }
}
