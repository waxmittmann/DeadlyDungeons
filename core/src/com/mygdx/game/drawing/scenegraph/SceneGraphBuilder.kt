package com.mygdx.game.drawing.scenegraph

import com.mygdx.game.drawing.Drawable
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Vec2
import java.util.*

class SceneGraphBuilder<S>(val sFactory: () -> S) {
    private var root: Translate<S> = Translate(Vec2(0.0, 0.0), mutableListOf(),
            getNextId(), sFactory())
    private var stack: Stack<SceneParent<S>> = Stack()
    private var nextId = 0

    init {
        stack.push(root)
    }

    private fun getNextId(): String {
        val id = nextId
        nextId += 1
        return id.toString()
    }

    fun translate(x: Double, y: Double, id: String = getNextId()):
            SceneGraphBuilder<S> {
        val t = Translate<S>(Vec2(x, y), mutableListOf(), id, sFactory())
        stack.peek().add(t)
        stack.push(t)
        return this
    }

    fun rotate(degrees: Int, id: String = getNextId()): SceneGraphBuilder<S> {
        val t = Rotate(Angle.create(degrees),  mutableListOf(), id, sFactory())
        stack.peek().add(t)
        stack.push(t)
        return this
    }

    fun pop(nr: Int = 1): SceneGraphBuilder<S> {
        if (stack.size <= nr) {
            throw RuntimeException("Cannot pop root")
        }
        (0 until nr).forEach { stack.pop() }
        return this
    }

    fun build(id: String = getNextId()): SceneParent<S> {
        val hp = root
        stack.clear()
        root = Translate<S>(Vec2(0.0, 0.0), mutableListOf(), id, sFactory())
        stack.push(root)
        return hp
    }

    fun leaf(drawable: Drawable, size: Dims2, id: String = getNextId()):
            SceneGraphBuilder<S> {
        stack.peek().add(Leaf(SizedDrawable(drawable, size), id, sFactory()))
        return this
    }
}