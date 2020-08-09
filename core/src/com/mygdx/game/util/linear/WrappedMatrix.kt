package com.mygdx.game.util.linear

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Matrix4.*
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.util.geometry.*
import java.lang.Double


class WrappedMatrix internal constructor(
        private val rawMatrix: Matrix4 = Matrix4()) {

    fun matrix(): Matrix4 = Matrix4(rawMatrix)

    companion object Factory {
        val identity = WrappedMatrix()

        fun from(copyFrom: WrappedMatrix): WrappedMatrix =
//                WrappedMatrix(copyFrom.get())
                WrappedMatrix(copyFrom.matrix())

        fun from(matrix: Matrix4): WrappedMatrix =
                WrappedMatrix(Matrix4(matrix))

        fun fromTransform(batch: Batch): WrappedMatrix =
                WrappedMatrix(Matrix4(batch.transformMatrix))

        fun from(rotation: Float): WrappedMatrix =
                WrappedMatrix().postRotate(rotation)

        fun from(x: Float, y: Float): WrappedMatrix =
                WrappedMatrix().postTranslate(x, y)
    }

    fun preMul(other: WrappedMatrix): WrappedMatrix =
//            WrappedMatrix(Matrix4(rawMatrix).mul(other.rawMatrix))
            WrappedMatrix(other.matrix().mul(matrix()))

    fun postMul(other: WrappedMatrix): WrappedMatrix =
//            WrappedMatrix(Matrix4(rawMatrix).mul(other.rawMatrix))
            WrappedMatrix(matrix().mul(other.matrix()))

    fun postTranslate(x: Float, y: Float): WrappedMatrix =
//            WrappedMatrix(Matrix4(rawMatrix).translate(x, y, 0f))
            WrappedMatrix(matrix().translate(x, y, 0f))

    fun postTranslate(v: Vec2): WrappedMatrix =
//            WrappedMatrix(Matrix4(rawMatrix).translate(v.xF, v.yF, 0f))
            WrappedMatrix(matrix().translate(v.xF, v.yF, 0f))

    // Equivalent to post?
    fun preTranslate(v: Vec2): WrappedMatrix =
//            WrappedMatrix()
            WrappedMatrix(Matrix4().translate(v.xF, v.yF, 0f).mul(matrix()))

//            WrappedMatrix().postTranslate(v).preMul(this)

//    fun translate(x: Float, y: Float): WrappedMatrix =
//            WrappedMatrix(Matrix4(rawMatrix).translate(x, y, 0f))

    // Rotate by negative degrees because natively rotation is counter
    // clockwise and I want clockwise.
    fun postRotate(degrees: Float): WrappedMatrix =
//            WrappedMatrix(Matrix4(this.rawMatrix).mul(Matrix4().rotate(0f, 0f, 1f, -degrees)))
            WrappedMatrix(matrix().mul(Matrix4().rotate(0f, 0f, 1f, -degrees)))

    fun preRotate(degrees: Float): WrappedMatrix =
//            WrappedMatrix(Matrix4().rotate(0f, 0f, 1f, -degrees).mul(Matrix4(this.rawMatrix)))
            WrappedMatrix(Matrix4().rotate(0f, 0f, 1f, -degrees).mul(matrix()))
//            this.preMul(identity.postRotate(degrees))

//    fun rotate(angle: Angle): WrappedMatrix = WrappedMatrix(
//            Matrix4(rawMatrix).rotate(0f, 0f, 1f, -angle.degrees.toFloat()))
//            Matrix4(rawMatrix).rotate(0f, 0f, 1f, -angle.degrees.toFloat()))
//        Matrix4(rawMatrix).rotate(0f, 0f, 1f, angle.degrees.toFloat()))

//    fun get(): Matrix4 = Matrix4(rawMatrix)

    fun transform(p: Point2): Point2 {
        val v = Vector3(p.x.toFloat(), p.y.toFloat(), 0f)
        val vt = v.mul(rawMatrix)
        return Point2(vt.x.toDouble(), vt.y.toDouble())
    }

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

//    fun transform(rect: Rect2): Polygon2_4 =
//            Polygon2._4(transform(rect.asPoylgon.p1),
//                    transform(rect.asPoylgon.p2), transform(rect.asPoylgon.p3),
//                    transform(rect.asPoylgon.p4))
//
//    fun transform(it: Polygon2_4) =
//            Polygon2._4(transform(it.p1), transform(it.p2), transform(it.p3),
//                    transform(it.p4))

    fun setTransform(batch: Batch) {
        batch.transformMatrix = matrix() //Matrix4(rawMatrix)
    }

    fun toAngle(): Float {
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

    fun toTranslate(): Vec2 {
        val x = rawMatrix.values[M03]
        val y = rawMatrix.values[M13]
        val z = rawMatrix.values[M23]

        assert(z == 0f)

        return Vec2(x.toDouble(), y.toDouble())
    }

    fun toScale(): Vec2 {
        return Vec2(rawMatrix.scaleX.toDouble(), rawMatrix.scaleY.toDouble())
    }

    override fun toString(): String {
        return rawMatrix.toString()
    }

    fun absTranslate(vector: Vec2): WrappedMatrix {
        val v = Vector3()
        rawMatrix.getTranslation(v)
        v.add(vector.xF, vector.yF, 0f)
        return WrappedMatrix(matrix().setTranslation(v))
    }

}
