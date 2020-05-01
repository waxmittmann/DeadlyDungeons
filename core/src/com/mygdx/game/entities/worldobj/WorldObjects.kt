package com.mygdx.game.entities.worldobj

import com.mygdx.game.collision.WorldObject
import com.mygdx.game.entities.MobAttributes
import com.mygdx.game.entities.PlayerAttributes
import com.mygdx.game.entities.ProjectileAttributes

class SceneNodeAttributes()

class WorldObjects(val player: WorldObject<PlayerAttributes>,
                   var mobs: List<WorldObject<MobAttributes>>,
                   var projectiles: List<WorldObject<ProjectileAttributes>>) {

    fun all(): List<WorldObject<out Any>> {
        return (mobs + player) + projectiles
    }

    fun addMob(mob: WorldObject<MobAttributes>) {
        mobs += mob
    }

    fun addProjectile(projectile: WorldObject<ProjectileAttributes>) {
        projectiles += projectile
    }
}
