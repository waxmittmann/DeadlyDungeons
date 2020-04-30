package com.mygdx.game.util.linear

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Matrix4.*
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2


class WrappedMatrix internal constructor(private val rawMatrix: Matrix4 = Matrix4()) {
    companion object Factory {
        fun from(copyFrom: WrappedMatrix): WrappedMatrix =
                WrappedMatrix(copyFrom.get())

        fun from(matrix: Matrix4): WrappedMatrix =
                WrappedMatrix(Matrix4(matrix))

        fun fromTransform(batch: Batch): WrappedMatrix =
                WrappedMatrix(Matrix4(batch.transformMatrix))
    }

    fun mul(other: WrappedMatrix): WrappedMatrix =
            WrappedMatrix(Matrix4(rawMatrix).mul(other.rawMatrix))

    fun translate(x: Float, y: Float): WrappedMatrix =
            WrappedMatrix(Matrix4(rawMatrix).translate(x, y, 0f))

    fun translate(v: Vec2): WrappedMatrix =
            WrappedMatrix(Matrix4(rawMatrix).translate(v.xF, v.yF, 0f))

    fun rotate(degrees: Int): WrappedMatrix = WrappedMatrix(
            Matrix4(rawMatrix).rotate(0f, 0f, 1f, degrees.toFloat()))

    fun rotate(angle: Angle): WrappedMatrix = WrappedMatrix(
        Matrix4(rawMatrix).rotate(0f, 0f, 1f, -angle.degrees.toFloat()))

    fun get(): Matrix4 = Matrix4(rawMatrix)

    fun transform(p: Point2): Point2 {
        val v = Vector3(p.x.toFloat(), p.y.toFloat(), 0f)
        val vt = v.mul(rawMatrix)
        return Point2(vt.x.toDouble(), vt.y.toDouble())
    }

    // Use only if no rotation... make better
    fun transform(rect: Rect2): Rect2 {
        return Rect2.fromPoints(transform(rect.lowerLeft()),
                    transform(rect.upperRight()))
    }

    fun setTransform(batch: Batch) {
        batch.transformMatrix = Matrix4(rawMatrix)
    }

    fun toAngle(): Angle {
        val quaternion: Quaternion = Quaternion()
        rawMatrix.getRotation(quaternion)

        assert(quaternion.x == 0f)
        assert(quaternion.y == 0f)
        assert(quaternion.z == 1f)

        return Angle.create(quaternion.angle.toInt())
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
}