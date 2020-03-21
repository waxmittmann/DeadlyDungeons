package com.mygdx.game.entities

class WorldObjs(val player: WorldObj, var mobs: List<WorldObj>) {
    fun all(): List<WorldObj> {
        return mobs + player
    }

    fun addMob(mob: WorldObj) {
        mobs += mob
    }
}