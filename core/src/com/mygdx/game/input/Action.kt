package com.mygdx.game.input

import com.mygdx.game.entities.Terrain
import com.mygdx.game.entities.World
import com.mygdx.game.util.Direction

//interface Action

//interface MoveAction

//enum class MoveAction: Action {
//enum class MoveAction : Action {
//enum class Action {
//    MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT
//}

//object AttackAction : Action


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

//    enum class MoveAction(action: Action) {
//        MOVE_UP(moveBy(amount, Direction.UP)) //, MOVE_DOWN(action), MOVE_LEFT, MOVE_RIGHT
//    }

}

val AttackAction: Action = { world ->

}

//enum class Action {
//    MOVE, ATTACK
//}

//object ATTACK : Action
