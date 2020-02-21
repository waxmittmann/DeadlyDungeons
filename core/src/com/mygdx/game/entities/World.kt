package com.mygdx.game.entities

import com.mygdx.game.util.Rect2

class World(val tileSize: Int, val worldObjects: WorldObjs, val terrain: Array<Array<Terrain>> = arrayOf(), var view: Rect2) {
    fun updateView(x: Int, y: Int) {
        view = view.plus(x, y)
        println("Changed to ${view.lx} ${view.ly}")
    }
}

