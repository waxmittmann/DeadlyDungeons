package com.mygdx.game.entities.worldobj

import com.mygdx.game.entities.MobAttributes
import com.mygdx.game.entities.PlayerAttributes
import com.mygdx.game.entities.ProjectileAttributes

typealias WorldObj<S> = WorldObjV2<S>

class WorldObjs(val player: WorldObj<PlayerAttributes>,
                var mobs: List<WorldObj<MobAttributes>>,
                var projectiles: List<WorldObj<ProjectileAttributes>>) {

    fun all(): List<WorldObj<out Any>> {
        return (mobs + player) + projectiles
    }

    fun addMob(mob: WorldObj<MobAttributes>) {
        mobs += mob
    }

    fun addProjectile(projectile: WorldObj<ProjectileAttributes>) {
        projectiles += projectile
    }
}
