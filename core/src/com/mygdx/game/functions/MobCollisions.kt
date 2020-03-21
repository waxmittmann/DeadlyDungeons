package com.mygdx.game.functions

import com.mygdx.game.collision.CollisionDetector
import com.mygdx.game.entities.World
import com.mygdx.game.entities.WorldObj

private val collisionDetector = CollisionDetector()

fun processCollisions(world: World) {
    val collisions: List<WorldObj> = collisionDetector.check(world.worldObjects.player, world.worldObjects.mobs)
    world.worldObjects.mobs = world.worldObjects.mobs.filter { o -> !collisions.contains(o) }
}
