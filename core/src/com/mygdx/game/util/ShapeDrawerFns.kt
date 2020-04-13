package com.mygdx.game.util

import com.badlogic.gdx.graphics.Color
import com.mygdx.game.util.geometry.Point2
import space.earlygrey.shapedrawer.ShapeDrawer

fun borderCircle(shapeDrawer: ShapeDrawer, fillColor: Color, borderColor: Color,
                 center: Point2, radius: Float) {
    shapeDrawer.setColor(fillColor)
    shapeDrawer.filledCircle(center.x.toFloat(), center.y.toFloat(), radius)
    shapeDrawer.setColor(borderColor)
    shapeDrawer.circle(center.x.toFloat(), center.y.toFloat(), radius)
}
