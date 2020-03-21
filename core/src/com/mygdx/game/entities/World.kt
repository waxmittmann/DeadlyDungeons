package com.mygdx.game.entities

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.extensions.list.foldable.foldLeft
import arrow.core.getOrElse
import com.mygdx.game.input.moveAmount
import com.mygdx.game.util.Cardinality
import com.mygdx.game.util.Point2
import com.mygdx.game.util.Rect2
import com.mygdx.game.util.Vec2

class World(val tileSize: Int, val worldObjects: WorldObjs, val terrain: List<List<Terrain>> = listOf(), var view: Rect2) {
    val width: Int = terrain[0].size * tileSize
    val height: Int = terrain.size * tileSize

    // Does not check validity of move
    fun movePlayer(moveBy: Vec2) {
        worldObjects.player.position = worldObjects.player.position.plus(moveBy)
        view = view.plus(moveBy)
    }
}

