package com.mygdx.game.entities

import com.mygdx.game.draw.DrawState
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.FullDirection
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Vec2

class WorldObjFactory(private val prototype: Prototypes) {

    fun createBullet(position: Point2, angle: Angle): WorldObj<ProjectileAttributes> {
        val mod = 9
        val vec = Vec2.create(0, mod).rotate(angle)
        val pos = position.minus(Vec2(-prototype.bullet.width / 2.0, prototype.bullet.height / 2.0))
        return WorldObj(prototype.bullet, ProjectileAttributes(FullDirection.WEST, vec), pos, Angle(0), DrawState(0.0f))
    }

}
