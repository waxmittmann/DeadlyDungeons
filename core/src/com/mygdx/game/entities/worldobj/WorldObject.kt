package com.mygdx.game.entities.worldobj

import com.mygdx.game.drawing.*
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.drawing.scenegraph.*
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
        return "Rect: ${rect()}, Rotation: $rotation"
    }

    abstract fun worldDrawable(): WorldDrawable
}

class WorldAabb<S>(val prototype: SizedDrawable, override val attributes: S,
                   override var position: Point2, override var rotation: Angle = Angle(0))
    : WorldObject<S>() {
    override fun rect(): Rect2 = prototype.size.asRect.plus(position.asVector())

    override fun worldDrawable(): WorldDrawable =
            SimpleDrawable(prototype,
                    position)
}


class WorldSceneNode<S>(private val prototype: SceneNode, override val attributes: S,
                        override var position: Point2, override var rotation: Angle = Angle(0)) :
        WorldObject<S>() {

    override fun rect(): Rect2 {
        // TODO: not strictly correct since it doesn't rotate the bounding box.
//        val simp = prototype.boundaryDims.asRect.plus(position.asVector())
//                .minus(prototype.boundaryDims.div(2f).asVector())
        val smp = prototype.boundaryDims.asRect
                .plus(position.asVector())
                .plus(prototype.boundaryDims.div(2f).asVector())


//        val mat = WrappedMatrix()
//                .translate(position.asVector())
//                .translate(prototype.boundaryDims.div(2f).asVector().invert())
//                .rotate(rotation)

//        println("As rect: ${prototype.boundaryDims.asRect}")

//        println("Converting: ${prototype.boundaryDims}")
//        println("Center: ${position}, Converted: ${mat.transform(position)}")
//        println("Center: ${position}, Converted: ${mat.transform(Point2(0.0,
//                0.0))}")

//        println("50/50: (50, 50), Converted: ${mat.transform(Point2(50.0,
//                50.0))}")

//        val comp = mat.transform(prototype.boundaryDims.asRect)
//
//
//        println("CHECKING!!! $simp $comp")
//        assert(simp == comp)

        return simp
    }

    val originTransformDrawables: List<TransformDrawable> by lazy {
        doTransform(prototype)
    }

    fun transformMatrix(): WrappedMatrix =
        WrappedMatrix().translate(position.asVector()).rotate(rotation)

    override fun worldDrawable(): WorldDrawable =
            translateDrawable(
                    position.asVector(),
                    listOf(rotateDrawable(
                            rotation, originTransformDrawables, "noid")),
                    "noid")

}

