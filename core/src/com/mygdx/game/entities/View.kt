package com.mygdx.game.entities

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2

class View(var viewAt: Point2, val viewCenter: Point2, private var viewDims: Dims2) {
    private val camera: OrthographicCamera = OrthographicCamera(viewDims.width, viewDims.height)

//    var viewAt = viewCenter

    init {
        camera.position.set(viewCenter.x.toFloat(), viewCenter.y.toFloat(), 0f)
//        camera.position.set(0f, 0f, 0f)
        camera.update()

//        println(camera.combined)

//        setCamera()
    }

    fun plus(moveBy: Vec2) {
//        viewCenter = viewCenter.plus(moveBy)
        viewAt = viewAt.plus(moveBy)
//        setCamera()
    }

//    private fun setCamera() {
//        camera.position.set(viewCenter.x.toFloat(), viewCenter.y.toFloat(), 0f)
//        camera.update()
//    }

    fun setProjectionMatrix(batch: SpriteBatch) {
        batch.projectionMatrix = camera.combined
    }

    fun getViewRect(): Rect2 {
//        val ll = Point2(viewCenter.x - viewDims.width / 2, viewCenter.y - viewDims.height / 2)
        val ll = Point2(viewAt.x - viewDims.width / 2, viewAt.y - viewDims.height / 2)
        return Rect2.create(ll, viewDims)
    }

    override fun toString(): String {
        return "Pos: $viewAt, Dims: $viewDims"
    }

//    val camera: OrthographicCamera = {
//        val camera = OrthographicCamera(viewDims.width, viewDims.height)
//        camera.position.set(viewPos.x.toFloat(), viewPos.y.toFloat(), 0f)
//        camera.update()
//        camera
//    }()



}