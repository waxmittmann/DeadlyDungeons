package com.mygdx.game.scenegraph

//import com.mygdx.game.draw.PositionedDrawable
import com.badlogic.gdx.graphics.g2d.Batch
import com.mygdx.game.draw.DrawableV2
import com.mygdx.game.draw.DrawableV2.drawCentered
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Vec2
import com.mygdx.game.util.linear.ProjectionSaver
import com.mygdx.game.util.linear.WrappedMatrix
import java.util.*

class SceneNodeBuilder {
    private val root: Translate = Translate(Vec2(0.0, 0.0))
    private var stack: Stack<SceneParent> = Stack()

    init {
        stack.push(root)
    }

    fun translate(x: Double, y: Double): SceneNodeBuilder {
        val t = Translate(Vec2(x, y))
        stack.peek().add(t)
        stack.push(t)
        return this
    }

    fun rotate(degrees: Int): SceneNodeBuilder {
        val t = Rotate(Angle.create(degrees))
        stack.peek().add(t)
        stack.push(t)
        return this
    }

    fun pop(nr: Int = 1): SceneNodeBuilder {
        if (stack.size <= nr) {
            throw RuntimeException("Cannot pop root")
        }
        (0 until nr).forEach { stack.pop() }
        return this
    }

    //    fun build(): SceneNode {
    fun build(): SceneParent {
        val hp = root
        stack.clear()
        stack.push(Translate(Vec2(0.0, 0.0)))
        return hp
    }

    fun leaf(drawable: DrawableV2.Drawable, size: Dims2): SceneNodeBuilder {
//        stack.peek().add(Leaf(drawable, size))
        stack.peek().add(Leaf(DrawableV2.SizedDrawable(drawable, size)))
        return this
    }
}

sealed class SceneNode {
    abstract val boundaryDims: Dims2
}

sealed class SceneParent : SceneNode() {
    abstract fun add(proto: SceneNode): SceneParent

    abstract val children: List<SceneNode>
}

//class Leaf(val textureRegion: TextureRegion, val size: Dims2) : SceneNode()
class Leaf(val drawable: DrawableV2.SizedDrawable) : SceneNode() {
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

class TransformedSceneLeaf(val drawable: DrawableV2.Drawable, val dims: Dims2,
                           val matrix: WrappedMatrix)


sealed class SceneNodeDrawable {
    abstract fun draw(batch: Batch, delta: Float)

}

class LeafDrawable(
        val drawable: DrawableV2.SizedDrawable) : SceneNodeDrawable() {
    override fun draw(batch: Batch, delta: Float) {
        drawCentered(drawable)(batch, delta)
    }
}

class TransformDrawable(val matrix: WrappedMatrix,
                        val children: List<SceneNodeDrawable>) : SceneNodeDrawable() {
    override fun draw(batch: Batch, delta: Float) {
//        val origTransform = WrappedMatrix.from(batch.transformMatrix)
//        val origProjection = WrappedMatrix.from(batch.projectionMatrix)

        (ProjectionSaver.doThenRestore<Unit>(batch)) {
//            batch.transformMatrix = batch.transformMatrix.mul(matrix.getInternals())
            batch.transformMatrix = batch.transformMatrix.mul(matrix.get())
            for (child: SceneNodeDrawable in children) child.draw(batch, delta)
        }

//        for (drawable in drawables)
//            drawCentered(drawable)(batch, delta)
//        batch.transformMatrix = origTransform.getInternals()
    }

    fun preMult(newMat: WrappedMatrix) =
            TransformDrawable(newMat.mul(matrix), children)
}

fun doTransform(node: SceneNode): List<TransformDrawable> = when (node) {
    is Rotate -> doTransform(node)
    is Translate -> doTransform(node)
    is Leaf -> listOf(doTransform(node))
}

//fun doTransform(trans: SceneParent, curMatrix: WrappedMatrix = WrappedMatrix()): List<TransformDrawable> {
fun doTransform(leaf: Leaf): TransformDrawable =
        TransformDrawable(WrappedMatrix(), listOf(LeafDrawable(leaf.drawable)))

fun doTransform(trans: SceneParent): List<TransformDrawable> {

    val transformChildren = trans.children.flatMap {
        when (it) {
            is Rotate -> doTransform(it)
            is Translate -> doTransform(it)
            is Leaf -> listOf()
        }
    }

    val leafChildren = trans.children.flatMap {
        when (it) {
            is Rotate -> listOf()
            is Translate -> listOf()
            is Leaf -> listOf(LeafDrawable(it.drawable))
        }
    }

    // If there are no children at all, drop transform.
    return if (transformChildren.isEmpty() && leafChildren.isEmpty()) {
        listOf()
    } else {
        val newMat = when (trans) {
            is Rotate -> WrappedMatrix().rotate(trans.rotation)
            is Translate -> WrappedMatrix().trn(trans.translation.xF,
                    trans.translation.yF)
        }

        // If there are no leaf children, merge this transform with its child
        // transform.
        if (leafChildren.isEmpty()) {
            transformChildren.map { it.preMult(newMat) }
        }
        // If there are leaf children, don't merge.
        else {
            listOf(TransformDrawable(newMat, transformChildren + leafChildren))
        }
    }
}

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