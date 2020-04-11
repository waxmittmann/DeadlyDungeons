package com.mygdx.game.entities

fun moveProjectiles(projectiles: List<WorldObj<ProjectileAttributes>>) {
    projectiles.forEach { projectile ->
        projectile.position = projectile.position.plus(projectile.attributes.velocity)
    }
}

