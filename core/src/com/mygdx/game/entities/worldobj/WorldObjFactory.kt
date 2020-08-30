package com.mygdx.game.entities.worldobj

import com.mygdx.game.collision.WorldObject
import com.mygdx.game.entities.PlayerAttributes
import com.mygdx.game.entities.ProjectileAttributes
import com.mygdx.game.entities.Prototypes
import com.mygdx.game.entities.SceneNodeData
import com.mygdx.game.scenegraph.Leaf
//import com.mygdx.game.scenegraph.calcBoundingBox
import com.mygdx.game.util.EightDirection
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
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
//        val aabb = calcBoundingBox(prototype.bullet)
        // TODO: Fix
//        val aabb = calcBoundingBox(null)
        val aabb = Rect2(0.0, 0.0, 0.0, 0.0)
//        val pos = position.minus( aabb.v!!.asVec2().div(2.0))
        val pos = Point2(0.0, 0.0)

//        return WorldSceneNode(prototype.bullet,
        // TODO: fix
//        return null
//        return WorldSceneNode(null,
          return WorldSceneNode(Leaf<SceneNodeData, SceneNodeData>(SceneNodeData("", null)),
                ProjectileAttributes(EightDirection.WEST, vec), pos, Angle(0f))
    }

    fun player(pos: Point2): WorldObject<PlayerAttributes> {
//        return WorldSceneNode(prototype.playerWithSword,PlayerAttributes(EightDirection
        return WorldSceneNode(Leaf(SceneNodeData("", null)),PlayerAttributes(EightDirection
                .NORTH, -100), pos, Angle(0f))

    }
}
