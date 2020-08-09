package com.mygdx.game.entities

import com.mygdx.game.drawing.Drawable
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.textures.Textures
import com.mygdx.game.entities.terrain.TerrainAttributes
import com.mygdx.game.entities.terrain.TerrainPrototype
import com.mygdx.game.entities.worldobj.SceneNodeAttributes
import com.mygdx.game.scenegraph.*
import com.mygdx.game.textures.SizedDrawables
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Vec2


class SceneNodeData(val name: String, val sizedDrawable: SizedDrawable? = null)

fun makeData(name: String, drawable: Drawable, dims: Dims2) = SceneNodeData(name, SizedDrawable(drawable, dims))
fun makeData(name: String, sizedDrawable: SizedDrawable) = SceneNodeData(name, sizedDrawable)
fun makeData(name: String) = SceneNodeData(name, null)

class Prototypes(val textures: Textures) {
    fun dataFactory(): SceneNodeData = SceneNodeData("<null>")

    // Terrain prototypes.
    val grass = TerrainPrototype("grass", textures.grassDrawable)
    val mud = TerrainPrototype("mud", textures.mudDrawable)
    val rocks = TerrainPrototype("rock", textures.rockDrawable,
            TerrainAttributes(false))

    // GameObj prototypes.
    val player = Leaf(SceneNodeData("player", SizedDrawable(
            textures.playerDrawable, Dims2(50f, 50f))))
//            attributes = SceneNodeAttributes())

    val playerWithSword: GameParentNode by lazy {
//        SceneGraphBuilder(
//                sFactory = { SceneNodeAttributes() })
//                .leaf(textures.playerDrawable, Dims2(50f, 50f), "player")
//                .translate(10.0, 0.0, "a")
//                .leaf(textures.playerDrawable, Dims2(50f, 50f), "player")
//                .rotate(90, "swordRotate") // TODO: removed
//                .translate(10.0, 0.0, "a")
//                .leaf(textures.playerDrawable, Dims2(50f, 50f), "player")
//                .build()

        SceneGraphBuilder(dataFactory(), ::dataFactory)
                .leaf(makeData("player", textures.playerDrawable, Dims2(50f, 50f)))
                .translate(Vec2(-18.0, 0.0), makeData("preRotateSwordTranslate")) {
                    rotate(-30f, makeData("swordRotate")) {
                        translate(Vec2(0.0, 30.0), makeData("postRotateSwordTranslate")) {
                            leaf(makeData("playerSword", textures.swordDrawable, Dims2(5f, 60f)))
                        }
                    }
                }
                .build()
    }

    val yeti = Leaf(makeData("yeti", textures.yetiDrawable, Dims2(50f, 50f)))

    val bullet = Leaf(makeData("bullet", textures.bulletDrawable, Dims2(25f, 25f)))
}