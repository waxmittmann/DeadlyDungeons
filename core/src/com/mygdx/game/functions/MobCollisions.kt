package com.mygdx.game.functions

import arrow.core.extensions.list.foldable.nonEmpty
import com.mygdx.game.collision.CollisionDetector
import com.mygdx.game.entities.MobAttributes
import com.mygdx.game.entities.World
import com.mygdx.game.entities.WorldObj

private val collisionDetector = CollisionDetector()

fun processCollisions(world: World) {
    val mobPlayerCollisions: List<WorldObj<MobAttributes>> = collisionDetector.check(world.worldObjects.player, world.worldObjects.mobs)


    world.worldObjects.projectiles.filter {projectile ->
        val mobBulletCollisions: List<WorldObj<MobAttributes>> = collisionDetector.check(projectile, world.worldObjects.mobs)
        if (mobBulletCollisions.nonEmpty()) {
            world.worldObjects.mobs = world.worldObjects.mobs.filter { o -> !mobPlayerCollisions.contains(o) }.filter { o -> !mobBulletCollisions.contains(o) }
            false
        } else {
            true
        }
    }

}
