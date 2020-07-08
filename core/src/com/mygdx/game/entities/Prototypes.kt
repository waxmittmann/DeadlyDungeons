package com.mygdx.game.entities

import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.textures.Textures
import com.mygdx.game.entities.terrain.TerrainAttributes
import com.mygdx.game.entities.terrain.TerrainPrototype
import com.mygdx.game.drawing.scenegraph.Leaf
import com.mygdx.game.drawing.scenegraph.SceneGraphBuilder
import com.mygdx.game.drawing.scenegraph.SceneParent
import com.mygdx.game.entities.worldobj.SceneNodeAttributes
import com.mygdx.game.util.geometry.Dims2


class Prototypes(val textures: Textures) {
    // Terrain prototypes.
    val grass = TerrainPrototype("grass", textures.grassDrawable)
    val mud = TerrainPrototype("mud", textures.mudDrawable)
    val rocks = TerrainPrototype("rock", textures.rockDrawable,
            TerrainAttributes(false))

    // GameObj prototypes.
    val player = Leaf<SceneNodeAttributes>(SizedDrawable(
            textures.playerDrawable, Dims2(50f, 50f)),
            attributes = SceneNodeAttributes())

    val playerWithSword: SceneParent<SceneNodeAttributes> by lazy {
//        SceneGraphBuilder(
//                sFactory = { SceneNodeAttributes() })
//                .leaf(textures.playerDrawable, Dims2(50f, 50f), "player")
//                .translate(10.0, 0.0, "a")
//                .leaf(textures.playerDrawable, Dims2(50f, 50f), "player")
//                .rotate(90, "swordRotate") // TODO: removed
//                .translate(10.0, 0.0, "a")
//                .leaf(textures.playerDrawable, Dims2(50f, 50f), "player")
//                .build()

        SceneGraphBuilder(
               sFactory = { SceneNodeAttributes() })
               .leaf(textures.playerDrawable, Dims2(50f, 50f), "player")
               .translate(-18.0, 0.0, "preRotateSwordTranslate")
               .rotate(-30, "swordRotate") // TODO: removed
//                .rotate(-135, "swordRotate") // TODO: removed
////               .rotate(-180) // TODO: removed
////               .translate(0.0, 100.0)
               .translate(0.0, 30.0, "postRotateSwordTranslate")
////               .leaf(textures.swordDrawable, Dims2(5f, 200f))
               .leaf(textures.swordDrawable, Dims2(5f, 60f), "playerSword")
               .build()


    }

    val yeti = Leaf(SizedDrawable(
            textures.yetiDrawable, Dims2(50f, 50f)), attributes = SceneNodeAttributes())
    val bullet = Leaf(SizedDrawable(
            textures.bulletDrawable, Dims2(25f, 25f)), attributes = SceneNodeAttributes())
}