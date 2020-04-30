package com.mygdx.game.drawing.scenegraph

import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Vec2
import java.lang.System.err

sealed class SceneNode {
    abstract val boundaryDims: Dims2
    abstract val id: String
}

sealed class SceneParent : SceneNode() {
    abstract fun add(proto: SceneNode): SceneParent

    abstract val children: List<SceneNode>
}

class Leaf(val drawable: SizedDrawable, override val id: String = "*noid*") :
        SceneNode() {
    override val boundaryDims: Dims2 = drawable.size
}

class Rotate(val rotation: Angle,
             override val children: MutableList<SceneNode> = mutableListOf(),
             override val id: String = "*noid*"
) : SceneParent() {
    override fun add(proto: SceneNode): Rotate {
        children += proto
        return this
    }

    override val boundaryDims: Dims2 by lazy {
        throw java.lang.RuntimeException("Whoops not implemented yet")
    }
}

class Translate(val translation: Vec2,
                override val children: MutableList<SceneNode> = mutableListOf
                (), override val id: String = "*noid*") : SceneParent() {
    override fun add(proto: SceneNode): Translate {
        children += proto
        return this
    }

    override val boundaryDims: Dims2 by lazy {
//        throw java.lang.RuntimeException("Whoops not implemented yet")
        err.println("NOT IMPLE<EMTED YET!!!!")
        Dims2(0f, 0f)
    }
}

//https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_collision.html
