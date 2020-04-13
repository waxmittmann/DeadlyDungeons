package com.mygdx.game.entities.terrain

import com.mygdx.game.draw.Drawable


class TerrainAttributes(val passable: Boolean = true) {
    override fun toString(): String {
        return "Passable: $passable"
    }
}

class TerrainPrototype(val name: String, val drawable: Drawable,
                       val attributes: TerrainAttributes = TerrainAttributes()) {
    override fun toString(): String {
        return "$name: $attributes"
    }
}

