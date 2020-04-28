package com.mygdx.game.actions

import arrow.core.Option
import arrow.core.Some
import arrow.core.k
import com.mygdx.game.collision.CollisionDetector
import com.mygdx.game.entities.World
import com.mygdx.game.entities.WorldFns

fun <S> noop(): (World, S, Long) -> S = { _, s, _ -> s }

class TaskPool {
    var tasks: List<TimedTask<out Any>> = mutableListOf()

    fun add(task: TimedTask<out Any>) {
        tasks = tasks + task
    }

    fun tick(curTime: Long, world: World): List<TimedTask<out Any>> {
        tasks.k().forEach { task ->
            task.tick(world, curTime)
        }

        val (doneTasks, remainingTasks) = tasks.partition {
            if (it.done(curTime)) {
                it.callDoneFn(world, curTime)
                true
            } else {
                false
            }
        }

        tasks = remainingTasks

        return doneTasks
    }
}

class TimedTask<S>(val timeStarted: Long,
                   val maybeMillisUntilComplete: Option<Long>,
                   val tickFn: (World, S, Long) -> (Pair<S, Boolean>),
                   val doneFn: (World, S, Long) -> S, var state: S) {

    var terminateEarly: Boolean = false

    fun done(curTime: Long): Boolean =
            terminateEarly || maybeMillisUntilComplete.fold({ false },
                    { millisUntilComplete -> curTime > (timeStarted + millisUntilComplete) })

    fun tick(world: World, curTime: Long) {
        if (done(curTime)) throw Exception("Already done.")
        val (newState, newTerminateEarly) = tickFn(world, state, curTime)
        state = newState
        terminateEarly = newTerminateEarly
    }

    fun callDoneFn(world: World, curTime: Long) {
        state = doneFn(world, state, curTime)
    }
}

fun attackTask(start: Long): TimedTask<Unit> {
    val collisionDetector = CollisionDetector()

    return TimedTask<Unit>(start, Some(200), { world: World, _: Unit, _: Long ->
//            world.worldObjects.mobs.

        Pair(Unit, false)
    }, noop(), Unit)
}


val attackMutation: Mutation = fromWorld { world ->
    if (world.worldObjects.player.attributes.lastShot + 200 < world.timeNow) {
        world.worldObjects.player.attributes.lastShot = world.timeNow
        WorldFns.addPlayerBullet(world)
//        performPlayerAttack();
    }
}