package com.mygdx.game.newworld

import arrow.core.*
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.scenegraph.*
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.linear.WrappedMatrix
import java.lang.Double

fun <S> toList(opt: Option<S>): ListKOf<S> = opt.fold({ listOf<S>() }, { listOf(it) }).k()

class BBFunctions<S, T, U, V>(
        val getValue: (Node<U, V>) -> Rect2,
        val getDims: (Leaf<S, T>) -> Dims2,
        val createRot: (Rotation<S, T>, MutableList<Node<U, V>>, Rect2) -> Rotation<U, V>,
        val createAbsTrans: (AbsoluteTranslation<S, T>, MutableList<Node<U, V>>, Rect2) -> AbsoluteTranslation<U, V>,
        val createRelTrans: (RelativeTranslation<S, T>, MutableList<Node<U, V>>, Rect2) -> RelativeTranslation<U, V>,
        val createLeaf: (Leaf<S, T>, Rect2) -> Node<U, V>
)

val DefaultBBFunctions = BBFunctions<SizedDrawable, Unit, PositionedDrawable, Rect2>(
        getValue = { n ->
            when (n) {
                is Rotation -> n.parentVal
                is RelativeTranslation -> n.parentVal
                is AbsoluteTranslation -> n.parentVal
                is Leaf -> n.leafVal.rect
            }
        },
        getDims = { it.leafVal.size },
        createRot = { r, children, rect -> Rotation(r.degrees, children, rect) },
        createAbsTrans = { r, children, rect -> AbsoluteTranslation(r.vector, children, rect) },
        createRelTrans = { r, children, rect -> RelativeTranslation(r.vector, children, rect) },
        createLeaf = { l, r -> Leaf(PositionedDrawable(l.leafVal.drawable, r)) }
)

fun <S, T, U, V> calcBoundingBox(d: Node<S, T>,
                                 fns: BBFunctions<S, T, U, V>,
                                 m: WrappedMatrix = WrappedMatrix()):
        Option<Node<U, V>> {
    return when (d) {
        is Rotation -> {
            val rm = m.postRotate(d.degrees)
            val mappedChildren: MutableList<Node<U, V>> = d.children.k().flatMap { toList(calcBoundingBox(it, fns, m = rm)) }.toMutableList()
            if (mappedChildren.isEmpty()) return None

            val pr: Rect2 = mappedChildren.fold(fns.getValue(mappedChildren.first()), { acc: Rect2, t: Node<U, V> ->
                acc.expand(fns.getValue(t))
            })
            Some(fns.createRot(d, mappedChildren, pr))
        }

        is Translation -> {
            val rm = when (d) {
                is RelativeTranslation -> m.postTranslate(d.vector)
                is AbsoluteTranslation -> m.absTranslate(d.vector)
            }

            val mappedChildren: MutableList<Node<U, V>> =
                    d.children.k().flatMap { toList(calcBoundingBox(it, fns, m = rm)) }.toMutableList()

            if (mappedChildren.isEmpty())
                return None

            val pr: Rect2 = mappedChildren.fold(fns.getValue(mappedChildren.first()), { acc: Rect2, t: Node<U, V> ->
                acc.expand(fns.getValue(t))
            })

            when (d) {
                is RelativeTranslation -> Some(fns.createRelTrans(d, mappedChildren, pr))
                is AbsoluteTranslation -> Some(fns.createAbsTrans(d, mappedChildren, pr))
            }
        }

        is Leaf -> {
            val rect: Dims2 = fns.getDims(d)
//            val rect: Rect2 = fns.getValue(d)
            val ll = m.transform(Point2(-rect.width / 2.0, -rect.height / 2.0))
            val ul = m.transform(Point2(-rect.width / 2.0, rect.height / 2.0))
            val ur = m.transform(Point2(rect.width / 2.0, rect.height / 2.0))
            val lr = m.transform(Point2(rect.width / 2.0, -rect.height / 2.0))

            val lx = Double.min(ll.x, Double.min(ul.x, Double.min(ur.x, lr.x)))
            val ly = Double.min(ll.y, Double.min(ul.y, Double.min(ur.y, lr.y)))
            val ux = Double.max(ll.x, Double.max(ul.x, Double.max(ur.x, lr.x)))
            val uy = Double.max(ll.y, Double.max(ul.y, Double.max(ur.y, lr.y)))

//            Some(BBLeaf(PositionedDrawable(d.leafVal.drawable, Rect2.fromLowerUpper(lx, ly, ux, uy)!!)))
            Some(fns.createLeaf(d, Rect2.fromLowerUpper(lx, ly, ux, uy)!!))
        }
    }
}
