package com.mygdx.game.entities

import com.mygdx.game.draw.Textures
import com.mygdx.game.entities.terrain.TerrainAttributes
import com.mygdx.game.entities.terrain.TerrainPrototype
import com.mygdx.game.entities.worldobj.WorldObjPrototype
import com.mygdx.game.util.geometry.Dims2

class Prototypes(val textures: Textures) {
    // Terrain prototypes.
    val grass= TerrainPrototype("grass", textures.grassDrawable)
    val mud = TerrainPrototype("mud", textures.mudDrawable)
    val rocks = TerrainPrototype("rock", textures.rockDrawable, TerrainAttributes(false))

    // GameObj prototypes.
    val player = WorldObjPrototype("player", textures.playerDrawable, Dims2(50f, 50f))
    val yeti = WorldObjPrototype("yeti", textures.yetiDrawable, Dims2(50f, 50f))
    val bullet = WorldObjPrototype("bullet", textures.bulletDrawable, Dims2(25f, 25f))

}