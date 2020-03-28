package com.mygdx.game.entities

import com.mygdx.game.draw.DrawState
import com.mygdx.game.draw.Textures
import com.mygdx.game.entities.*
import com.mygdx.game.util.FullDirection
import com.mygdx.game.util.Point2
import com.mygdx.game.util.Vec2

class WorldObjFactory(private val prototype: Prototypes) {

    fun createBullet(position: Point2, orientation: FullDirection): WorldObj<ProjectileAttributes> {
       return WorldObj(prototype.bullet, ProjectileAttributes(orientation, Vec2(1, 1)), position, DrawState(0.0f))
    }

}
