package com.mygdx.game.entities

import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2


class UiState(var showInventory: Boolean,
        var cursorScreenCoord: Point2,
        var cursorGameCoord: Point2,
        var cursorUnprojected: Point2,
        var playerRotation: Angle,
        var windowDims: Dims2 = Dims2(0f, 0f)
        ) {

    companion object {
        fun create(): UiState = UiState(false, Point2(0.0, 0.0),
                Point2(0.0, 0.0), Point2(0.0, 0.0), Angle(0f))
    }
}
