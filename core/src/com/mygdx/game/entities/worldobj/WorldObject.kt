package com.mygdx.game.entities.worldobj

import com.mygdx.game.collision.HasAabbBoundingBox
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.entities.makeData
import com.mygdx.game.scenegraph.*
//import com.mygdx.game.drawing.scenegraph.Leaf
//import com.mygdx.game.drawing.scenegraph.Rotate
//import com.mygdx.game.drawing.scenegraph.SceneNode
//import com.mygdx.game.drawing.scenegraph.Translate
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.drawMatrix
import com.mygdx.game.util.linear.WrappedMatrix
import space.earlygrey.shapedrawer.ShapeDrawer

fun <S> createAabb(sizedDrawable: SizedDrawable, attributes: S,
                   name: String,
         position: Point2,
        rotation: Angle = Angle(0f)) = WorldSceneNode(
        GameLeaf(makeData(name, sizedDrawable)), attributes, position,
        rotation)

class WorldSceneNode<T>(val prototype: GameNode,
        val attributes: T, var position: Point2,
        var rotation: Angle = Angle(0f)) : HasAabbBoundingBox {

    // Does not apply position & rotation transformations. So e.g. can be used
    // to check avatar against walls.
//    fun untransformedBoundingBox(): Rect2 {
//        return prototype.aabb.plus(position.asVector())
//                .plus(prototype.aabb.div(2f).asVector())
//    }

    fun debugDraw(shapeDrawer: ShapeDrawer) {
        drawMatrix(shapeDrawer, transformMatrix())
    }

//    fun points(): List<Point2> {
//        val m = translationMatrix().mul(rotationMatrix().rotate(90))
//        return prototype.points.map { m.transform(it) }
//    }

    // For now, use the untransformed bounding box. But should optionally be
    // able to use the transformed box instead. And possibly to flexibly filter
    // out parts of the prototype.
    override fun aabbBox(): Rect2 {
        return translationMatrix().postMul(rotationMatrix()).transform(calcBoundingBox(prototype).v!!)
    }

    fun translationMatrix(): WrappedMatrix =
        WrappedMatrix().postTranslate(position.asVector())

    fun rotationMatrix(): WrappedMatrix = WrappedMatrix().postRotate(rotation
            .rotate(90f).invert().degrees.toFloat())

    fun transformMatrix(): WrappedMatrix =
    WrappedMatrix().postTranslate(position.asVector()).postRotate(rotation.invert().degrees.toFloat())

    fun worldPositionedSceneNode(
            uVal: SceneNodeAttributes): GameNode =
            // TODO(wittie): Is relative correct?
           RelativeGameTranslation(position.asVector(), mutableListOf(
                    GameRotation(rotation.degrees.toFloat(), mutableListOf(prototype), makeData(""))), makeData(""))
}

