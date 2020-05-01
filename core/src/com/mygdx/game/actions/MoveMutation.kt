package com.mygdx.game.actions

import com.mygdx.game.entities.World
import com.mygdx.game.entities.WorldFns
import com.mygdx.game.entities.WorldFns.changePlayerOrientation
import com.mygdx.game.entities.terrain.Terrain
import com.mygdx.game.input.movePlayer
import com.mygdx.game.util.EightDirection
import com.mygdx.game.util.FourDirection
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2

class MoveMutationFactory(val amount: Int) {
    val leftUp: Mutation = fromWorld { world ->
        moveBy(amount,
                FourDirection.UP)(world)
        moveBy(amount,
                FourDirection.LEFT)(world)
        changePlayerOrientation(world, 
                EightDirection.NORTH_WEST)
    }
    
    val rightUp: Mutation = fromWorld { world ->
        moveBy(amount,
                FourDirection.UP)(world)
        moveBy(amount,
                FourDirection.RIGHT)(world)
        changePlayerOrientation(world, 
                EightDirection.NORTH_EAST)
    }
    val leftDown: Mutation = fromWorld { world ->
        moveBy(amount,
                FourDirection.DOWN)(world)
        moveBy(amount,
                FourDirection.LEFT)(world)
        changePlayerOrientation(world, 
                EightDirection.SOUTH_WEST)
    }
    val rightDown: Mutation = fromWorld { world ->
        moveBy(amount,
                FourDirection.DOWN)(world)
        moveBy(amount,
                FourDirection.RIGHT)(world)
        changePlayerOrientation(world, 
                EightDirection.SOUTH_EAST)
    }
    val up: Mutation = fromWorld { world ->
        moveBy(amount,
                FourDirection.UP)(world)
        changePlayerOrientation(world, 
                EightDirection.NORTH)
    }
    val down: Mutation = fromWorld { world ->
        moveBy(amount,
                FourDirection.DOWN)(world)
        changePlayerOrientation(world, 
                EightDirection.SOUTH)
    }
    val left: Mutation = fromWorld { world ->
        moveBy(amount,
                FourDirection.LEFT)(world)
        changePlayerOrientation(world, 
                EightDirection.WEST)
    }
    val right: Mutation = fromWorld { world ->
        moveBy(amount,
                FourDirection.RIGHT)(world)
        changePlayerOrientation(world, 
                EightDirection.EAST)
    }
}

fun moveBy(amount: Int, cardinality: FourDirection): WorldMutation = { world ->
    // Using UNTRANSFORMED player bounding box at the moment to calculate
    // distance to terrain.
    val moveBy = movePlayer(
            world.worldObjects.player.aabbBox(), amount, world.terrain,
            { t: Terrain -> t.prototype.attributes.passable },
            amount, cardinality)
    WorldFns.movePlayer(world, moveBy)
}

fun changeOrientation(mouseClick: Point2): WorldMutation = { world: World ->
    val angle =
            Angle.create(
                    Point2(
                            mouseClick.x - world.view.getWindowDims().width / 2.0,
                            world.view.getWindowDims().height / 2.0 - mouseClick.y))
    world.worldObjects.player.rotation = angle
}