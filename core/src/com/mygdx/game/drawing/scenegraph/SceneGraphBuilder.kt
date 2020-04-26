package com.mygdx.game.drawing.scenegraph

import com.mygdx.game.drawing.Drawable
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Vec2
import java.util.*

class SceneGraphBuilder {
    private val root: Translate =
            Translate(
                    Vec2(0.0,
                            0.0))
    private var stack: Stack<SceneParent> =
            Stack()

    init {
        stack.push(root)
    }

    fun translate(x: Double, y: Double): SceneGraphBuilder {
        val t = Translate(
                Vec2(x, y))
        stack.peek().add(t)
        stack.push(t)
        return this
    }

    fun rotate(degrees: Int): SceneGraphBuilder {
        val t = Rotate(
                Angle.create(
                        degrees))
        stack.peek().add(t)
        stack.push(t)
        return this
    }

    fun pop(nr: Int = 1): SceneGraphBuilder {
        if (stack.size <= nr) {
            throw RuntimeException("Cannot pop root")
        }
        (0 until nr).forEach { stack.pop() }
        return this
    }

    fun build(): SceneParent {
        val hp = root
        stack.clear()
        stack.push(Translate(
                Vec2(0.0, 0.0)))
        return hp
    }

    fun leaf(drawable: Drawable, size: Dims2): SceneGraphBuilder {
        stack.peek().add(Leaf(
                SizedDrawable(
                        drawable, size)))
        return this
    }
}