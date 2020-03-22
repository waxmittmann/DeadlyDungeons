package com.mygdx.game.input

import com.mygdx.game.entities.Terrain
import com.mygdx.game.entities.World
import com.mygdx.game.util.Direction

typealias Action = (World) -> Unit

fun moveBy(amount: Int, cardinality: Direction): Action = { world ->
    val moveBy = movePlayer(world.worldObjects.player.rect(), amount, world.terrain,
            { t: Terrain -> t.prototype.attributes.passable }, amount, cardinality)
    world.movePlayer(moveBy)
}

class MoveActions(val amount: Int) {
    val UP = moveBy(amount, Direction.UP)
    val DOWN = moveBy(amount, Direction.DOWN)
    val LEFT = moveBy(amount, Direction.LEFT)
    val RIGHT = moveBy(amount, Direction.RIGHT)
}

val AttackAction: Action = { world ->

}

