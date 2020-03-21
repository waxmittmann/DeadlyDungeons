package com.mygdx.game.entities

import com.mygdx.game.draw.DrawState

class Terrain(val prototype: TerrainPrototype, val drawState: DrawState) {
    override fun toString(): String {
        return prototype.toString()
    }
}
