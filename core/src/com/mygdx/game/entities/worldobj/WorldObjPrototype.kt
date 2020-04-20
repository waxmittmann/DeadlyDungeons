package com.mygdx.game.entities.worldobj

import arrow.core.k
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Matrix4
import com.mygdx.game.draw.Drawable
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2
import java.util.*


class WrappedMatrix(private val rawMatrix: Matrix4 = Matrix4()) {
    companion object Factory {
        fun from(copyFrom: WrappedMatrix): WrappedMatrix =
                WrappedMatrix(copyFrom.get())

        fun from(matrix: Matrix4): WrappedMatrix =
                WrappedMatrix(Matrix4(matrix))
    }

    fun mul(other: WrappedMatrix): WrappedMatrix =
            WrappedMatrix(Matrix4(rawMatrix).mul(other.rawMatrix))

    fun trn(x: Float, y: Float): WrappedMatrix =
            WrappedMatrix(Matrix4(rawMatrix).translate(x, y, 0f))

    fun trn(v: Vec2): WrappedMatrix =
            WrappedMatrix(Matrix4(rawMatrix).translate(v.xF, v.yF, 0f))

    fun rotate(degrees: Int): WrappedMatrix = WrappedMatrix(
            Matrix4(rawMatrix).rotate(0f, 0f, 1f, degrees.toFloat()))

    fun rotate(angle: Angle): WrappedMatrix = WrappedMatrix(
            Matrix4(rawMatrix).rotate(0f, 0f, 1f, angle.degrees.toFloat()))

    fun get(): Matrix4 = Matrix4(rawMatrix)

    fun getInternals(): Matrix4 = rawMatrix
}

object MatrixUtils {
//    fun
}

class HierarchicalPrototypeBuilder() {
    val root: PrototypeTranslate = PrototypeTranslate(Vec2(0.0, 0.0))
    var stack: Stack<HierarchicalParent> = Stack()

    init {
        stack.push(root)
    }

    fun translate(x: Double, y: Double): HierarchicalPrototypeBuilder {
        val t = PrototypeTranslate(Vec2(x, y))
        stack.peek().add(t)
        stack.push(t)
        return this
    }


    fun rotate(degrees: Int): HierarchicalPrototypeBuilder {
        val t = PrototypeRotate(Angle.create(degrees))
        stack.peek().add(t)
        stack.push(t)
        return this
    }

    fun pop(nr: Int = 1): HierarchicalPrototypeBuilder {
        if (stack.size <= nr) {
            throw RuntimeException("Cannot pop root")
        }
        (0 until nr).forEach { stack.pop() }
        return this
    }

    fun build(): HierarchicalPrototype {
        val hp = root
        stack.clear()
        stack.push(PrototypeTranslate(Vec2(0.0, 0.0)))
        return hp
    }

    fun leaf(textureRegion: TextureRegion,
             size: Dims2): HierarchicalPrototypeBuilder {
        stack.peek().add(PrototypeLeaf(textureRegion, size))
        return this
    }
}

sealed class HierarchicalPrototype
sealed class HierarchicalParent : HierarchicalPrototype() {
    abstract fun add(proto: HierarchicalPrototype): HierarchicalParent
}

class WorldObjPrototype(val name: String, val drawable: Drawable,
                        val size: Dims2) //: HierarchicalPrototype

//class PrototypeLeaf : HierarchicalPrototype()
//class PrototypeTransform(val rotation: Angle, val transform: Vec2,
//                         children: List<HierarchicalPrototype>) : HierarchicalPrototype


//class PrototypeLeaf(val drawable: Drawable, val size: Dims2) : HierarchicalPrototype
class PrototypeLeaf(val textureRegion: TextureRegion,
                    val size: Dims2) : HierarchicalPrototype()

class PrototypeRotate(val rotation: Angle,
                      val children: MutableList<HierarchicalPrototype> = mutableListOf()) : HierarchicalParent() {
    override fun add(proto: HierarchicalPrototype): PrototypeRotate {
        children += proto
        return this
    }
}

