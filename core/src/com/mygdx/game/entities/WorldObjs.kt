package com.mygdx.game.entities




class WorldObjs(val player: WorldObj<PlayerAttributes>, var mobs: List<WorldObj<MobAttributes>>, var projectiles: List<WorldObj<ProjectileAttributes>>) {

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
