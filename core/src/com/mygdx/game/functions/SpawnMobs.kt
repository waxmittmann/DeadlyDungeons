package com.mygdx.game.functions

import com.mygdx.game.draw.DrawState
import com.mygdx.game.entities.*
import com.mygdx.game.util.Angle
import com.mygdx.game.util.FullDirection
import com.mygdx.game.util.FullDirectionFns
import com.mygdx.game.util.Point2

const val maxMobs = 20

class SpawnMobState(val lastSpawn: Long)

class SpawnMobs(private val prototypes: Prototypes) {
    val spawnMobs: (World) -> (SpawnMobState) -> (Long) -> SpawnMobState = { world ->
        { state ->
            { curTime ->
                if (world.worldObjects.mobs.size < maxMobs && state.lastSpawn + 1000 < curTime) {

                    val mob = WorldObj(prototypes.yeti, Attributes(FullDirectionFns.random()), Point2.random(world.width, world.height), DrawState(0f))
                    world.worldObjects.addMob(mob)
                    SpawnMobState(curTime)
                } else {
                    state
                }
            }
        }
    }
}

