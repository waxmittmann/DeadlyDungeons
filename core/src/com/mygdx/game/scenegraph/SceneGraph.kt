package com.mygdx.game.scenegraph

import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2
import java.util.*

fun unitSceneGraphBuilder(): SceneGraphBuilder<Unit, Unit> = SceneGraphBuilder(Unit, { Unit }, { Unit })

typealias SameNode<S> = Node<S, S>
//class SameNode<S> : Node<S, S>()

fun <S>sameTypeSceneGraphBuilder(s: S, factory: () -> S, factory2: () -> S = factory) = SceneGraphBuilder(s, factory, factory2)

sealed class Node<S, T> {
//    abstract val v: S

    abstract fun <U, V> map(fn: (S) -> U, fn2: (T) -> V): Node<U, V>
}

sealed class ParentNode<S, T> : Node<S, T>() {
    abstract val children: MutableList<Node<S, T>>

    abstract val parentVal: T
}

class Rotation<S, T>(val degrees: Float, override val children: MutableList<Node<S, T>>,
                  override val parentVal: T) : ParentNode<S, T>() {
    override fun <U, V> map(fn: (S) -> U, fn2: (T) -> V): Node<U, V> = Rotation(degrees, children.map { it.map(fn, fn2) }.toMutableList(), fn2(parentVal))
}

sealed class Translation<S, T> : ParentNode<S, T>() {
    abstract val vector: Vec2
//    abstract fun<S, T> pure(vector: Vec2, children: MutableList<Node<S, T>>,
//                          v: T): Translation<S, T>
}

class RelativeTranslation<S, T>(override val vector: Vec2, override val children: MutableList<Node<S, T>>,
                             override val parentVal: T) : Translation<S, T>() {
    override fun <U, V> map(fn: (S) -> U, fn2: (T) -> V): Node<U, V> = RelativeTranslation(vector, children.map { it.map(fn, fn2) }.toMutableList(), fn2(parentVal))
//    override fun pure(vector: Vec2, children: MutableList<Node<S, T>>, v: T): Translation<S, T>  = RelativeTranslation(vector, children, v)
}

class AbsoluteTranslation<S, T>(override val vector: Vec2, override val children: MutableList<Node<S, T>>,
                             override val parentVal: T) : Translation<S, T>() {
    override fun <U, V> map(fn: (S) -> U, fn2: (T) -> V): Node<U, V> = AbsoluteTranslation(vector, children.map { it.map(fn, fn2) }.toMutableList(), fn2(parentVal))
//    override fun <T> map(fn: (S) -> T): Node<T> = AbsoluteTranslation(vector,  children.map { it.map(fn) }.toMutableList(), fn(v))
//    override fun pure(vector: Vec2, children: MutableList<Node<T>>, v: T): Translation<T>  = AbsoluteTranslation(vector, children, v)
}

class Leaf<S, T>(val leafVal: S) : Node<S, T>() {
    override fun <U, V> map(fn: (S) -> U, fn2: (T) -> V): Node<U, V> = Leaf(fn(leafVal))
}

class SceneGraphBuilder<S, T>(v: T, val defaultLeafFactory: () -> S, val defaultParentFactory: () -> T) {
    var root: ParentNode<S, T> =
            RelativeTranslation(Vec2(0.0, 0.0), mutableListOf(), v)
    val stack: Stack<ParentNode<S, T>> = Stack()

    companion object Factory {
        fun <S, T> create(v: T, defaultLeafFactory: () -> S, defaultParentFactory: () -> T, fn: SceneGraphBuilder<S, T>.() -> Unit): ParentNode<S, T> {
            val sb = SceneGraphBuilder(v, defaultLeafFactory, defaultParentFactory)
            fn(sb)
            return sb.build()
        }
    }

    init {
        stack.push(root)
    }

    private fun performTransform(transform: ParentNode<S, T>, fn: (SceneGraphBuilder<S, T>) -> Unit): SceneGraphBuilder<S, T> {
        stack.peek().children += transform
        stack.push(transform)
        fn(this)
        stack.pop()
        return this
    }

    fun translate(x: Double, y: Double, v: T = defaultParentFactory(), fn: SceneGraphBuilder<S, T>.() -> Unit): SceneGraphBuilder<S, T> =
            translate(Vec2(x, y), v, fn)

    fun translate(vector: Vec2, v: T = defaultParentFactory(), fn: SceneGraphBuilder<S, T>.() -> Unit): SceneGraphBuilder<S, T> =
            performTransform(RelativeTranslation(vector, mutableListOf(), v), fn)

    fun absTranslate(vector: Vec2, v: T = defaultParentFactory(), fn: SceneGraphBuilder<S, T>.() -> Unit):
            SceneGraphBuilder<S, T> =
            performTransform(AbsoluteTranslation(vector, mutableListOf(), v), fn)

    fun rotate(degrees: Float, v: T = defaultParentFactory(), fn: SceneGraphBuilder<S, T>.() -> Unit):
            SceneGraphBuilder<S, T> =
            performTransform(Rotation(degrees, mutableListOf(), v), fn)

    fun jointRotate(degrees: Float, jointPos: Vec2, v: T = defaultParentFactory(), fn: SceneGraphBuilder<S, T>.() -> Unit):
            SceneGraphBuilder<S, T> =
            translate(jointPos, v) {
                rotate(degrees, v) {
                    translate(jointPos.invert(), v, fn)
                }
            }

    fun leaf(s: S = defaultLeafFactory()):
            SceneGraphBuilder<S, T> {
        stack.peek().children += Leaf(s)
        return this
    }

    fun build(): ParentNode<S, T> {
        return root
    }
}
