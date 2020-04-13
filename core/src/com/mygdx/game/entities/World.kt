package com.mygdx.game.entities

import com.mygdx.game.entities.terrain.Terrain
import com.mygdx.game.entities.worldobj.WorldObj
import com.mygdx.game.entities.worldobj.WorldObjFactory
import com.mygdx.game.entities.worldobj.WorldObjs
import com.mygdx.game.util.FullDirection
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Vec2

class World(playerPos: Point2, mobs: List<WorldObj<MobAttributes>>,
            var timeNow: Long, private val worldObjFactory: WorldObjFactory,
            val tileSize: Int, val terrain: List<List<Terrain>> = listOf(),
            windowDims: Dims2) {

    val view: View
    val worldObjects: WorldObjs

    init {
        val player: WorldObj<PlayerAttributes> =
                worldObjFactory.player(playerPos)
        view = View(playerPos, player.prototype.size, windowDims)
        worldObjects = WorldObjs(player, mobs, emptyList())
    }

    val width: Int = terrain[0].size * tileSize
    val height: Int = terrain.size * tileSize

    // Does not check validity of move
    fun movePlayer(moveBy: Vec2) {
        worldObjects.player.position = worldObjects.player.position.plus(moveBy)
        view.plus(moveBy)
    }

    fun changePlayerOrientation(direction: FullDirection) {
        worldObjects.player.attributes.orientation = direction
    }

    fun addPlayerBullet() {
        val midpoint = worldObjects.player.rect().midpoint()
        worldObjects.addProjectile(worldObjFactory.createBullet(midpoint,
                worldObjects.player.rotation))
    }

    fun setTime(time: Long) {
        timeNow = time
    }

    fun updateWindowSize(newDims: Dims2) {
        view.setWindowDims(newDims)
    }
}

