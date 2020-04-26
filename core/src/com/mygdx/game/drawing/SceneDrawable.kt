package com.mygdx.game.drawing

import com.badlogic.gdx.graphics.g2d.Batch
import com.mygdx.game.drawing.scenegraph.*
import com.mygdx.game.drawing.DrawableFns.drawCentered
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Vec2
import com.mygdx.game.util.linear.ProjectionSaver
import com.mygdx.game.util.linear.WrappedMatrix

abstract class WorldDrawable {
    abstract fun draw(batch: Batch, delta: Float)
}

class SimpleDrawable(val drawable: SizedDrawable, val center: Point2)
    : WorldDrawable() {
    override fun draw(batch: Batch, delta: Float) {
        (drawCentered(drawable, center))(batch, delta)
    }
}

sealed class SceneNodeDrawable : WorldDrawable()

class LeafDrawable(
        val drawable: SizedDrawable) : SceneNodeDrawable() {
    // Assumes transforms have occurred to position object correctly.
    // TODO: This should be protected. LeafDrawable should probably not count
    // as a WorldDrawable.
    override fun draw(batch: Batch, delta: Float) {
        drawCentered(drawable)(batch, delta)
    }
}

class TransformDrawable(val matrix: WrappedMatrix,
                        val children: List<SceneNodeDrawable>) : SceneNodeDrawable() {
    override fun draw(batch: Batch, delta: Float) {
        (ProjectionSaver.doThenRestore<Unit>(
                batch)) {
            batch.transformMatrix = batch.transformMatrix.mul(matrix.get())
            for (child: SceneNodeDrawable in children) child.draw(batch, delta)
        }
    }

    fun preMult(newMat: WrappedMatrix) =
            TransformDrawable(
                    newMat.mul(matrix), children)
}

fun translateDrawable(vec: Vec2, children: List<SceneNodeDrawable>): SceneNodeDrawable =
        TransformDrawable(
                WrappedMatrix().translate(vec), children)

fun rotateDrawable(angle: Angle, children: List<SceneNodeDrawable>): SceneNodeDrawable =
        TransformDrawable(
                WrappedMatrix().rotate(angle), children)

fun doTransform(node: SceneNode): List<TransformDrawable> = when (node) {
    is Rotate -> doTransform(
            node)
    is Translate -> doTransform(
            node)
    is Leaf -> listOf(
            doTransform(node))
}

fun doTransform(leaf: Leaf): TransformDrawable =
        TransformDrawable(
                WrappedMatrix(),
                listOf(LeafDrawable(
                        leaf.drawable)))

fun doTransform(trans: SceneParent): List<TransformDrawable> {

    val transformChildren = trans.children.flatMap {
        when (it) {
            is Rotate -> doTransform(
                    it)
            is Translate -> doTransform(
                    it)
            is Leaf -> listOf()
        }
    }

    val leafChildren = trans.children.flatMap {
        when (it) {
            is Rotate -> listOf()
            is Translate -> listOf()
            is Leaf -> listOf(
                    LeafDrawable(
                            it.drawable))
        }
    }

    // If there are no children at all, drop transform.
    return if (transformChildren.isEmpty() && leafChildren.isEmpty()) {
        listOf()
    } else {
        val newMat = when (trans) {
            is Rotate -> WrappedMatrix()
                    .rotate(trans.rotation)
            is Translate -> WrappedMatrix()
                    .translate(trans.translation.xF,
                    trans.translation.yF)
        }

        // If there are no leaf children, merge this transform with its child
        // transform.
        if (leafChildren.isEmpty()) {
            transformChildren.map { it.preMult(newMat) }
        }
        // If there are leaf children, don't merge.
        else {
            listOf(TransformDrawable(
                    newMat, transformChildren + leafChildren))
        }
    }
}