package com.mygdx.game.scenegraph

import com.mygdx.game.util.geometry.Vec2
import java.util.*

fun unitSceneGraphBuilder(): SceneGraphBuilder<Unit> = SceneGraphBuilder(Unit, { Unit })

sealed class Node<S> {
    abstract val v: S

    abstract fun <T> map(fn: (S) -> T): Node<T>
}

sealed class ParentNode<S> : Node<S>() {
    abstract val children: MutableList<Node<S>>
}

class Rotation<S>(val degrees: Float, override val children: MutableList<Node<S>>,
                  override val v: S) : ParentNode<S>() {
    override fun <T> map(fn: (S) -> T): Node<T> = Rotation(degrees, children.map { it.map(fn) }.toMutableList(), fn(v))
}

sealed class Translation<S> : ParentNode<S>() {
    abstract val vector: Vec2
    abstract fun <T> pure(vector: Vec2, children: MutableList<Node<T>>,
                          v: T): Translation<T>
}

class RelativeTranslation<S>(override val vector: Vec2, override val children: MutableList<Node<S>>,
                             override val v: S) : Translation<S>() {
    override fun <T> map(fn: (S) -> T): Node<T> = RelativeTranslation(vector,  children.map { it.map(fn) }.toMutableList(), fn(v))
    override fun <T> pure(vector: Vec2, children: MutableList<Node<T>>, v: T): Translation<T>  = RelativeTranslation(vector, children, v)
}

class AbsoluteTranslation<S>(override val vector: Vec2, override val children: MutableList<Node<S>>,
                             override val v: S) : Translation<S>() {
    override fun <T> map(fn: (S) -> T): Node<T> = AbsoluteTranslation(vector,  children.map { it.map(fn) }.toMutableList(), fn(v))
    override fun <T> pure(vector: Vec2, children: MutableList<Node<T>>, v: T): Translation<T>  = AbsoluteTranslation(vector, children, v)
}

class Leaf<S>(
        override val v: S) : Node<S>() {
    override fun <T> map(fn: (S) -> T): Node<T> = Leaf(fn(v))
}

class SceneGraphBuilder<S>(v: S, val defaultFactory: () -> S) {
    var root: ParentNode<S> =
            RelativeTranslation(Vec2(0.0, 0.0), mutableListOf(), v)
    val stack: Stack<ParentNode<S>> = Stack<ParentNode<S>>()

    companion object Factory {
        fun <S> create(v: S, defaultFactory: () -> S, fn: SceneGraphBuilder<S>.() -> Unit): ParentNode<S> {
            val sb = SceneGraphBuilder<S>(v, defaultFactory)
            fn(sb)
            return sb.build()
        }
    }

    init {
        stack.push(root)
    }

    private fun performTransform(transform: ParentNode<S>, v: S, fn: (SceneGraphBuilder<S>) -> Unit): SceneGraphBuilder<S> {
        stack.peek().children += transform
        stack.push(transform)
        fn(this)
        stack.pop()
        return this
    }

    fun translate(x: Double, y: Double, v: S = defaultFactory(), fn: SceneGraphBuilder<S>.() -> Unit): SceneGraphBuilder<S> =
            translate(Vec2(x, y), v, fn)

    fun translate(vector: Vec2, v: S = defaultFactory(), fn: SceneGraphBuilder<S>.() -> Unit): SceneGraphBuilder<S> =
            performTransform(RelativeTranslation(vector, mutableListOf(), v), v, fn)

    fun absTranslate(vector: Vec2, v: S = defaultFactory(), fn: SceneGraphBuilder<S>.() -> Unit):
            SceneGraphBuilder<S> =
            performTransform(AbsoluteTranslation(vector, mutableListOf(), v), v, fn)

    fun rotate(degrees: Float, v: S = defaultFactory(), fn: SceneGraphBuilder<S>.() -> Unit):
            SceneGraphBuilder<S> =
            performTransform(Rotation(degrees, mutableListOf(), v), v, fn)

    fun jointRotate(degrees: Float, jointPos: Vec2, v: S = defaultFactory(), fn: SceneGraphBuilder<S>.() -> Unit):
            SceneGraphBuilder<S> =
            translate(jointPos, v) {
                rotate(degrees, v) {
                    translate(jointPos.invert(), v, fn)
                }
            }

    fun leaf(s: S = defaultFactory()):
            SceneGraphBuilder<S> {
        stack.peek().children += Leaf(s)
        return this
    }

    fun build(): ParentNode<S> {
        return root
    }
}
