package com.mygdx.game.scenegraph

import com.mygdx.game.util.geometry.*
import com.mygdx.game.util.linear.WrappedMatrix
import java.lang.Double.min
import java.lang.Double.max

/*
// TODO(wittie): Any reason to ever postMult = false?
fun <S : MaybeHasDims2>calcBoundingBox(d: Node<S>, postMult: Boolean = true, m: WrappedMatrix = WrappedMatrix()):
        Node<Rect2?> {
    return when (d) {
        is Rotation -> {
            if (d.children.isEmpty())
                return Rotation(d.degrees, mutableListOf(), null)
            val rm = if (postMult) m.postRotate(d.degrees) else m.preRotate(d.degrees)
            val mappedChildren: MutableList<Node<Rect2?>> = d.children.map { calcBoundingBox(it, postMult, m = rm) }.toMutableList()
            val pr: Rect2? = mappedChildren.fold(mappedChildren.first().v, { acc: Rect2?, t: Node<Rect2?> ->
                if (acc == null) {
                    t.v
                } else if (t.v != null){
                    acc.expand(t.v!!)
                } else {
                    null
                }
            })
            Rotation(d.degrees, mappedChildren, pr)
        }

        is Translation -> {
            if (d.children.isEmpty())
                return d.pure(d.vector, mutableListOf(), null)
//            val asT: Translation<OriginShapeDrawerRect> = d
            val rm =
//                    when (asT) {
                    when (d) {
                        is RelativeTranslation -> {
                            if (postMult) m.postTranslate(d.vector) else m.preTranslate(d.vector)
                        }

                        is AbsoluteTranslation -> {
                            m.absTranslate(d.vector)
                        }
                    }


            val mappedChildren: MutableList<Node<Rect2?>> =
                    d.children.map { calcBoundingBox(it, postMult, m = rm) }.toMutableList()

            val pr: Rect2? = mappedChildren.fold(mappedChildren.first().v, { acc: Rect2?, t: Node<Rect2?> ->
                if (acc == null) {
                    t.v
                } else if (t.v != null){
                    acc.expand(t.v!!)
                } else {
                    null
                }
            })

//            val pr = mappedChildren.fold(mappedChildren.first().v, { acc: Rect2, t: Node<Rect2> ->
//                acc.expand(t.v)
//            })
//            asT.pure(d.vector, mappedChildren, pr)
            d.pure(d.vector, mappedChildren, pr)
        }

        is Leaf -> {
            if (d.v.dims() != null) {
                // TODO(wittie): Why must this call be null-asserted?
                val rect: Dims2 = d.v.dims()!!
                val ll = m.transform(Point2(-rect.width / 2.0, -rect.height / 2.0))
                val ul = m.transform(Point2(-rect.width / 2.0, rect.height / 2.0))
                val ur = m.transform(Point2(rect.width / 2.0, rect.height / 2.0))
                val lr = m.transform(Point2(rect.width / 2.0, -rect.height / 2.0))

                val lx = min(ll.x, min(ul.x, min(ur.x, lr.x)))
                val ly = min(ll.y, min(ul.y, min(ur.y, lr.y)))
                val ux = max(ll.x, max(ul.x, max(ur.x, lr.x)))
                val uy = max(ll.y, max(ul.y, max(ur.y, lr.y)))

                Leaf<Rect2?>(Rect2.fromLowerUpper(lx, ly, ux, uy)!!)
            } else {
                Leaf<Rect2?>(null)
            }
        }
    }
}
*/
//fun aabb(node: GameNode): Rect2 {
//
//}
