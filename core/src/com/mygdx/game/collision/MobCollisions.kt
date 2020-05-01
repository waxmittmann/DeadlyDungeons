package com.mygdx.game.collision

import arrow.core.extensions.list.foldable.nonEmpty
import com.mygdx.game.entities.MobAttributes
import com.mygdx.game.entities.World
import com.mygdx.game.entities.worldobj.SceneNodeAttributes
import com.mygdx.game.entities.worldobj.WorldSceneNode

private val collisionDetector = CollisionDetector()

typealias WorldObject<S> = WorldSceneNode<SceneNodeAttributes, S>

fun processCollisions(world: World) {

    val mobPlayerCollisions: List<WorldObject<MobAttributes>> =
            collisionDetector.check(world.worldObjects.player,
                    world.worldObjects.mobs)

    world.worldObjects.projectiles =
            world.worldObjects.projectiles.filter { projectile ->
                val mobBulletCollisions: List<WorldObject<MobAttributes>> =
                        collisionDetector.check(projectile,
                                world.worldObjects.mobs)
                if (mobBulletCollisions.nonEmpty()) {
                    world.worldObjects.mobs =
                            world.worldObjects.mobs.filter { o ->
                                !mobPlayerCollisions.contains(o)
                            }.filter { o -> !mobBulletCollisions.contains(o) }
                    false
                } else {
                    true
                }
            }

}
