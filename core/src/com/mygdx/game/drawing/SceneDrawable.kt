package com.mygdx.game.drawing

import com.badlogic.gdx.graphics.g2d.Batch
import com.mygdx.game.drawing.scenegraph.*
import com.mygdx.game.drawing.DrawableFns.drawCentered
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Vec2
import com.mygdx.game.util.linear.ProjectionSaver
import com.mygdx.game.util.linear.WrappedMatrix

abstract class WorldDrawable {
    abstract fun draw(batch: Batch, delta: Float)
}

class SimpleDrawable(val drawable: SizedDrawable, val center: Point2)
    : WorldDrawable() {
    override fun draw(batch: Batch, delta: Float) {
        (drawCentered(drawable, center))(batch, delta)
    }
}

sealed class SceneNodeDrawable : WorldDrawable() {
    abstract fun toHierarchyString(indent: String = ""): String
}

class LeafDrawable(
        val drawable: SizedDrawable, val id: String) : SceneNodeDrawable() {

    // Assumes transforms have occurred to position object correctly.
    // TODO: This should be protected. LeafDrawable should probably not count
    // as a WorldDrawable.
    override fun draw(batch: Batch, delta: Float) {
        drawCentered(drawable, batch, delta)
    }

    override fun toHierarchyString(indent: String): String {
        return indent + "Leaf id: $id"
    }
}
