package com.mygdx.game.entities

import com.mygdx.game.draw.Textures

class Prototypes(val textures: Textures) {
    // Terrain prototypes.
    val grass= TerrainPrototype("grass", textures.grassDrawable)
    val mud = TerrainPrototype("mud", textures.mudDrawable)
    val rocks = TerrainPrototype("rock", textures.rockDrawable)

    // GameObj prototypes.
    val player = WorldObjPrototype("player", textures.playerDrawable, 50, 50, Attributes())

}