package com.mygdx.game.functions

import com.mygdx.game.entities.ProjectileAttributes
import com.mygdx.game.entities.WorldObj

fun moveProjectiles(projectiles: List<WorldObj<ProjectileAttributes>>) {
    projectiles.forEach { projectile ->
        projectile.position = projectile.position.plus(projectile.attributes.velocity)
    }
}

