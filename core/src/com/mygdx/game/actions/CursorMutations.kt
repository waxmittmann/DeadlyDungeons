package com.mygdx.game.actions

import com.mygdx.game.util.geometry.Point2

fun cursorMutation(screenPos: Point2): Mutation = {
    val convertedScreenPos = Point2(screenPos.x, it.ui.windowDims.height -
            screenPos.y)

    it.ui.cursorScreenCoord = convertedScreenPos
    it.ui.cursorGameCoord = it.world.view.translate(convertedScreenPos)
    it.ui.cursorUnprojected = it.world.view.unproject(screenPos)
}