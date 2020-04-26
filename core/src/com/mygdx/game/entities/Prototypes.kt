package com.mygdx.game.entities

//import com.mygdx.game.entities.worldobj.WorldObjPrototype
//import com.mygdx.game.entities.worldobj.WorldObjPrototypeV2
import com.mygdx.game.draw.Drawable
import com.mygdx.game.draw.SizedDrawable
import com.mygdx.game.draw.Textures
import com.mygdx.game.entities.terrain.TerrainAttributes
import com.mygdx.game.entities.terrain.TerrainPrototype
import com.mygdx.game.scenegraph.Leaf
//import com.mygdx.game.entities.worldobj.AabbPrototype
//import com.mygdx.game.entities.worldobj.AabbWorldObject
import com.mygdx.game.util.geometry.Dims2

//typealias WorldObj<S> = WorldObjV2<S>

//typealias  WorldObjPrototype = WorldObjPrototypeV2

class Prototypes(val textures: Textures) {
    // Terrain prototypes.
    val grass = TerrainPrototype("grass", textures.grassDrawable)
    val mud = TerrainPrototype("mud", textures.mudDrawable)
    val rocks = TerrainPrototype("rock", textures.rockDrawable,
            TerrainAttributes(false))

    // GameObj prototypes.
//    val player = WorldObjPrototype("player", textures.playerDrawable,
//            Dims2(50f, 50f))
//    val yeti = WorldObjPrototype("yeti", textures.yetiDrawable, Dims2(50f, 50f))
//    val bullet = WorldObjPrototype("bullet", textures.bulletDrawable,
//            Dims2(25f, 25f))

//    val player = AabbPrototype(textures.playerDrawable, Dims2(50f, 50f))
//    val yeti = AabbPrototype(textures.yetiDrawable, Dims2(50f, 50f))
//    val bullet = AabbPrototype(textures.bulletDrawable, Dims2(25f, 25f))

    val player = Leaf(
            SizedDrawable(textures.playerDrawable, Dims2(50f, 50f)))
    val yeti = Leaf(SizedDrawable(textures.yetiDrawable, Dims2(50f, 50f)))
    val bullet = Leaf(SizedDrawable(textures.bulletDrawable, Dims2(25f, 25f)))
}