package com.mygdx.game.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2

class View(var viewAt: Point2, private val playerDims: Dims2, private var windowDims: Dims2) {
    private var camera: OrthographicCamera
    private var viewport: Viewport

    init {
        val r: Pair<OrthographicCamera, Viewport> = updateCamera()
        camera = r.first
        viewport = r.second
    }

    private fun updateCamera(): Pair<OrthographicCamera, Viewport> {
        val viewCenter = Point2(windowDims.width / 2.0, windowDims.height / 2.0)
                .plus(Vec2(playerDims.width/2.0, playerDims.height/2.0))

        camera = OrthographicCamera(windowDims.width, windowDims.height)
        viewport = FitViewport(windowDims.width, windowDims.height, camera)

        camera.position.set(viewCenter.x.toFloat(), viewCenter.y.toFloat(), 0f)
        camera.update()
        return Pair(camera, viewport) // Just for constructor.
    }

    fun plus(moveBy: Vec2) {
        viewAt = viewAt.plus(moveBy)
    }

    fun setWindowDims(newWindowDims: Dims2) {
        windowDims = newWindowDims
        updateCamera()
    }

    fun setProjectionMatrix(batch: Batch) {
        batch.projectionMatrix = camera.combined
    }

    fun getViewRect(): Rect2 {
        val ll = Point2(viewAt.x - windowDims.width / 2, viewAt.y - windowDims.height / 2)
        return Rect2.create(ll, windowDims)
    }

    override fun toString(): String {
        return "Pos: $viewAt, Dims: $windowDims"
    }

    fun getWindowDims(): Dims2 = windowDims
}