package com.mygdx.game.util.geometry

interface HasRect2 {
    fun rect(): Rect2
}

class Rect2(val lx: Double, val ly: Double, val width: Double,
            val height: Double) {
    fun ux(): Double = lx + width
    fun uy(): Double = ly + height

    val lxF = lx.toFloat()
    val lyF = ly.toFloat()
    val widthF = width.toFloat()
    val heightF = height.toFloat()

    fun lowerLeft(): Point2 {
        return Point2(lx, ly)
    }

    fun plus(vec2: Vec2): Rect2 {
        return Rect2(lx + vec2.x, ly + vec2.y, width, height)
    }

    fun minus(vec2: Vec2): Rect2 {
        return Rect2(lx - vec2.x, ly - vec2.y, width, height)
    }

    override fun toString(): String {
        return "($lx, $ly), (${lx + width}, ${ly + height})"
    }

    fun moduloX(modX: Int): Rect2 {
        assert(modX != 0)
        return Rect2((lx % modX) * modX, ly, width, height)
    }

    fun moduloY(modY: Int): Rect2 {
        assert(modY != 0)
        return Rect2(lx, (ly % modY) * modY, width, height)
    }

    fun add(_x: Int, _y: Int): Rect2 {
        return Rect2(lx + _x, ly + _y, width, height)
    }

    fun shrink(xs: Int, ys: Int): Rect2 {
        return Rect2(lx + 1, ly + 1, width - 1, height - 1)
    }

    fun upperRight(): Point2 {
        return Point2(lx + width, ly + height)
    }

    fun overlaps(other: Rect2): Boolean {
        if (lx > other.ux()) return false
        if (other.lx > ux()) return false
        if (ly > other.uy()) return false
        if (other.uy() > uy()) return false
        return true
    }

    fun midpoint(): Point2 = Point2(lx + width / 2, ly + height / 2)

    fun center(): Rect2 = Rect2(lx + width / 2.0, ly + height / 2.0, width /
            2.0, height / 2.0)

    fun expand(v: Rect2): Rect2 {
        val lx = java.lang.Double.min(v.lx, lx)
        val ly = java.lang.Double.min(v.ly, ly)
        val ux = java.lang.Double.max(v.ux(), ux())
        val uy = java.lang.Double.max(v.uy(), uy())
        return fromLowerUpper(lx, ly, ux, uy)!!
    }

    fun uxF(): Float {
        return ux().toFloat()
    }

    fun uyF(): Float {
        return uy().toFloat()
    }

    fun asVec2(): Vec2 = Vec2(width, height)

    fun asDims(): Dims2 = Dims2(widthF, heightF)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rect2

        if (lx != other.lx) return false
        if (ly != other.ly) return false
        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = lx.hashCode()
        result = 31 * result + ly.hashCode()
        result = 31 * result + width.hashCode()
        result = 31 * result + height.hashCode()
        return result
    }

//    val asDims: Dims2 by lazy { Dims2(widthF, heightF) }

//    val asPoylgon: Polygon2_4 by lazy {
//        Polygon2_4(Point2(lx, ly), Point2(lx + width, ly),
//                Point2(lx + width, ly + height), Point2(lx, ly + height))
//        PolygonBuilder(Point2(lx, ly)).moveX(width).moveY(height).moveX(-width)
//                .build()
//    }

    val asPoints: List<Point2> by lazy {
        listOf(lowerLeft(), upperRight(), Point2(lx, uy()), Point2(ux(),
                ly))
    }

    companion object Factory {
        fun create(lx: Int, ly: Int, width: Int, height: Int): Rect2 =
                Rect2(lx.toDouble(), ly.toDouble(), width.toDouble(),
                        height.toDouble())

        fun fromUpperRight(ux: Int, uy: Int, width: Int, height: Int): Rect2 {
            return create(ux - width, uy - height, width, height)
        }

        fun fromLowerUpper(lx: Int, ly: Int, ux: Int, uy: Int): Rect2? {
            return if (lx >= ux || ly >= uy) null
            else create(lx, ly, ux - lx, uy - ly)
        }

        fun fromLowerUpper(lx: Double, ly: Double, ux: Double,
                           uy: Double): Rect2? {
            if (lx >= ux || ly >= uy) throw Exception("$lx >= $ux OR $ly >= $uy")
            return Rect2(lx, ly, ux - lx, uy - ly)
        }

        fun fromPoints(ll: Point2, ur: Point2): Rect2 {
            assert(ll.x < ur.x)
            assert(ll.y < ur.y)
            return Rect2(ll.x, ll.y, ur.x - ll.x, ur.y - ll.y)
        }

        // TODO(wittie): Why was this commented out?
        fun create(lowerLeft: Point2, dims: Dims2): Rect2 {
            return Rect2(lowerLeft.x, lowerLeft.y, dims.width.toDouble(),
                    dims.height.toDouble())
        }

        fun aabbFromRects(p: List<Rect2>) =
                aabb(p.flatMap { it.asPoints })

        //        fun aabbFrom(p: List<Polygon2_4>) =
//            aabb(p.flatMap { it.vertices })
//
        fun aabb(points: List<Point2>): Rect2 {
            var lx = points[0].x
            var ly = points[0].y
            var ux = points[0].x
            var uy = points[0].y

            for (point in points) {
                if (point.x < lx) lx = point.x
                if (point.x > ux) ux = point.x
                if (point.y < ly) ly = point.y
                if (point.y > uy) uy = point.y
            }

            return fromLowerUpper(lx, ly, ux, uy)!!
        }
    }
}

