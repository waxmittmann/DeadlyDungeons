package com.mygdx.game.actions

import com.mygdx.game.entities.World
import com.mygdx.game.entities.terrain.Terrain
import com.mygdx.game.input.movePlayer
import com.mygdx.game.util.EightDirection
import com.mygdx.game.util.FourDirection
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2

class MoveMutationFactory(val amount: Int) {
    val leftUp: Mutation = { world ->
        moveBy(amount,
                FourDirection.UP)(world)
        moveBy(amount,
                FourDirection.LEFT)(world)
        world.changePlayerOrientation(
                EightDirection.NORTH_WEST)
    }
    val rightUp: Mutation = { world ->
        moveBy(amount,
                FourDirection.UP)(world)
        moveBy(amount,
                FourDirection.RIGHT)(world)
        world.changePlayerOrientation(
                EightDirection.NORTH_EAST)
    }
    val leftDown: Mutation = { world ->
        moveBy(amount,
                FourDirection.DOWN)(world)
        moveBy(amount,
                FourDirection.LEFT)(world)
        world.changePlayerOrientation(
                EightDirection.SOUTH_WEST)
    }
    val rightDown: Mutation = { world ->
        moveBy(amount,
                FourDirection.DOWN)(world)
        moveBy(amount,
                FourDirection.RIGHT)(world)
        world.changePlayerOrientation(
                EightDirection.SOUTH_EAST)
    }
    val up: Mutation = { world ->
        moveBy(amount,
                FourDirection.UP)(world)
        world.changePlayerOrientation(
                EightDirection.NORTH)
    }
    val down: Mutation = { world ->
        moveBy(amount,
                FourDirection.DOWN)(world)
        world.changePlayerOrientation(
                EightDirection.SOUTH)
    }
    val left: Mutation = { world ->
        moveBy(amount,
                FourDirection.LEFT)(world)
        world.changePlayerOrientation(
                EightDirection.WEST)
    }
    val right: Mutation = { world ->
        moveBy(amount,
                FourDirection.RIGHT)(world)
        world.changePlayerOrientation(
                EightDirection.EAST)
    }
}

fun moveBy(amount: Int, cardinality: FourDirection): Mutation = { world ->
    val moveBy = movePlayer(
            world.worldObjects.player.rect(), amount, world.terrain,
            { t: Terrain -> t.prototype.attributes.passable },
            amount, cardinality)
    world.movePlayer(moveBy)
}

fun changeOrientation(mouseClick: Point2): Mutation = { world: World ->
    val angle =
            Angle.create(
                    Point2(
                            mouseClick.x - world.view.getWindowDims().width / 2.0,
                            world.view.getWindowDims().height / 2.0 - mouseClick.y))
    world.worldObjects.player.rotation = angle
}