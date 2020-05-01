package com.mygdx.game.entities.worldobj

import com.mygdx.game.collision.HasAabbBoundingBox
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.drawing.scenegraph.Leaf
import com.mygdx.game.drawing.scenegraph.Rotate
import com.mygdx.game.drawing.scenegraph.SceneNode
import com.mygdx.game.drawing.scenegraph.Translate
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.linear.WrappedMatrix

fun <S, T> createAabb(sizedDrawable: SizedDrawable, attributes: S,
        sceneNodeAttributes: T, position: Point2, rotation: Angle = Angle(0)) =
        WorldSceneNode(Leaf(sizedDrawable, attributes = sceneNodeAttributes),
                attributes, position, rotation)

class WorldSceneNode<S, T>(val prototype: SceneNode<S>, val attributes: T,
        var position: Point2, var rotation: Angle = Angle(0)): HasAabbBoundingBox {

    // Does not apply position & rotation transformations. So e.g. can be used
    // to check avatar against walls.
    fun untransformedBoundingBox(): Rect2 {
        return prototype.boundaryDims.asRect.plus(position.asVector())
                .plus(prototype.boundaryDims.div(2f).asVector())
    }

    // For now, use the untransformed bounding box. But should optionally be
    // able to use the transformed box instead. And possibly to flexibly filter
    // out parts of the prototype.
    override fun aabbBox(): Rect2 = untransformedBoundingBox()

    fun transformMatrix(): WrappedMatrix =
            WrappedMatrix().translate(position.asVector()).rotate(rotation)

    fun worldPositionedSceneNode(uVal: S): SceneNode<S> =
            Translate<S>(position.asVector(), mutableListOf(
                    Rotate(rotation, mutableListOf(prototype), attributes = uVal)),
                    attributes = uVal)
}

