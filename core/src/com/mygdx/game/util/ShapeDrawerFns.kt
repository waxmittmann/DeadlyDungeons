package com.mygdx.game.util

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Vec2
import com.mygdx.game.util.linear.WrappedMatrix
import space.earlygrey.shapedrawer.ShapeDrawer

fun borderCircle(shapeDrawer: ShapeDrawer, fillColor: Color, borderColor: Color,
        center: Point2, radius: Float) {
    shapeDrawer.setColor(fillColor)
    shapeDrawer.filledCircle(center.x.toFloat(), center.y.toFloat(), radius)
    shapeDrawer.setColor(borderColor)
    shapeDrawer.circle(center.x.toFloat(), center.y.toFloat(), radius)
}

fun drawCoord(shapeDrawer: ShapeDrawer, t: Point2 = Point2(0.0, 0.0)) {
    shapeDrawer.setColor(1f, 0f, 0f, 1f)
    shapeDrawer.line(t.asGdxVector2(),
            t.plus(Vec2(100.0, 0.0)).asGdxVector2())
    shapeDrawer.setColor(0f, 1f, 0f, 1f)
    shapeDrawer.line(t.asGdxVector2(),
            t.plus(Vec2(0.0, 100.0)).asGdxVector2())
    shapeDrawer.setColor(0f, 0f, 1f, 1f)
    shapeDrawer.line(t.asGdxVector2(),
            t.plus(Vec2(100.0, 100.0)).asGdxVector2())
}

fun drawMatrix(shapeDrawer: ShapeDrawer, m: WrappedMatrix) {
    val t = m.toTranslate()
    // Invert angle because natively rotation is counter-clockwise.
    val a = m.toAngle().invert()

    val t2 = a.transform(t.asPoint, t.plus(Vec2(200.0, 0.0)).asPoint)
    val t3 = a.transform(t.asPoint, t.plus(Vec2(0.0, 200.0)).asPoint)
    val t4 = a.transform(t.asPoint, t.plus(Vec2(200.0, 200.0)).asPoint)

    shapeDrawer.setColor(1f, 0f, 0f, 1f)
    shapeDrawer.filledCircle(t.xF, t.yF, 1.5f)
    shapeDrawer.line(Vector2(t.xF, t.yF),
            Vector2(t2.x.toFloat(), t2.y.toFloat()))
    shapeDrawer.setColor(0f, 1f, 0f, 1f)
    shapeDrawer.line(Vector2(t.xF, t.yF),
            Vector2(t3.x.toFloat(), t3.y.toFloat()))
    shapeDrawer.setColor(0f, 0f, 1f, 1f)
    shapeDrawer.line(Vector2(t.xF, t.yF),
            Vector2(t4.x.toFloat(), t4.y.toFloat()))

}
