package com.mygdx.game.util.linear

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2

class WrappedMatrix(private val rawMatrix: Matrix4 = Matrix4()) {
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

    fun transform(rect: Rect2): Rect2 =
            Rect2.fromPoints(transform(rect.lowerLeft()),
                    transform(rect.upperRight()))

    fun setTransform(batch: Batch) {
        batch.transformMatrix = Matrix4(rawMatrix)
    }

}