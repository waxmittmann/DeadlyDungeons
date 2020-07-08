package com.mygdx.game.drawing.scenegraph

import arrow.core.k
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.util.geometry.*
import com.mygdx.game.util.linear.WrappedMatrix
import java.lang.System.err

sealed class SceneNode<S> {
//    abstract val boundaryDims: Dims2


    // TRyiNG TO PLUMB THE MATRIX THROUGH/
    // I was multiplying the wrong way around I think, by translating points
    // throuhg chain last item firt.
    // Should mirror the matrrix call chain.

//    val aabb: Rect2 by lazy {

    abstract val points: List<Point2>
    abstract val aabb: Rect2

//    val aabb: Rect2 by lazy {
//        chil
//
//    }

    fun aabb2(m: WrappedMatrix = WrappedMatrix()): Rect2 {
        return Rect2.aabb(polygon2_4(m).flatMap { it.vertices })
    }


    abstract fun aabb3(m: WrappedMatrix): Rect2

    /*
    val aabb: Rect2 by lazy {

        println("Polys:")
        polygon2_4.forEach {
            println("$it\n")
        }

        Rect2.aabb(polygon2_4.flatMap { it.vertices })

//        var lx = 0.0
//        var ly = 0.0
//        var ux = 0.0
//        var uy = 0.0
//
//        for (poly in polygon2_4) {
//            for (point in poly.vertices) {
//                if (point.x < lx)
//                    lx = point.x
//                if (point.x > ux)
//                    ux = point.x
//                if (point.y < ly)
//                    ly = point.y
//                if (point.y > uy)
//                    uy = point.y
//            }
//        }
//
//        Rect2.fromPoints(Point2(lx, ly), Point2(ux, uy))
    }
     */
//    abstract val polygon2_4: List<Polygon2_4>
    abstract fun polygon2_4(m: WrappedMatrix): List<Polygon2_4>

    abstract fun print(wrappedMatrix: WrappedMatrix): Unit

    abstract val id: String
    abstract val attributes: S
}

sealed class SceneParent<S> : SceneNode<S>() {
    abstract fun add(proto: SceneNode<S>): SceneParent<S>

    abstract val children: List<SceneNode<S>>

}

class Leaf<S>(val drawable: SizedDrawable, override val id: String = "*noid*",
        override val attributes: S) :
        SceneNode<S>() {
//    override val boundaryDims: Dims2 = drawable.size
//    override val polygon2_4: List<Polygon2_4> by lazy {
//        listOf(drawable.size.asRect.asPoylgon)
//    }

    override fun polygon2_4(m: WrappedMatrix): List<Polygon2_4> {
       return listOf(m.transform(drawable.size.asRect.asPoylgon))
    }

    override val points: List<Point2> = drawable.size.asRect.asPoints

    override val aabb: Rect2 = drawable.size.asRect

    override fun aabb3(m: WrappedMatrix): Rect2 = m.transform(drawable.size
            .asRect).asAabb

    override fun print(wrappedMatrix: WrappedMatrix): Unit {
        println("$id: ${wrappedMatrix.transform(drawable.size.asRect)}")
    }
}

class Rotate<S>(val rotation: Angle,
             override val children: MutableList<SceneNode<S>> = mutableListOf(),
             override val id: String = "*noid*", override val attributes: S) : SceneParent<S>() {
    override fun add(proto: SceneNode<S>): Rotate<S> {
        children += proto
        return this
    }

    override val points: List<Point2> by lazy {
        val m = WrappedMatrix.from(rotation)
        val x = children.k().flatMap {
            it.points.k().map { it2 ->
            m.transform(it2)
        } }.toList()
        x
    }

    // Backwards????
//    override val polygon2_4: List<Polygon2_4> by lazy {
//        children.k().flatMap<Polygon2_4> { child ->
//            child.polygon2_4.k().map<Polygon2_4> { WrappedMatrix.from(rotation).transform(it) }
//        }
//    }

    override fun polygon2_4(m: WrappedMatrix): List<Polygon2_4> {
        val mh = m.rotate(rotation)
        return children.k().flatMap<Polygon2_4> { child ->
//            child.polygon2_4(mh).k().map<Polygon2_4> { WrappedMatrix.from(rotation).transform(it) }
            child.polygon2_4(mh).k()
        }
    }

    override val aabb: Rect2 by lazy {
        val m = WrappedMatrix.from(rotation)
        val x = Rect2.aabbFromRects(children.k().map<Rect2> { m.transform(it
                .aabb).asAabb }
                .toList())
        x
    }

    override fun aabb3(m: WrappedMatrix): Rect2 {
        val m2 = m.mul(WrappedMatrix.from(rotation))
//        val m2 = WrappedMatrix.from(rotation).mul(m)
        val x = Rect2.aabbFromRects(children.k().map<Rect2> { it.aabb3(m2) }
                .toList())
        return x
    }

    override fun print(wrappedMatrix: WrappedMatrix): Unit {
        val mh = wrappedMatrix.mul(WrappedMatrix().rotate(rotation))
        println("$id: ${mh.transform(Point2(0.0, 0.0))}")
        children.k().map { it.print(mh) }
    }
}

class Translate<S>(val translation: Vec2,
                override val children: MutableList<SceneNode<S>> = mutableListOf
                (), override val id: String = "*noid*", override val attributes: S) :
        SceneParent<S>() {
    override fun add(proto: SceneNode<S>): Translate<S> {
        children += proto
        return this
    }

    override val points: List<Point2> by lazy {
        val m = WrappedMatrix.from(translation)
        val x = children.k().flatMap { it ->
            it.points.k().map { it2 ->
            m.transform(it2)
        } }.toList()
        x
    }

//    override val boundaryDims: Dims2 by lazy {
//        throw java.lang.RuntimeException("Whoops not implemented yet")
//        err.println("NOT IMPLE<EMTED YET!!!!")
//        Dims2(0f, 0f)
//    }

//    override val polgon2_4: List<Polygon2_4> by lazy {
//        children.flatMap {  child ->
//            WrappedMatrix.from(translation).(child.polygon2_4)
//        }
//    }

//    override val polygon2_4: List<Polygon2_4> by lazy {
//        children.k().flatMap<Polygon2_4> { child ->
//            child.polygon2_4.k().map<Polygon2_4> { WrappedMatrix.from(translation)
//                    .transform(it) }
//        }
//    }

    override fun polygon2_4(m: WrappedMatrix): List<Polygon2_4> {
        val mh = m.translate(translation)
//        val mh = translation.translate(translation)
        return children.k().flatMap<Polygon2_4> { child ->
            val x = child.polygon2_4(mh).k()
            x
        }
    }


    override val aabb: Rect2 by lazy {
        val m = WrappedMatrix.from(translation)
        val x = Rect2.aabbFromRects(children.k().map<Rect2> { m.transform(it
                .aabb)
               .asAabb }
                .toList())
        x
    }

    override fun aabb3(m: WrappedMatrix): Rect2 {
        val m2 = m.mul(WrappedMatrix.from(translation))
//        val m2 =WrappedMatrix.from(translation).mul(m)
        val x = Rect2.aabbFromRects(children.k().map<Rect2> { it.aabb3(m2) }
                .toList())
        return x
    }

    override fun print(wrappedMatrix: WrappedMatrix): Unit {
        val mh = wrappedMatrix.mul(WrappedMatrix().translate(translation))
        println("$id: ${mh.transform(Point2(0.0, 0.0))}")
        children.k().map { it.print(mh) }
    }
}

//https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_collision.html
