package com.mygdx.game.entities.worldobj

import com.mygdx.game.draw.DrawState
import com.mygdx.game.entities.PlayerAttributes
import com.mygdx.game.entities.ProjectileAttributes
import com.mygdx.game.entities.Prototypes
import com.mygdx.game.util.EightDirection
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Vec2

class WorldObjFactory(private val prototype: Prototypes) {

    fun createBullet(position: Point2,
                     angle: Angle): WorldObj<ProjectileAttributes> {
        val mod = 9
        val vec = Vec2.create(0, mod).rotate(angle)
        val pos = position.minus(Vec2(prototype.bullet.boundaryDims.width / 2.0,
                prototype.bullet.boundaryDims.height / 2.0))
        return WorldObj(prototype.bullet,
                ProjectileAttributes(EightDirection.WEST, vec), pos, Angle(0))
//                DrawState(0.0f))
    }

    fun player(pos: Point2): WorldObj<PlayerAttributes> =
            WorldObj(prototype.player,
                    PlayerAttributes(EightDirection.NORTH, -100), pos, Angle(0))
//                    DrawState(0f))
}
