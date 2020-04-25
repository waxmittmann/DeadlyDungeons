package com.mygdx.game.entities.worldobj

import com.badlogic.gdx.math.Matrix4
import com.mygdx.game.scenegraph.SceneNode
import com.mygdx.game.scenegraph.TransformDrawable
import com.mygdx.game.scenegraph.doTransform
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.AsRect
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.linear.WrappedMatrix

//class WorldObj<S>(val prototype: WorldObjPrototype, val attributes: S, var position: Point2, var rotation: Angle, val drawState: DrawState) : AsRect {
//class WorldObj<S>(val prototype: WorldObjPrototype, val attributes: S,
//                  var position: Point2, var rotation: Angle,
//                  val drawState: DrawState) : AsRect {
//    override fun rect(): Rect2 =
//            Rect2(position.x, position.y, prototype.size.width.toDouble(),
//                    prototype.size.height.toDouble())
//
//    override fun toString(): String {
//        return "Position: $position, Dims: (${prototype.size.width}, ${prototype.size.height}), Rotation: $rotation"
//    }
//}

//class WorldObjV2<S>(val prototype: WorldObjPrototype, val attributes: S,
class WorldObjV2<S>(val prototype: SceneNode, val attributes: S,
                    var position: Point2, var rotation: Angle) : AsRect {

    override val rect: Rect2 by lazy {
        WrappedMatrix()
                .trn(position.asVector())
                .rotate(rotation)
                .transform(prototype.boundaryDims.asRect)
    }

    val originTransformDrawables: List<TransformDrawable> by lazy {
        doTransform(prototype)
    }

//            Rect2(position.x, position.y, prototype.size.width.toDouble(),
//                    prototype.size.height.toDouble())

    override fun toString(): String {
        return "Rect: $rect, Rotation: $rotation"
    }

    fun transformMatrix(): WrappedMatrix =
        WrappedMatrix().trn(position.asVector()).rotate(rotation)
}

