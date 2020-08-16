/*
package com.mygdx.game.util.linear

import arrow.core.extensions.set.monoidal.identity
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Matrix4.*
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.util.geometry.*
import java.lang.Double


interface Matrix4 {

    fun postMult(rhs: Matrix4): Matrix4

}

abstract class CompatibleWrappedMatrix<S> {
    abstract val matrixOps: MatrixOps<S>

    abstract fun clone(): CompatibleWrappedMatrix<S>

    abstract fun cloneRaw(): S

    //
    interface Factory<S> {
        val identity: CompatibleWrappedMatrix<S>

        fun from(s: S): CompatibleWrappedMatrix<S>

        fun from(rotation: Float): CompatibleWrappedMatrix<S> =
                identity.postRotate(rotation)

        fun from(x: Float, y: Float): CompatibleWrappedMatrix<S> =
                identity.postTranslate(x, y)
    }

    abstract val localFactory: Factory<S>

    fun preMul(other: CompatibleWrappedMatrix<S>): CompatibleWrappedMatrix<S> =
            localFactory.from(matrixOps.mul(cloneRaw(), other.cloneRaw()))

    fun postMul(other: CompatibleWrappedMatrix<S>): CompatibleWrappedMatrix<S> =
            localFactory.from(matrixOps.mul(other.cloneRaw(), cloneRaw()))

    fun postTranslate(x: Float, y: Float): CompatibleWrappedMatrix<S> = postTranslate(Vec2(x, y))

    fun postTranslate(v: Vec2): CompatibleWrappedMatrix<S> =
            localFactory.from(matrixOps.translate(cloneRaw(), v))

    // Equivalent to post?
    fun preTranslate(v: Vec2): CompatibleWrappedMatrix<S> =
            localFactory.from(matrixOps.translate(cloneRaw(), v))


    // Rotate by negative degrees because natively rotation is counter
    // clockwise and I want clockwise.
//    open fun postRotate(degrees: Float): CompatibleWrappedMatrix<S>

    fun toScale(): Vec2 {
        val s = cloneRaw()
        return Vec2(matrixOps.scaleX(s).toDouble(), LibgdxMatrixOps.scaleY(s).toDouble())
    }

    // Rotate by negative degrees because natively rotation is counter
    // clockwise and I want clockwise.
    open fun postRotate(degrees: Float): CompatibleWrappedMatrix<S> {
//        val cloned = matrixOps.clone(cloneRaw())
        val rotation = matrixOps.rotate(0f, 0f, 1f, -degrees)
        val mul = matrixOps.mul(cloneRaw(), rotation)
        return CompatibleWrappedMatrix(mul)
    }

    open fun preRotate(degrees: Float): CompatibleWrappedMatrix<S>

    open fun transform(p: Point2): Point2

    open fun toAngle(): Float

    open fun toTranslate(): Vec2

    fun toScale(): Vec2

    override fun toString(): String

    open fun absTranslate(vector: Vec2): CompatibleWrappedMatrix<S>

    fun transform(rect: Rect2): Rect2 {
        val ll = transform(rect.lowerLeft())
        val ul = transform(Point2(rect.ux(), rect.ly))
        val lr = transform(Point2(rect.lx, rect.uy()))
        val ur = transform(Point2(rect.ux(), rect.uy()))
        return Rect2.fromPoints(
                Point2(
                        Double.min(ll.x, Double.min(ul.x, Double.min(lr.x, ur.x))),
                        Double.min(ll.y, Double.min(ul.y, Double.min(lr.y, ur.y)))
                ),
                Point2(
                        Double.max(ll.x, Double.max(ul.x, Double.max(lr.x, ur.x))),
                        Double.max(ll.y, Double.max(ul.y, Double.max(lr.y, ur.y)))
                )
        )
    }
}


interface MatrixOps<Matrix> {
    fun clone(m: Matrix): Matrix

    fun scaleX(m: Matrix): Float

    fun scaleY(m: Matrix): Float

    fun identity(): Matrix

    fun mul(left: Matrix, right: Matrix): Matrix

    fun rotate(x: Float, y: Float, z: Float, degrees: Float, identity: Matrix = identity()): Matrix

    fun translate(cloneRaw: Matrix, v: Vec2): Matrix
}

object LibgdxMatrixOps : MatrixOps<Matrix4> {
    override fun clone(m: Matrix4): Matrix4 = Matrix4(m)

    override fun scaleX(m: Matrix4): Float = m.scaleX

    override fun scaleY(m: Matrix4): Float = m.scaleY

    override fun identity(): Matrix4 = Matrix4()
}

class LibgdxWrappedMatrix constructor(private val rawMatrix: Matrix4) : CompatibleWrappedMatrix<Matrix4> {

    override val matrixOps: MatrixOps<Matrix4> = LibgdxMatrixOps

    companion object LocalFactory : CompatibleWrappedMatrix.Factory<Matrix4> {
        fun from(matrix: Matrix4): CompatibleWrappedMatrix<Matrix4> =
                LibgdxWrappedMatrix(Matrix4(matrix))

        fun fromTransform(batch: Batch): CompatibleWrappedMatrix<Matrix4> =
                LibgdxWrappedMatrix(Matrix4(batch.transformMatrix))

        override val identity: CompatibleWrappedMatrix<Matrix4>
            get() = LibgdxWrappedMatrix(Matrix4())
    }

    override fun clone(): CompatibleWrappedMatrix<Matrix4> = LibgdxWrappedMatrix(Matrix4(rawMatrix))

    override fun cloneRaw(): Matrix4 = Matrix4(rawMatrix)

    override fun preMul(other: CompatibleWrappedMatrix<Matrix4>): CompatibleWrappedMatrix<Matrix4> =
            LibgdxWrappedMatrix(cloneRaw().mul(other.cloneRaw()))

    override fun postMul(other: CompatibleWrappedMatrix<Matrix4>): CompatibleWrappedMatrix<Matrix4> =
            LibgdxWrappedMatrix(other.cloneRaw().mul(rawMatrix))

    override fun postTranslate(v: Vec2): CompatibleWrappedMatrix<Matrix4> =
            LibgdxWrappedMatrix(cloneRaw().translate(v.xF, v.yF, 0f))

    // Equivalent to post?
    override fun preTranslate(v: Vec2): CompatibleWrappedMatrix<Matrix4> =
            LibgdxWrappedMatrix(Matrix4().translate(v.xF, v.yF, 0f).mul(rawMatrix))

    // Rotate by negative degrees because natively rotation is counter
    // clockwise and I want clockwise.

//            LibgdxWrappedMatrix(cloneRaw().mul(Matrix4().rotate(0f, 0f, 1f, -degrees)))
//        LibgdxWrappedMatrix(matrixOps.clone(rawMatrix).mul(Matrix4().rotate(0f, 0f, 1f, -degrees)))

    override fun preRotate(degrees: Float): CompatibleWrappedMatrix<Matrix4> {
        val rotation = matrixOps.rotate(0f, 0f, 1f, -degrees)
        return LibgdxWrappedMatrix(matrixOps.mul(rotation, rawMatrix))
    }
//            LibgdxWrappedMatrix(matrixOps.mul(matrixOps.mul(matrixOps.identity().rotate(0f, 0f, 1f, -degrees), matrixOps.clone(rawMatrix))))//.mul(cloneRaw()))

    override fun transform(p: Point2): Point2 {
        val v = Vector3(p.x.toFloat(), p.y.toFloat(), 0f)
        val vt = v.mul(rawMatrix)
        return Point2(vt.x.toDouble(), vt.y.toDouble())
    }

    fun setTransform(batch: Batch) {
        batch.transformMatrix = cloneRaw() //Matrix4(rawMatrix)
    }

    override fun toAngle(): Float {
        val quaternion: Quaternion = Quaternion()
        rawMatrix.getRotation(quaternion)

        if (quaternion.x != 0f || quaternion.y != 0f || (quaternion.angle != 0f && quaternion.z == 0f)) {
            println("${quaternion.x}  ${quaternion.y} ${quaternion.z} " + "${quaternion.angle}")
            throw RuntimeException("!")
        }
//        assert(quaternion.x == 0f)
//        assert(quaternion.y == 0f)
//        assert(quaternion.z == 1f)

//        println(quaternion.z)

        return if (quaternion.z > 0) {
            quaternion.angle
        } else {
            360 - quaternion.angle
        }
    }

    override fun toTranslate(): Vec2 {
        val x = rawMatrix.values[M03]
        val y = rawMatrix.values[M13]
        val z = rawMatrix.values[M23]

        assert(z == 0f)

        return Vec2(x.toDouble(), y.toDouble())
    }


    override fun toString(): String {
        return rawMatrix.toString()
    }

    override fun absTranslate(vector: Vec2): CompatibleWrappedMatrix<Matrix4> {
        val v = Vector3()
        rawMatrix.getTranslation(v)
        v.add(vector.xF, vector.yF, 0f)
        return LibgdxWrappedMatrix(cloneRaw().setTranslation(v))
    }

//    override fun matrix(): LibgdxWrappedMatrix {
//        TODO("Not yet implemented")
//    }

}
*/