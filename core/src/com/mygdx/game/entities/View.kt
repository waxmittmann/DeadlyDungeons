package com.mygdx.game.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Quaternion
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.collision.WorldObject
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2

//class View(var viewAt: Point2, private var windowDims: Dims2) {
class View(val player: WorldObject<PlayerAttributes>, private var windowDims: Dims2) {
    private var camera: OrthographicCamera
    private var viewport: Viewport

    init {
        val r: Pair<OrthographicCamera, Viewport> = updateCamera()
        camera = r.first
        viewport = r.second
    }

    fun updateCamera(): Pair<OrthographicCamera, Viewport> {
        val viewCenter = player.position
        camera = OrthographicCamera(windowDims.width, windowDims.height)
        camera.position.set(viewCenter.x.toFloat(), viewCenter.y.toFloat(), 0f)
//        camera.position.set(0f, 0f, 0f)
//        camera.position.set(500f, 500f, 0f)
//        camera.zoom = 2.0f
        viewport = FitViewport(windowDims.width, windowDims.height, camera)
        camera.update()
        return Pair(camera, viewport) // Just for constructor.
    }

    fun setWindowDims(newWindowDims: Dims2) {
        windowDims = newWindowDims
        updateCamera()
    }

    fun setProjectionMatrix(batch: Batch) {
        batch.projectionMatrix = camera.combined
    }

    fun getViewRect(): Rect2 {
        val viewAt = player.position
        val ll = Point2(viewAt.x - windowDims.width / 2,
                viewAt.y - windowDims.height / 2)
        return Rect2.create(ll, windowDims)
    }

    override fun toString(): String {
        return "Pos: ${player.position}, Dims: $windowDims\n${extractProjectionTransform()}"
    }

    fun extractProjectionTransform(): String {
        val trans: Vector3 = Vector3()
        camera.combined.getTranslation(trans)
        val quaternion = Quaternion()
        camera.combined.getRotation(quaternion)
        val scale = Vector3()
        camera.combined.getScale(scale)

        val t = Vector3(windowDims.width, windowDims.height, 0f).mul(camera.combined)

        return "Trans: $trans\nRot: $quaternion\nScale: $scale\nUR: $t"
    }

    fun getWindowDims(): Dims2 = windowDims

    fun translate(screenPos: Point2): Point2 {
        val v = Vector3(screenPos.x.toFloat(), screenPos.y.toFloat(), 0f).mul(camera.combined)
//                screenPos.y.toFloat(), 0f))
        return Point2(v.x.toDouble(), v.y.toDouble())
    }

    fun unproject(worldPoint: Point2): Point2 {
        val r = camera.unproject(worldPoint.asGdxVector())
        if (r.z != 0f)
            throw RuntimeException("Should be 0.")
        return Point2(r.x.toDouble(), r.y.toDouble())
    }
}