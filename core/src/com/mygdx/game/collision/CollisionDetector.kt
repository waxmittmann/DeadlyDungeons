package com.mygdx.game.collision

import com.mygdx.game.util.geometry.AsRect

class CollisionDetector {
    fun <S : AsRect> check(lhs: S, rhs: Collection<S>): List<S> {
        return rhs.filter { hp -> hp.rect().overlaps(lhs.rect()) }
    }

    fun <S, T : WorldObj<S>> check(lhs: WorldObj<out Any>,
                                   rhs: Collection<T>): List<T> {
        return rhs.filter { hp -> hp.rect().overlaps(lhs.rect()) }
    }
}