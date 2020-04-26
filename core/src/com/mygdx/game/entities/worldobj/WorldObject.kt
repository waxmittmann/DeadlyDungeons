package com.mygdx.game.entities.worldobj

import com.mygdx.game.draw.DrawableV2
import com.mygdx.game.scenegraph.*
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

sealed class WorldObject<S> : AsRect {
    abstract val attributes: S
    abstract var position: Point2
    abstract var rotation: Angle

    override fun toString(): String {
        return "Rect: $rect, Rotation: $rotation"
    }

    abstract fun worldDrawable(): WorldDrawable
}

class WorldAabb<S>(val prototype: DrawableV2.SizedDrawable, override val attributes: S,
                   override var position: Point2, override var rotation: Angle = Angle(0))
    : WorldObject<S>() {
    override val rect: Rect2 = prototype.size.asRect.plus(position.asVector())

    override fun worldDrawable(): WorldDrawable = SimpleDrawable(prototype, position)
}


class WorldSceneNode<S>(private val prototype: SceneNode, override val attributes: S,
                        override var position: Point2, override var rotation: Angle = Angle(0)) :
        WorldObject<S>() {

    override val rect: Rect2 by lazy {
        WrappedMatrix()
                .translate(position.asVector())
                .rotate(rotation)
                .transform(prototype.boundaryDims.asRect)
    }

    val originTransformDrawables: List<TransformDrawable> by lazy {
        doTransform(prototype)
    }

    fun transformMatrix(): WrappedMatrix =
        WrappedMatrix().translate(position.asVector()).rotate(rotation)

    override fun worldDrawable(): WorldDrawable =
        translateDrawable(position.asVector(), listOf(
                rotateDrawable(rotation, originTransformDrawables)
        ))

}

