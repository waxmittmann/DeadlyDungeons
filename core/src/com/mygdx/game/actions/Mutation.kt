package com.mygdx.game.actions

import com.mygdx.game.actions.old.GameState
import com.mygdx.game.entities.World

typealias WorldMutation = (World) -> Unit
typealias Mutation = (GameState) -> Unit

fun fromWorld(m: WorldMutation): Mutation = { m(it.world) }

