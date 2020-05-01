package com.mygdx.game.drawing.scenegraph

import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Vec2
import java.lang.System.err

sealed class SceneNode<S> {
    abstract val boundaryDims: Dims2
    abstract val id: String
    abstract val attributes: S
}

sealed class SceneParent<S> : SceneNode<S>() {
    abstract fun add(proto: SceneNode<S>): SceneParent<S>

    abstract val children: List<SceneNode<S>>
}

class Leaf<S>(val drawable: SizedDrawable, override val id: String = "*noid*",
        override val attributes: S) :
        SceneNode<S>() {
    override val boundaryDims: Dims2 = drawable.size
}

class Rotate<S>(val rotation: Angle,
             override val children: MutableList<SceneNode<S>> = mutableListOf(),
             override val id: String = "*noid*", override val attributes: S) : SceneParent<S>() {
    override fun add(proto: SceneNode<S>): Rotate<S> {
        children += proto
        return this
    }

    override val boundaryDims: Dims2 by lazy {
        throw java.lang.RuntimeException("Whoops not implemented yet")
    }
}

class Translate<S>(val translation: Vec2,
                override val children: MutableList<SceneNode<S>> = mutableListOf
                (), override val id: String = "*noid*", override val attributes: S) :
        SceneParent<S>() {
    override fun add(proto: SceneNode<S>): Translate<S> {
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
