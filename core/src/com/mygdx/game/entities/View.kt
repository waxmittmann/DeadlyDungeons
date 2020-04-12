package com.mygdx.game.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2


class View(var viewAt: Point2, var viewCenter: Point2, private var viewDims: Dims2) {
    private var camera: OrthographicCamera = OrthographicCamera(viewDims.width, viewDims.height)
    private var viewport: Viewport = FitViewport(viewDims.width, viewDims.height, camera)

    init {
        camera.position.set(viewCenter.x.toFloat(), viewCenter.y.toFloat(), 0f)
        camera.update()
    }

    fun plus(moveBy: Vec2) {
        viewAt = viewAt.plus(moveBy)
    }

//    private fun setCamera() {
//        camera.position.set(viewCenter.x.toFloat(), viewCenter.y.toFloat(), 0f)
//        camera.update()
//    }

    fun setProjectionMatrix(batch: Batch) {
        batch.projectionMatrix = camera.combined
    }

    fun getViewRect(): Rect2 {
        val ll = Point2(viewAt.x - viewDims.width / 2, viewAt.y - viewDims.height / 2)
        return Rect2.create(ll, viewDims)
    }

    override fun toString(): String {
        return "Pos: $viewAt, Dims: $viewDims"
    }

    fun updateCamera(x: Float, y: Float, width: Float, height: Float) {
        println("Updating to $x $y $width $height")
        viewCenter = Point2(width / 2.0, height / 2.0)
//        viewport.setScreenBounds(x.toInt(), y.toInt(), width.toInt(), height.toInt())

        viewport.setScreenBounds(0, 100, width.toInt(), height.toInt() - 100)
        viewport.update(width.toInt(), height.toInt() - 100)
//        camera = OrthographicCamera(width, height)
//        viewport = FitViewport(800, 480, camera)
//        camera = OrthographicCamera(250f, 250f)
//        camera.position.set(viewCenter.x.toFloat(), viewCenter.y.toFloat(), 0f)
        camera.update()
    }
}