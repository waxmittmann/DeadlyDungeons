package com.mygdx.game.collision

class CollisionDetector {
    fun <S : HasBoundingBox> check(lhs: S, rhs: Set<S>): Set<S> {
        return rhs.filter { hp -> lhs.collision(hp) }.toSet()
    }
}