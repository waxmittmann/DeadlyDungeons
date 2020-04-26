package com.mygdx.game.entities

import com.mygdx.game.entities.worldobj.WorldSceneNode
import com.mygdx.game.util.EightDirectionFns
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Vec2

const val maxMobs = 20
typealias WorldObj<S> = WorldSceneNode<S>

class SpawnMobState(val lastSpawn: Long)

class SpawnMobs(private val prototypes: Prototypes) {
    val spawnMobs: (World) -> (SpawnMobState) -> (Long) -> SpawnMobState =
            { world ->
                { state ->
                    { curTime ->
                        if (world.worldObjects.mobs.size < maxMobs && state.lastSpawn + 1000 < curTime) {
                            val mob = WorldSceneNode(prototypes.yeti,
                                    MobAttributes(EightDirectionFns.random(),
                                            Vec2.create(1, 1)),
                                    Point2.random(world.width, world.height),
                                    Angle(0)) //, DrawState(0f))
                            world.worldObjects.addMob(mob)
                            SpawnMobState(curTime)
                        } else {
                            state
                        }
                    }
                }
            }
}

