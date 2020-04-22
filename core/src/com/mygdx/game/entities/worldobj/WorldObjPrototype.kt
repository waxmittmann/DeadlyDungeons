package com.mygdx.game.entities.worldobj

import arrow.core.k
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.draw.Drawable
import com.mygdx.game.util.geometry.*
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

    fun transform(p: Point2): Point2 {
        val v = Vector3(p.x.toFloat(), p.y.toFloat(), 0f)
        val vt = v.mul(rawMatrix)
        return Point2(vt.x.toDouble(), vt.y.toDouble())
    }
}

class SceneNodeBuilder() {
    val root: Translate = Translate(Vec2(0.0, 0.0))
    var stack: Stack<SceneParent> = Stack()

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

    fun build(): SceneNode {
        val hp = root
        stack.clear()
        stack.push(Translate(Vec2(0.0, 0.0)))
        return hp
    }

    fun leaf(textureRegion: TextureRegion, size: Dims2): SceneNodeBuilder {
        stack.peek().add(Leaf(textureRegion, size))
        return this
    }
}

sealed class SceneNode
sealed class SceneParent : SceneNode() {
    abstract fun add(proto: SceneNode): SceneParent
}

class WorldObjPrototype(val name: String, val drawable: Drawable,
                        val size: Dims2) //: HierarchicalPrototype

class Leaf(val textureRegion: TextureRegion, val size: Dims2) : SceneNode()

class Rotate(val rotation: Angle,
             val children: MutableList<SceneNode> = mutableListOf()) : SceneParent() {
    override fun add(proto: SceneNode): Rotate {
        children += proto
        return this
    }
}

class Translate(val translation: Vec2,
                val children: MutableList<SceneNode> = mutableListOf()) : SceneParent() {
    override fun add(proto: SceneNode): Translate {
        children += proto
        return this
    }
}

class TransformedSceneLeaf(val textureRegion: TextureRegion,
//                           val polygon: Polygon2, val boundingRect: Rect2,
                           val rect: Rect2, //val boundingRect: Rect2,
                           val matrix: WrappedMatrix)

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

        is Leaf -> listOf(TransformedSceneLeaf(hp.textureRegion, hp.size.asRect,
                curMatrix))
    }
}

//https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_collision.html
