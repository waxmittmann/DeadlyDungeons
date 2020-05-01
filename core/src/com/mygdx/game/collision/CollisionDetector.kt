package com.mygdx.game.collision

import com.mygdx.game.util.geometry.Rect2

interface HasAabbBoundingBox {
    fun aabbBox(): Rect2
}

class CollisionDetector {
//    fun <S : AsRect> check(lhs: S, rhs: Collection<S>): List<S> {
//        return rhs.filter { hp -> hp.rect().overlaps(lhs.rect()) }
//    }
//
//    fun <S, T : WorldObj<S>> check(lhs: WorldObj<out Any>,
//                                   rhs: Collection<T>): List<T> {
//        return rhs.filter { hp -> hp.rect().overlaps(lhs.rect()) }
//    }

    fun <S : HasAabbBoundingBox, T : HasAabbBoundingBox> check(lhs: S,
            rhs: Collection<T>): List<T> = rhs.filter { hp -> hp.aabbBox().overlaps(lhs.aabbBox()) }


}