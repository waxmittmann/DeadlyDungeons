package com.mygdx.game.input

import com.mygdx.game.entities.Terrain
import com.mygdx.game.entities.World
import com.mygdx.game.util.Angle
import com.mygdx.game.util.Direction
import com.mygdx.game.util.FullDirection

typealias Action = (World) -> Unit

fun moveBy(amount: Int, cardinality: Direction): Action = { world ->
    val moveBy = movePlayer(world.worldObjects.player.rect(), amount, world.terrain,
            { t: Terrain -> t.prototype.attributes.passable }, amount, cardinality)
    world.movePlayer(moveBy)
}

class MoveActions(val amount: Int) {
    val LEFT_UP: (World) -> Unit = { world ->
        moveBy(amount, Direction.UP)(world)
        moveBy(amount, Direction.LEFT)(world)
        world.changePlayerOrientation(FullDirection.NORTH_WEST)
    }
    val RIGHT_UP: (World) -> Unit = { world ->
        moveBy(amount, Direction.UP)(world)
        moveBy(amount, Direction.RIGHT)(world)
        world.changePlayerOrientation(FullDirection.NORTH_EAST)
    }
    val LEFT_DOWN: (World) -> Unit = { world ->
        moveBy(amount, Direction.DOWN)(world)
        moveBy(amount, Direction.LEFT)(world)
        world.changePlayerOrientation(FullDirection.SOUTH_WEST)
    }
    val RIGHT_DOWN: (World) -> Unit = { world ->
        moveBy(amount, Direction.DOWN)(world)
        moveBy(amount, Direction.RIGHT)(world)
        world.changePlayerOrientation(FullDirection.SOUTH_EAST)
    }

    val UP: (World) -> Unit  = { world ->
        moveBy(amount, Direction.UP)(world)
        world.changePlayerOrientation(FullDirection.NORTH)
    }

    val DOWN: (World) -> Unit  = { world ->
        moveBy(amount, Direction.DOWN)(world)
        world.changePlayerOrientation(FullDirection.SOUTH)
    }

    val LEFT: (World) -> Unit  = { world ->
        moveBy(amount, Direction.LEFT)(world)
        world.changePlayerOrientation(FullDirection.WEST)
    }

    val RIGHT: (World) -> Unit = { world ->
        moveBy(amount, Direction.RIGHT)(world)
        world.changePlayerOrientation(FullDirection.EAST)
    }
}

val AttackAction: Action = { world ->
//    println("ATTACK!")
    if (world.worldObjects.player.attributes.lastShot + 200 < world.timeNow) {
        world.worldObjects.player.attributes.lastShot = world.timeNow
        world.addPlayerBullet()
    }
}

fun ChangeOrientation(angle: Angle): Action = { world ->
//    println("Setting angle to : " + angle)
    world.worldObjects.player.rotation = angle
}

