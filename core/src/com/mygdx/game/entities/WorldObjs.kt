package com.mygdx.game.entities

class WorldObjs(val player: WorldObj, val mobs: List<WorldObj>) {

    fun all(): List<WorldObj> {
        return mobs + player
    }

}