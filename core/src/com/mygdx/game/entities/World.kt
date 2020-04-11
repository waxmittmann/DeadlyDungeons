package com.mygdx.game.entities

import arrow.core.invalid
import com.mygdx.game.entities.terrain.Terrain
import com.mygdx.game.entities.worldobj.WorldObj
import com.mygdx.game.entities.worldobj.WorldObjFactory
import com.mygdx.game.entities.worldobj.WorldObjs
import com.mygdx.game.util.FullDirection
import com.mygdx.game.util.geometry.*

class World(var playerPos: Point2, viewDims: Dims2, mobs: List<WorldObj<MobAttributes>>, var timeNow: Long,
            private val worldObjFactory: WorldObjFactory, val tileSize: Int,
            val terrain: List<List<Terrain>> = listOf(),
            val windowDims: Dims2) {

    val view: View
    val worldObjects: WorldObjs

    init {
        val player: WorldObj<PlayerAttributes> = worldObjFactory.player(playerPos)
        val viewCenter = Point2(windowDims.width / 2.0, windowDims.height / 2.0)
                .plus(Vec2(player.prototype.width/2.0, player.prototype.height/2.0))
        view = View(playerPos, viewCenter, viewDims)

        worldObjects = WorldObjs(player, mobs, emptyList())
    }

    val width: Int = terrain[0].size * tileSize
    val height: Int = terrain.size * tileSize

    // Does not check validity of move
    fun movePlayer(moveBy: Vec2) {
        worldObjects.player.position = worldObjects.player.position.plus(moveBy)
        view.plus(moveBy)
        println("Player: ${worldObjects.player}\nView: $view\n---")
    }

    fun changePlayerOrientation(direction: FullDirection) {
        worldObjects.player.attributes.orientation = direction
    }

    fun addPlayerBullet() {
        val midpoint = worldObjects.player.rect().midpoint()
        worldObjects.addProjectile(worldObjFactory.createBullet(midpoint, worldObjects.player.rotation))
    }

    fun setTime(time: Long) {
        timeNow = time
    }
}

