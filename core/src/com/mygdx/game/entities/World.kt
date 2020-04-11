package com.mygdx.game.entities

import com.mygdx.game.entities.terrain.Terrain
import com.mygdx.game.entities.worldobj.WorldObjFactory
import com.mygdx.game.entities.worldobj.WorldObjs
import com.mygdx.game.util.FullDirection
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2

class World(var timeNow: Long, val worldObjFactory: WorldObjFactory, val tileSize: Int, val worldObjects: WorldObjs, val terrain: List<List<Terrain>> = listOf(), var view: Rect2) {
    val width: Int = terrain[0].size * tileSize
    val height: Int = terrain.size * tileSize

    // Does not check validity of move
    fun movePlayer(moveBy: Vec2) {
        worldObjects.player.position = worldObjects.player.position.plus(moveBy)
        view = view.plus(moveBy)
    }

    fun changePlayerOrientation(direction: FullDirection) {
        worldObjects.player.attributes.orientation = direction
    }

    fun addPlayerBullet() {
        worldObjects.addProjectile(worldObjFactory.createBullet(worldObjects.player.rect().midpoint(), worldObjects.player.rotation))
        println("Bullet added for " + worldObjects.player.position + ", " + worldObjects.player.attributes.orientation)
    }

    fun setTime(time: Long) {
        timeNow = time
    }
}

