package com.mygdx.game.entities

import com.mygdx.game.draw.Textures
import com.mygdx.game.entities.terrain.TerrainAttributes
import com.mygdx.game.entities.terrain.TerrainPrototype
import com.mygdx.game.entities.worldobj.WorldObjPrototype

class Prototypes(val textures: Textures) {
    // Terrain prototypes.
    val grass= TerrainPrototype("grass", textures.grassDrawable)
    val mud = TerrainPrototype("mud", textures.mudDrawable)
    val rocks = TerrainPrototype("rock", textures.rockDrawable, TerrainAttributes(false))

    // GameObj prototypes.
    val player = WorldObjPrototype("player", textures.playerDrawable, 50, 50)
    val yeti = WorldObjPrototype("yeti", textures.yetiDrawable, 50, 50)
    val bullet = WorldObjPrototype("bullet", textures.bulletDrawable, 25, 25)

}