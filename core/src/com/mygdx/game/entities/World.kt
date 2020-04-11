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
//        playerPos = playerPos.plus(Vec2(10.0, -10.0))
        val player: WorldObj<PlayerAttributes> = worldObjFactory.player(playerPos)
//        val viewPos: Point2 = playerPos.plus(Vec2(player.prototype.width / 2.0, -player.prototype.width/2.0))
//        val viewPos: Point2 = playerPos.plus(Vec2(player.prototype.width.toDouble() + 10, -player.prototype.width/2.0 - 10))
//        val viewPos: Point2 = playerPos.plus(Vec2(player.prototype.width.toDouble() + 13, -player.prototype.width/2.0 - 13))

//        val viewPos: Point2 = playerPos.plus(Vec2(player.prototype.width.toDouble() + 13, -player.prototype.height/2.0 - 13))
//        val viewPos: Point2 = playerPos.plus(Vec2(player.prototype.width.toDouble(), -player.prototype.height/2.0))
//        val viewPos: Point2 = playerPos.plus(Vec2(player.prototype.width.toDouble(), -player.prototype.height.toDouble()))
//        val viewPos: Point2 = playerPos.plus(Vec2(player.prototype.width.toDouble()/2.0, player.prototype.height/2.0))

        println(player.prototype.width)

//        val viewPos: Point2 = playerPos.minus(Vec2(player.prototype.width/2.0, player.prototype.height/2.0))
//        val viewPos: Point2 = playerPos.plus(Vec2(50.0, -50.0))
//        val viewPos: Point2 = playerPos.plus(Vec2(62.5, -37.5))
        val viewPos: Point2 = playerPos //.plus(Vec2(62.5, -37.5))
        // (1.25 * width, -0.75 * height)

//        val viewPos: Point2 = playerPos.plus(Vec2(player.prototype.width.toDouble() + 25, -player.prototype.width/2.0 - 25))
//        val viewPos: Point2 = playerPos.plus(Vec2(0.0, -player.prototype.width/2.0))

        val viewCenter = Point2(windowDims.width / 2.0, windowDims.height / 2.0)
                .plus(Vec2(player.prototype.width/2.0, player.prototype.height/2.0))

//        view = View(viewPos, viewDims)
        view = View(viewPos, viewCenter, viewDims)



        worldObjects = WorldObjs(player, mobs, emptyList())
        println(view)
        println(worldObjects.player)
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
//        val midpoint = worldObjects.player.position

//        worldObjects.addProjectile(worldObjFactory.createBullet(worldObjects.player.rect().midpoint(),
        worldObjects.addProjectile(worldObjFactory.createBullet(midpoint,
                worldObjects.player.rotation))
//        println("Bullet added for " + worldObjects.player.position + ", " + worldObjects.player.attributes.orientation)
        println("Bullet added for " + worldObjects.player.position + ", " + worldObjects.player.rotation)
    }

    fun setTime(time: Long) {
        timeNow = time
    }
}

