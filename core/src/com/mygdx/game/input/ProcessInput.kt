package com.mygdx.game.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.mygdx.game.entities.World

import arrow.core.compose
import com.mygdx.game.entities.Terrain
import com.mygdx.game.util.Direction

val moveActions = MoveActions(amount = 5)

val readKey: (Unit) -> (Set<Key>) = {
    val keys = emptySet<Key>().toMutableSet()
    if (Gdx.input.isKeyPressed(Input.Keys.UP))
        keys += PressedSpecial.UP
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
        keys += PressedSpecial.DOWN
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        keys += PressedSpecial.LEFT
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        keys += PressedSpecial.RIGHT
    if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        keys += PressedSpecial.ATTACK
    keys
}

val processKeys: (Set<Key>) -> (Set<Action>) = { keys: Set<Key> ->
    val actions = emptySet<Action>().toMutableSet()
    if (keys.contains(PressedSpecial.UP))
        actions.add(moveActions.UP)
    if (keys.contains(PressedSpecial.DOWN))
        actions.add(moveActions.DOWN)
    if (keys.contains(PressedSpecial.LEFT))
        actions.add(moveActions.LEFT)
    if (keys.contains(PressedSpecial.RIGHT))
        actions.add(moveActions.RIGHT)
//    if (keys.contains(PressedSpecial.ATTACK))
//        actions.add(Action.ATTACK)
    actions
}

//fun processAction(world: World, action: Action) {
//        val cardinality = when (action) {
//            Action.MOVE_UP -> Direction.UP
//            Action.MOVE_DOWN -> Direction.DOWN
//            Action.MOVE_LEFT -> Direction.LEFT
//            Action.MOVE_RIGHT -> Direction.RIGHT
////            ATTACK ->
//        }
//        val moveBy = movePlayer(world.worldObjects.player.rect(), 50, world.terrain,
//                { t: Terrain -> t.prototype.attributes.passable }, moveAmount, cardinality)
//        world.movePlayer(moveBy)
//}

//fun processAction(world: World, action: MoveAction) {
//    val cardinality = when (action) {
//        MoveAction.MOVE_UP -> Direction.UP
//        MoveAction.MOVE_DOWN -> Direction.DOWN
//        MoveAction.MOVE_LEFT -> Direction.LEFT
//        MoveAction.MOVE_RIGHT -> Direction.RIGHT
////            ATTACK ->
//    }
//    val moveBy = movePlayer(world.worldObjects.player.rect(), 50, world.terrain,
//            { t: Terrain -> t.prototype.attributes.passable }, moveAmount, cardinality)
//    world.movePlayer(moveBy)
//}
//
//fun processAction(world: World, action: AttackAction) {
//
//}

////interface ProcessAction {
//    fun call(world: World, action: Action)
//}

/*
    action(world)





 */



val processActions: (World) -> (Set<Action>) -> Unit = { world ->
    { actions ->
        for (action in actions) {
            action(world)
            // action(world)
//            processAction(world, action)
        }
    }
}

val processInput: (World) -> Unit = { world ->
    (processActions(world) compose (processKeys compose readKey))(Unit)
}

