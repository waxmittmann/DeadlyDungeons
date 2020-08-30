package com.mygdx.game.newworld

import arrow.core.*
import com.mygdx.game.drawing.DebugDrawable
import com.mygdx.game.drawing.Drawable
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.entities.MobAttributes
import com.mygdx.game.scenegraph.*
//import com.mygdx.game.scenegraph.calcBoundingBox
import com.mygdx.game.util.geometry.*
import com.mygdx.game.util.linear.WrappedMatrix
import java.lang.Double

sealed class TerrainAttributes
class TestTerrain(val name: String) : TerrainAttributes()  // Use objects that inherit, for easy equals sigh
{
    override fun toString(): String {
        return "TestTerrain(name='$name')"
    }
}

class MobAttributes()
class PlayerAttributes()

//class Data<S>(val attributes: S, val drawable: Drawable)

//class HasDims2(val dims: Dims2): MaybeHasDims2 {
//    companion object Builder {
//        fun build(n: Node<Dims2>) = HasDims2(n.v)
//    }
//
//    override fun dims(): Dims2? = dims
//}

class PositionedDrawable(val drawable: Drawable, val rect: Rect2) : HasDims2 {
    override fun dims(): Dims2 = rect.asDims()
}

typealias RawNode = Node<SizedDrawable, Unit>
//typealias BBNode = SameNode<Rect2>
typealias BBNode = Node<PositionedDrawable, Rect2>
typealias BBLeaf = Leaf<PositionedDrawable, Rect2>

open class Entity<S>(open var center: Point2, open val shape: BBNode, open val data: S)

class PlayerData

//typealias Terrain = Entity<TerrainAttributes>
class Terrain(override var center: Point2, override val shape: BBNode,
              override val data: TerrainAttributes) : Entity<TerrainAttributes>(center, shape, data) {
    override fun toString(): String {
        return "Terrain($center, $data)"
    }
}


//class PrintableTerrain : Entity<TerrainAttributes>()

val emptyLeaf: BBLeaf = BBLeaf(PositionedDrawable(DebugDrawable("empty"), Rect2(0.0, 0.0, 1.0, 1.0)))

fun makeEmptyTerrain(ta: TerrainAttributes): Terrain {
    return Terrain(Point2(0.0, 0.0), emptyLeaf, ta)
}

typealias Mob = Entity<MobAttributes>
typealias Player = Entity<PlayerAttributes>

class World(val tileSize: Int, val terrain: List<List<Terrain>>, val player: Player, val mobs: List<Mob>)

fun rect2(node: Node<PositionedDrawable, Rect2>): Rect2 =
        when (node) {
            is ParentNode -> node.parentVal
            is Leaf -> node.leafVal.rect
        }

//class Moved(newMid: Point2)
/*
fun <S> move(toMove: GameEntity<S>, moveBy: Vec2, terrain: List<List<Terrain>>) {



    var moveAt = {
        val moveAtX =
            if (moveBy.x < 0) {
                 toMove.center.x - rect2(toMove.shape).lx
            } else {
                toMove.center.x + rect2(toMove.shape).lx
            }
        val moveAtY =
                if (moveBy.y < 0) {
                    toMove.center.y - rect2(toMove.shape).ly
                } else {
                    toMove.center.y + rect2(toMove.shape).ly
                }
       Point2(moveAtX, moveAtY)
    }

        while (moveAt..x != moveBy.x && moveAt.y != moveBy.y) {

            if (toMove.center.x < moveBy.x && toMove.center.y < moveBy.y) {

            }

        }
    }

}
*/

fun getValue(n: Node<PositionedDrawable, Rect2>): Rect2 = when (n) {
    is Rotation -> n.parentVal
    is RelativeTranslation -> n.parentVal
    is AbsoluteTranslation -> n.parentVal
    is Leaf -> n.leafVal.rect
}

/*
fun calcBoundingBox(d: RawNode, m: WrappedMatrix = WrappedMatrix()):
        Option<BBNode> {
    return when (d) {
        is Rotation -> {
            val rm = m.postRotate(d.degrees)
            val mappedChildren: MutableList<BBNode> = d.children.k().flatMap { toList(calcBoundingBox(it, m = rm)) }.toMutableList()
            if (mappedChildren.isEmpty()) return None

            val pr: Rect2 = mappedChildren.fold(getValue(mappedChildren.first()), { acc: Rect2, t: BBNode ->
                acc.expand(getValue(t))
            })
            Some(Rotation(d.degrees, mappedChildren, pr))
        }

        is Translation -> {
            val rm = when (d) {
                is RelativeTranslation -> m.postTranslate(d.vector)
                is AbsoluteTranslation -> m.absTranslate(d.vector)
            }

            val mappedChildren: MutableList<BBNode> =
                    d.children.k().flatMap { toList(calcBoundingBox(it, m = rm)) }.toMutableList()

            if (mappedChildren.isEmpty())
                return None

            val pr: Rect2 = mappedChildren.fold(getValue(mappedChildren.first()), { acc: Rect2, t: BBNode ->
                acc.expand(getValue(t))
            })

            when (d) {
                is RelativeTranslation -> Some(RelativeTranslation(d.vector, mappedChildren, pr))
                is AbsoluteTranslation -> Some(AbsoluteTranslation(d.vector, mappedChildren, pr))
            }
        }

        is Leaf -> {
            val rect: Dims2 = d.leafVal.size
            val ll = m.transform(Point2(-rect.width / 2.0, -rect.height / 2.0))
            val ul = m.transform(Point2(-rect.width / 2.0, rect.height / 2.0))
            val ur = m.transform(Point2(rect.width / 2.0, rect.height / 2.0))
            val lr = m.transform(Point2(rect.width / 2.0, -rect.height / 2.0))

            val lx = Double.min(ll.x, Double.min(ul.x, Double.min(ur.x, lr.x)))
            val ly = Double.min(ll.y, Double.min(ul.y, Double.min(ur.y, lr.y)))
            val ux = Double.max(ll.x, Double.max(ul.x, Double.max(ur.x, lr.x)))
            val uy = Double.max(ll.y, Double.max(ul.y, Double.max(ur.y, lr.y)))

            Some(BBLeaf(PositionedDrawable(d.leafVal.drawable, Rect2.fromLowerUpper(lx, ly, ux, uy)!!)))
        }
    }
}
 */

fun main(args: Array<String>) {


}


