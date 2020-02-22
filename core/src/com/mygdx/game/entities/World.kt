package com.mygdx.game.entities

import com.mygdx.game.util.Rect2

class World(val tileSize: Int, val worldObjects: WorldObjs, val terrain: Array<Array<Terrain>> = arrayOf(), var view: Rect2) {
    val width: Int = terrain[0].size * tileSize
    val height: Int = terrain.size * tileSize

    fun updateView(x: Int, y: Int) {
        view = view.plus(x, y)
    }
}

