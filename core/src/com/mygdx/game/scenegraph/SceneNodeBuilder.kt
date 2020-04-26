package com.mygdx.game.scenegraph

import com.mygdx.game.draw.DrawableV2
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Vec2
import java.util.*

class SceneNodeBuilder {
    private val root: Translate =
            Translate(
                    Vec2(0.0,
                            0.0))
    private var stack: Stack<SceneParent> =
            Stack()

    init {
        stack.push(root)
    }

    fun translate(x: Double, y: Double): SceneNodeBuilder {
        val t = Translate(
                Vec2(x, y))
        stack.peek().add(t)
        stack.push(t)
        return this
    }

    fun rotate(degrees: Int): SceneNodeBuilder {
        val t = Rotate(
                Angle.create(
                        degrees))
        stack.peek().add(t)
        stack.push(t)
        return this
    }

    fun pop(nr: Int = 1): SceneNodeBuilder {
        if (stack.size <= nr) {
            throw RuntimeException("Cannot pop root")
        }
        (0 until nr).forEach { stack.pop() }
        return this
    }

    //    fun build(): SceneNode {
    fun build(): SceneParent {
        val hp = root
        stack.clear()
        stack.push(Translate(
                Vec2(0.0, 0.0)))
        return hp
    }

    fun leaf(drawable: DrawableV2.Drawable, size: Dims2): SceneNodeBuilder {
//        stack.peek().add(Leaf(drawable, size))
        stack.peek().add(Leaf(
                DrawableV2.SizedDrawable(
                        drawable, size)))
        return this
    }
}