class PrototypeTranslate(val translation: Vec2,
                         val children: MutableList<HierarchicalPrototype> = mutableListOf()) : HierarchicalParent() {
    override fun add(proto: HierarchicalPrototype): PrototypeTranslate {
        children += proto
        return this
    }
}

class TransformedPrototype(val textureRegion: TextureRegion,
//                           val polygon: Polygon2, val boundingRect: Rect2,
                           val rect: Rect2, //val boundingRect: Rect2,
                           val matrix: WrappedMatrix)


//fun draw(sb: Batch, texture: TextureRegion, x: Float, y: Float, width: Float,
//         height: Float, originX: Float = 0.0f, originY: Float = 0.0f,
//         scaleX: Float = 1.0f, scaleY: Float = 1.0f, rotation: Float = 0.0f,
//         clockwise: Boolean = true) {
//    sb.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY,
//            90 - rotation, clockwise)
//}

//fun draw(batch: Batch, hp: TransformedPrototype) {
//    val prevMatrix = Matrix4(batch.transformMatrix)
////    batch.transformMatrix = hp.matrix.mul(batch.transformMatrix)
//    batch.transformMatrix = batch.transformMatrix.mul(hp.matrix)
//    batch.draw(hp.textureRegion, 0f, 0f, hp.rect.widthF, hp.rect.heightF)
//    batch.transformMatrix = prevMatrix
//}

fun resolveHierarchicalPrototype(hp: HierarchicalPrototype,
//                                 curMatrix: Matrix4 = Matrix4()): List<TransformedPrototype> {
                                 curMatrix: WrappedMatrix = WrappedMatrix()): List<TransformedPrototype> {
    return when (hp) {
        is PrototypeTranslate -> {
//            val newMatrix =
//                    Matrix4(curMatrix).trn(hp.translation.xF, hp.translation.yF, 0f)

            val newMatrix = curMatrix.trn(hp.translation.xF, hp.translation.yF)
//                    Matrix4().trn(hp.translation.xF, hp.translation.yF, 0f)
//                            .mul(curMatrix)

            hp.children.k().flatMap {
                resolveHierarchicalPrototype(it, newMatrix).k()
            }.toList()
        }
        is PrototypeRotate -> {
//            val newMatrix = Matrix4(curMatrix).rotate(0f, 0f, 1f, hp.rotation.degrees.toFloat())

            val newMatrix = curMatrix.rotate(hp.rotation.degrees)
//                    Matrix4().rotate(0f, 0f, 1f, hp.rotation.degrees.toFloat())
//                            .mul(curMatrix)

            hp.children.k().flatMap {
                resolveHierarchicalPrototype(it, newMatrix).k()
            }.toList()
        }
        is PrototypeLeaf -> {
//            listOf(TransformedPrototype(hp.textureRegion, hp.size.asRect,
//                    curMatrix.getInternals()))
            listOf(TransformedPrototype(hp.textureRegion, hp.size.asRect,
                    curMatrix))
        }
    }
}

//https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_collision.html
//fun resolveHierarchicalPrototype(curMatrix: Matrix4, hp: HierarchicalPrototype):
//        List<TransformedPrototype> {
//
//    when (hp) {
//        is PrototypeTranslate -> {
//            val newMatrix = Matrix4(curMatrix).trn(hp.translation.xF, hp
//                    .translation
//                    .yF, 0f)
//            hp.children.k().flatMap {
//                resolveHierarchicalPrototype(newMatrix, hp).k()
//            }.toList()
//        }
//        is PrototypeRotate -> {
//            val newMatrix = Matrix4(curMatrix).rotate(0f, 0f, 1f, hp.rotation
//                    .radians.toFloat())
//            hp.children.k().flatMap {
//                resolveHierarchicalPrototype(newMatrix, hp).k()
//            }.toList()
//        }
//        is PrototypeLeaf -> {
//            val world: World = World(Vector2(0f, 0f), true)
//            val bodyDef: BodyDef = BodyDef()
//            bodyDef.active = true
////            bodyDef.position
//            bodyDef.type
//            world.createBody()
////            val body = Body(world)
//            listOf(TransformedPrototype(hp.textureRegion))
//        }
//    }
//}
//
