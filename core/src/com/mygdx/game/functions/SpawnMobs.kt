package com.mygdx.game.functions

import com.mygdx.game.draw.DrawState
import com.mygdx.game.entities.*
import com.mygdx.game.util.*

const val maxMobs = 20

class SpawnMobState(val lastSpawn: Long)

class SpawnMobs(private val prototypes: Prototypes) {
    val spawnMobs: (World) -> (SpawnMobState) -> (Long) -> SpawnMobState = { world ->
        { state ->
            { curTime ->
                if (world.worldObjects.mobs.size < maxMobs && state.lastSpawn + 1000 < curTime) {

                    val mob = WorldObj(prototypes.yeti, MobAttributes(FullDirectionFns.random(), Vec2.create(1, 1)), Point2.random(world.width, world.height), Angle(0), DrawState(0f) )
                    world.worldObjects.addMob(mob)
                    SpawnMobState(curTime)
                } else {
                    state
                }
            }
        }
    }
}

