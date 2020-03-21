package com.mygdx.game.collision

import com.mygdx.game.entities.AsRect

class CollisionDetector {
    fun <S : AsRect> check(lhs: S, rhs: Collection<S>): List<S> {
        return rhs.filter { hp -> hp.rect().overlaps(lhs.rect()) }
    }
}