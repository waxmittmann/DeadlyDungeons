package com.mygdx.game.entities.worldobj

import com.mygdx.game.draw.DrawState
import com.mygdx.game.entities.PlayerAttributes
import com.mygdx.game.entities.ProjectileAttributes
import com.mygdx.game.entities.Prototypes
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.FullDirection
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Vec2

class WorldObjFactory(private val prototype: Prototypes) {

    fun createBullet(position: Point2, angle: Angle): WorldObj<ProjectileAttributes> {
        val mod = 9
//        val mod = 0
        val vec = Vec2.create(0, mod).rotate(angle)
        val pos = position.minus(Vec2(prototype.bullet.width / 2.0, prototype.bullet.height / 2.0))
//        val pos = position.plus(Vec2(prototype.bullet.width / 2.0, prototype.bullet.height / 2.0))
//        val pos = position
        return WorldObj(prototype.bullet, ProjectileAttributes(FullDirection.WEST, vec), pos, Angle(0), DrawState(0.0f))
    }

    fun player(pos: Point2): WorldObj<PlayerAttributes> =
            WorldObj(prototype.player, PlayerAttributes(FullDirection.NORTH, -100), pos, Angle(0),
                    DrawState(0f))

//    fun player(centerPos: Point2, viewDims: Dims2): WorldObj<PlayerAttributes> =
//       WorldObj(prototype.player,
//                PlayerAttributes(FullDirection.NORTH, -100),
//                Point2(
//                        centerPos.x + prototype.player.width.toFloat() / 2 + viewDims.width / 2,
//                        centerPos.y + prototype.player.height.toFloat() / 2 + viewDims.height / 2),
//               Angle(0), DrawState(0f))
}
