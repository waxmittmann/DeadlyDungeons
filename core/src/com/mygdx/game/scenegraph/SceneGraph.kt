package com.mygdx.game.scenegraph

import com.mygdx.game.draw.SizedDrawable
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Vec2

sealed class SceneNode {
    abstract val boundaryDims: Dims2
}

sealed class SceneParent : SceneNode() {
    abstract fun add(proto: SceneNode): SceneParent

    abstract val children: List<SceneNode>
}

class Leaf(val drawable: SizedDrawable) : SceneNode() {
    override val boundaryDims: Dims2 = drawable.size
}

class Rotate(val rotation: Angle,
             override val children: MutableList<SceneNode> = mutableListOf()) : SceneParent() {
    override fun add(proto: SceneNode): Rotate {
        children += proto
        return this
    }

    override val boundaryDims: Dims2 by lazy {
        throw java.lang.RuntimeException("Whoops not implemented yet")
//        Dims2(0f, 0f)
    }
}

class Translate(val translation: Vec2,
                override val children: MutableList<SceneNode> = mutableListOf()) : SceneParent() {
    override fun add(proto: SceneNode): Translate {
        children += proto
        return this
    }

    override val boundaryDims: Dims2 by lazy {
        throw java.lang.RuntimeException("Whoops not implemented yet")
//        Dims2(0f, 0f)
    }
}

//class TransformedSceneLeaf(val drawable: DrawableV2.Drawable, val dims: Dims2,
//                           val matrix: WrappedMatrix)


/*
fun getLeavesSND(hp: SceneNode,
              curMatrix: WrappedMatrix = WrappedMatrix()):
        Pair<List<LeafDrawable>, List<TransformDrawable>> {
    return when (hp) {
        is Translate -> {

            val newMat = curMatrix.trn(hp.translation.xF, hp.translation.yF)

            val transforms =
            hp.children.flatMap {
                when (it) {
                    is Translate -> {
                        getLeavesSND(it, newMat)
                    }

                    is Rotate -> {

                    }

                    is Leaf -> {
                        listOf()
                    }
                }
            }



        }

        is Rotate -> hp.children.k().flatMap {
            getLeavesSND(it, curMatrix.rotate(hp.rotation.degrees)).k()
        }.toList()

        is Leaf -> listOf(TransformedSceneLeaf(hp.drawable, hp.size,
                curMatrix))
    }
}

fun getLeaves(hp: SceneNode,
              curMatrix: WrappedMatrix = WrappedMatrix()): List<TransformedSceneLeaf> {
    return when (hp) {
        is Translate -> hp.children.k().flatMap {
            getLeaves(it,
                    curMatrix.trn(hp.translation.xF, hp.translation.yF)).k()
        }.toList()

        is Rotate -> hp.children.k().flatMap {
            getLeaves(it, curMatrix.rotate(hp.rotation.degrees)).k()
        }.toList()

        is Leaf -> listOf(TransformedSceneLeaf(hp.drawable, hp.size,
                curMatrix))
    }
}

//https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_collision.html
*/