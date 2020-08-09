package com.mygdx.game.entities.worldobj

import arrow.core.invalid
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
import com.mygdx.game.util.linear.WrappedMatrix
import com.mygdx.game.util.drawMatrix
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
//    override fun aabbBox(): Rect2 = untransformedBoundingBox()
    override fun aabbBox(): Rect2 {
        /*
//        return transformMatrix().transform(prototype.aabb).asAabb
//        return positionTransformMatrix().transform(prototype.aabb).asAabb
//        return positionTransformMatrix().transform(prototype.aabb).asAabb
//        return centeredTransformMatrix().transform(prototype.aabb).asAabb
//        return centeredTransformMatrix().transform(prototype.aabb).asAabb
//        return rotationMatrix().transform(prototype.aabb).asAabb
//        (-63.00002670288086, 0.0, 50.0, 80.44226837158203)
        val p = prototype.aabb
//        println(prototype.aabb)

        val p2 = prototype.aabb3(WrappedMatrix())

        return translationMatrix().mul(rotationMatrix()).transform(prototype
                .aabb)
                .asAabb
        */


//        val aabbOrig = prototype.aabb
//        val aabbCentered = aabbOrig.center()
//        return translationMatrix().mul(rotationMatrix()).transform(prototype
//                .aabb.center())
//                .asAabb

//        return translationMatrix().postMul(rotationMatrix()).transform(prototype
//                .aabb.center())
//                .asAabb


//        translationMatrix().postMul(rotationMatrix()).transform(calcBoundingBox(prototype).v!!.center())
        return translationMatrix().postMul(rotationMatrix()).transform(calcBoundingBox(prototype).v!!)

//        return prototype.aabb2(transformMatrix())
//        return prototype.aabb2(centeredTransformMatrix())
//        return prototype.aabb2(WrappedMatrix())
//        val transformed = Rect2.aabbFrom(SceneGraphBuilder {
//            SceneNodeAttributes() }
//                .translate(position.asVector())
//                .rotate(rotation)
//                .merge(prototype)
//                .build()
//                .polygon2_4(WrappedMatrix())
//        )


        // Wrong order.... ?
        /*
        val protoAabb = prototype.aabb
        val transformed = transformMatrix().transform(protoAabb.minus(
                Vec2(protoAabb.asDims.width / 2.0, protoAabb.asDims.height / 2.0)))
                .asAabb
        */


//        println("Transformed aabb: $transformed")
//        return transformed
    }

    fun translationMatrix(): WrappedMatrix =
//            WrappedMatrix().translate(position.asVector())
        WrappedMatrix().postTranslate(position.asVector())

    fun rotationMatrix(): WrappedMatrix = WrappedMatrix().postRotate(rotation
            .rotate(90f).invert().degrees.toFloat())

//    fun rotationMatrix(): WrappedMatrix = WrappedMatrix().rotate(rotation)



//    fun rotationMatrix(): WrappedMatrix = WrappedMatrix().rotate(rotation)

    fun transformMatrix(): WrappedMatrix =
//            WrappedMatrix().translate(position.asVector()).rotate(rotation)
//            WrappedMatrix().translate(position.asVector()).rotate(rotation)
    WrappedMatrix().postTranslate(position.asVector()).postRotate(rotation.invert().degrees.toFloat())
//                    .rotate(90))
//    WrappedMatrix().translate(position.asVector())

    /*
    fun centeredTransformMatrix(): WrappedMatrix =
//            WrappedMatrix().translate(position.asVector()).rotate(rotation)
            transformMatrix().postTranslate(+prototype.aabb.widthF / 2f,
                    +prototype.aabb.heightF / 2f)
//    WrappedMatrix().translate(position.asVector())
     */

    fun worldPositionedSceneNode(
            uVal: SceneNodeAttributes): GameNode =
            // TODO(wittie): Is relative correct?
           RelativeGameTranslation(position.asVector(), mutableListOf(
                    GameRotation(rotation.degrees.toFloat(), mutableListOf(prototype), makeData(""))), makeData(""))
}

