package com.mygdx.game.input

import com.mygdx.game.entities.World
import com.mygdx.game.entities.terrain.Terrain
import com.mygdx.game.util.Direction
import com.mygdx.game.util.FullDirection
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2

typealias Action = (World) -> Unit

fun moveBy(amount: Int, cardinality: Direction): Action = { world ->
    val moveBy =
            movePlayer(world.worldObjects.player.rect(), amount, world.terrain,
                    { t: Terrain -> t.prototype.attributes.passable }, amount,
                    cardinality)
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

    val UP: (World) -> Unit = { world ->
        moveBy(amount, Direction.UP)(world)
        world.changePlayerOrientation(FullDirection.NORTH)
    }

    val DOWN: (World) -> Unit = { world ->
        moveBy(amount, Direction.DOWN)(world)
        world.changePlayerOrientation(FullDirection.SOUTH)
    }

    val LEFT: (World) -> Unit = { world ->
        moveBy(amount, Direction.LEFT)(world)
        world.changePlayerOrientation(FullDirection.WEST)
    }

    val RIGHT: (World) -> Unit = { world ->
        moveBy(amount, Direction.RIGHT)(world)
        world.changePlayerOrientation(FullDirection.EAST)
    }
}

val AttackAction: Action = { world ->
    if (world.worldObjects.player.attributes.lastShot + 200 < world.timeNow) {
        world.worldObjects.player.attributes.lastShot = world.timeNow
        world.addPlayerBullet()
    }
}

fun ChangeOrientation(mouseClick: Point2): Action = { world: World ->
    val angle = Angle.create(
            Point2(mouseClick.x - world.view.getWindowDims().width / 2.0,
                    world.view.getWindowDims().height / 2.0 - mouseClick.y))
    world.worldObjects.player.rotation = angle
}
