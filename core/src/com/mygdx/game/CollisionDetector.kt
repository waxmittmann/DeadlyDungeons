package com.mygdx.game

class CollisionDetector {
    fun <S : HasBoundingBox> check(lhs: S, rhs: List<S>): List<S> {
        return rhs.filter { hp -> lhs.collision(hp) }
    }
}