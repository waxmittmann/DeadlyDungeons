package com.mygdx.game.entities

import com.mygdx.game.entities.worldobj.WorldObj

fun moveProjectiles(projectiles: List<WorldObj<ProjectileAttributes>>) {
    projectiles.forEach { projectile ->
        projectile.position =
                projectile.position.plus(projectile.attributes.velocity)
    }
}

