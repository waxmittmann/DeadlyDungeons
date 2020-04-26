package com.mygdx.game.entities

import com.mygdx.game.entities.worldobj.WorldSceneNode

fun moveProjectiles(projectiles: List<WorldSceneNode<ProjectileAttributes>>) {
    projectiles.forEach { projectile ->
        projectile.position =
                projectile.position.plus(projectile.attributes.velocity)
    }
}

