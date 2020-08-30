package com.mygdx.game.newworld

import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2


//sealed trait Failure(moveRemaining: Vec2)
//class OutOfBounds(oobAt: Point2)
//class Impassable(impassableTerrain: Set<Terrain>)

//fun touchesBorder(obj: Node, curPos: Point2, moveTo: Point2, tileSize: Double): Point2 {
//    val v = Vec2(moveTo.x - curPos.x, moveTo.y - curPos.y).normalize()
//
//    val distanceToX =
//        if (moveTo.x - curPos.x > 0) { tileSize - (obj.pos.x % tileSize) }
//        else { obj.pos.x % tileSize }
//
//    val distanceToY =
//        if (moveTo.y - curPos.y > 0) { tileSize - (obj.pos.y % tileSize) }
//        else { obj.pos.y % tileSize }
//
//    if (moveTo.x - curPos.x < distanceToX && moveTo.y - curPos.y < distanceToY) {
//        moveTo
//    } else {
//        if (v.x != 0 && (v.y == 0 || distanceToX / v.x )) {
//            Point2(curPos.x + distanceToX, curPos.y + v.y * (distanceToX / v.x) )
//        } else {
//            Point2(curPos.x + v.x * (distanceToY / v.y), curPos.y + distanceToY )
//        }
//    }
//}
//
//fun next(bb: Rect2, curPos: Point2, moveTo: Point2, terrain: Terrain[][]): (Node, Vec, Option<Failure>) {
//
//    val touches = touchesBorder(obj, curPos, moveTo, tileSize);
//    if (touches == moveTo) {
//        return Done()
//    } else {
//        if (!CheckTerrain(touches.terrain)) {
//
//        }
//    }
//
//}

class Grid(val terrain: List<List<Terrain>>, val tileSize: Int) {
    fun getTile(x: Int, y: Int): Terrain = terrain[y][x]
}

interface Corner {
    val p: Point2
    val rect: Rect2
    fun adjacentTerrain(terrain: Grid): Set<Terrain>
}

class LowerLeft(override val rect: Rect2) : Corner {
    override val p: Point2 = Point2(rect.lx, rect.ly)

    override fun adjacentTerrain(grid: Grid): Set<Terrain> {
        val adjacent: MutableSet<Terrain> = mutableSetOf()
        // Get terrain adjacent to left side.
        if (p.x > 0 && p.x.toInt() % grid.tileSize == 0) {
            var yAt = (rect.ly / grid.tileSize).toInt()
            // Does 'uy' belong to this one or not?
            while ((yAt * grid.tileSize) < rect.uy()) {
                println("At $yAt, ${yAt * grid.tileSize}, ${rect.uy()}")
                adjacent.add(grid.terrain[yAt][(p.x / grid.tileSize).toInt() - 1])
                yAt++
            }
        }
        // Get terrain adjacent to bottom side.
        if (p.y > 0 && p.y.toInt() % grid.tileSize == 0) {
            var xAt = (rect.lx / grid.tileSize).toInt()
            while ((xAt * grid.tileSize) < rect.ux()) {
                adjacent.add(grid.terrain[(p.y / grid.tileSize).toInt() - 1][xAt])
                xAt++
            }
        }
        // Get terrain that's diagonal from lower-left.
        if (p.x > 0 && p.y > 0 && (p.x.toInt() % grid.tileSize == 0) && (p.y.toInt() % grid.tileSize == 0)) {
            adjacent.add(grid.terrain[(p.y / grid.tileSize).toInt() - 1][(p.y / grid.tileSize).toInt() - 1])
        }

        return adjacent
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LowerLeft

        if (rect != other.rect) return false
        if (p != other.p) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rect.hashCode()
        result = 31 * result + p.hashCode()
        return result
    }
}


//fun move(bb: Rect2, initialPos: Point2, moveBy: Vec2, terrain: List<List<Terrain>>, tileSize: Int): Point2 {
//    var curPos = initialPos
//    var moveTo = initialPos.plus(moveBy)
//    while (curPos != moveTo) {
//
//        curPos =
//            match (next(obj.bb, terrain, curPos, moveTo)) {
//                case f @ Failure(pos) -> f
//                case Success(pos) -> pos
//            }
//    }
//    return Success(moveTo)
//}
//
