package com.mygdx.game.util

import com.mygdx.game.entities.Terrain
import arrow.core.*
import arrow.core.extensions.*
import arrow.core.extensions.listk.semigroup.*
import arrow.typeclasses.Semigroup

fun main(args: Array<String>) {
    val u = Utils(10)

    println(u.pointToIndex(Point2(0, 0)))
    println(u.pointToIndex(Point2(9, 9)))
    println(u.pointToIndex(Point2(10, 10)))
    println(u.pointToIndex(Point2(-30,
            30)))

    println(u.rectToIndex(Rect2.fromLowerUpper(0, 0, 9, 9)!!))
    println(u.rectToIndex(Rect2.fromLowerUpper(0, 0, 10, 10)!!))
    println(u.rectToIndex(Rect2.fromLowerUpper(-1, -1, 10, 10)!!))
    println(u.rectToIndex(Rect2.fromLowerUpper(0, -1, 10, 10)!!))
    println(u.rectToIndex(Rect2.fromLowerUpper(0, -1, 10, 9)!!))

//    val u2 = Utils(50)
//    u2.rectToIndex()


}

class Indices(val xr: IntRange, val yr: IntRange) {
    override fun toString(): String {
        return "([$xr], [$yr])";
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Indices

        if (xr != other.xr) return false
        if (yr != other.yr) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xr.hashCode()
        result = 31 * result + yr.hashCode()
        return result
    }

//    fun minus(amnt: Int): Indices {
//        return Indices(xr.)
//    }
}

class Utils(private val tileSize: Int) {
    fun pointToIndex(point2: Point2): Pair<Int, Int> {
        val x = if (point2.x >= 0) {
            point2.x / tileSize
        } else {
            (point2.x - tileSize + 1) / tileSize
        }

        val y = if (point2.y >= 0) {
            point2.y / tileSize
        } else {
            (point2.y - tileSize + 1) / tileSize
        }
        return Pair(x, y)
    }

    fun rectToIndex(rect: Rect2): Indices {
        val l = pointToIndex(rect.lowerLeft())
//        val u = pointToIndex(rect.upperRight())
        val u = pointToIndex(rect.upperRight().minus(Vec2(1, 1)))
        return Indices(l.first .. u.first, l.second .. u.second)
//        return Indices(l.first until u.first, l.second until u.second)
//        return Indices(l.first until u.first, l.second until u.second)
    }
}

fun <S> slice(indices: Indices, terrain: List<List<S>>): List<List<S>> {
    // I don't get why slice uses 'endInclusive + 1'. It's already inclusive, why add 1??
//    return terrain.slice(IntRange(indices.yr.first, indices.yr.last - 1)).map { a -> a.slice(IntRange(indices.xr.first, indices.xr.last - 1)) }
    return terrain.slice(IntRange(indices.yr.first, indices.yr.last)).map { a -> a.slice(IntRange(indices.xr.first, indices.xr.last)) }
}

//@instance(VirtualCard::class)
//interface VirtualCardSemigroup : Semigroup<Terrain> {
//
//    override fun Terrain.combine(b: Terrain): VirtualCard =
//            VirtualCard(
//                    this.id,
//                    this@combine.chargeList + b.chargeList,
//                    this.cvc,
//                    this.expireDate
//            )
//}
//
//val terrainPassableSemigroup: Semigroup<Terrain>  = Semigroup<Terrain> {
//
//}


//fun <S, T>fn2(input: List<List<Terrain>>, fn: (Terrain) -> (S), cFn: (S, S) -> (T)): T {
//    ListK.semigroup<Terrain>().run {
//
//        input.flatten().k().combine()
//
//        listOf(1, 2, 3).k().combine(listOf(4, 5, 6).k())
//    }
//
//    input.flatten().semigroup
//
//}
