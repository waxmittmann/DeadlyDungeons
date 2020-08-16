package com.mygdx.game.entities.worldobj

import com.mygdx.game.collision.WorldObject
import com.mygdx.game.entities.PlayerAttributes
import com.mygdx.game.entities.ProjectileAttributes
import com.mygdx.game.entities.Prototypes
import com.mygdx.game.scenegraph.calcBoundingBox
import com.mygdx.game.util.EightDirection
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Vec2

class WorldObjFactory(private val prototype: Prototypes) {

    fun createBullet(position: Point2,
                     angle: Angle): WorldObject<ProjectileAttributes> {
        val mod = 9
        val vec = Vec2.create(0, mod).rotate(angle)

//        val pos = position.minus(Vec2(prototype.bullet.aabb(WrappedMatrix())
//                .width /
//                2.0,
//                prototype.bullet.aabb(WrappedMatrix()).height / 2.0))
        val aabb = calcBoundingBox(prototype.bullet)
        val pos = position.minus( aabb.v!!.asVec2().div(2.0))

        return WorldSceneNode(prototype.bullet,
                ProjectileAttributes(EightDirection.WEST, vec), pos, Angle(0f))
    }

    fun player(pos: Point2): WorldObject<PlayerAttributes> {
        return WorldSceneNode(prototype.playerWithSword,PlayerAttributes(EightDirection
                .NORTH, -100), pos, Angle(0f))

    }
}
