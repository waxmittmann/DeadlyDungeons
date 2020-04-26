package com.mygdx.game.entities.worldobj

import com.mygdx.game.entities.MobAttributes
import com.mygdx.game.entities.PlayerAttributes
import com.mygdx.game.entities.ProjectileAttributes

class WorldObjects(val player: WorldSceneNode<PlayerAttributes>,
                   var mobs: List<WorldSceneNode<MobAttributes>>,
                   var projectiles: List<WorldSceneNode<ProjectileAttributes>>) {

    fun all(): List<WorldSceneNode<out Any>> {
        return (mobs + player) + projectiles
    }

    fun addMob(mob: WorldSceneNode<MobAttributes>) {
        mobs += mob
    }

    fun addProjectile(projectile: WorldSceneNode<ProjectileAttributes>) {
        projectiles += projectile
    }
}
