package com.mygdx.game.entities

import com.mygdx.game.collision.WorldObject

fun moveProjectiles(projectiles: List<WorldObject<ProjectileAttributes>>) {
    projectiles.forEach { projectile ->
        projectile.position =
                projectile.position.plus(projectile.attributes.velocity)
    }
}

