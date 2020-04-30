package com.mygdx.game.entities.worldobj

import com.mygdx.game.entities.PlayerAttributes
import com.mygdx.game.entities.ProjectileAttributes
import com.mygdx.game.entities.Prototypes
import com.mygdx.game.util.EightDirection
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Vec2

class WorldObjFactory(private val prototype: Prototypes) {

    fun createBullet(position: Point2,
                     angle: Angle): WorldSceneNode<ProjectileAttributes> {
        val mod = 9
        val vec = Vec2.create(0, mod).rotate(angle)
        val pos = position.minus(Vec2(prototype.bullet.boundaryDims.width / 2.0,
                prototype.bullet.boundaryDims.height / 2.0))
        return WorldSceneNode(prototype.bullet,
                ProjectileAttributes(EightDirection.WEST, vec), pos, Angle(0))
    }

    fun player(pos: Point2): WorldSceneNode<PlayerAttributes> {
        return WorldSceneNode(prototype.playerWithSword,PlayerAttributes(EightDirection
                .NORTH, -100), pos, Angle(0))

    }
}
