package com.mygdx.game.entities.worldobj

import arrow.core.k
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.draw.Drawable
import com.mygdx.game.scenegraph.SceneNode
import com.mygdx.game.util.geometry.*
import com.mygdx.game.util.linear.WrappedMatrix
import java.util.*


//class WorldObjPrototype(val name: String, val drawable: Drawable,
//                        val size: Dims2) //: HierarchicalPrototype


/*
sealed class WorldObjPrototype {
    abstract val boundaryDims: Dims2
}

class SceneNodePrototype(val root: SceneNode) : WorldObjPrototype() {
    override val boundaryDims: Dims2 by lazy { root.boundaryDims }


//    fun doThat() {
//        root.
//    }
}


class AabbPrototype(val drawable: Drawable, val size: Dims2) : WorldObjPrototype() //:
{
    override val boundaryDims: Dims2 = size
}
*/

// HierarchicalPrototype

//class WorldObjPrototypeV2(val root: SceneNode) //: HierarchicalPrototype

