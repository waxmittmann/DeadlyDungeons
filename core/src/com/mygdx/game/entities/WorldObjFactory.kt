package com.mygdx.game.entities

import com.mygdx.game.draw.DrawState
import com.mygdx.game.draw.Textures
import com.mygdx.game.entities.*
import com.mygdx.game.util.FullDirection
import com.mygdx.game.util.Point2
import com.mygdx.game.util.Vec2

class WorldObjFactory(private val prototype: Prototypes) {

    fun createBullet(position: Point2, orientation: FullDirection): WorldObj<ProjectileAttributes> {
        val mod = 9
        val velocity = when (orientation) {
            FullDirection.SOUTH -> Vec2(0, -1 * mod)
            FullDirection.SOUTH_WEST -> Vec2(-1* mod, -1* mod)
            FullDirection.WEST -> Vec2(-1*mod, 0)
            FullDirection.NORTH_WEST -> Vec2(-1* mod, 1* mod)
            FullDirection.NORTH -> Vec2(0, 1* mod)
            FullDirection.NORTH_EAST -> Vec2(1* mod, 1* mod)
            FullDirection.EAST -> Vec2(1* mod, 0)
            FullDirection.SOUTH_EAST -> Vec2(1* mod, -1* mod)
        }

       return WorldObj(prototype.bullet, ProjectileAttributes(orientation, velocity), position, DrawState(0.0f))
    }

}
