package com.mygdx.game.input

import com.mygdx.game.entities.World
import com.mygdx.game.entities.terrain.Terrain
import com.mygdx.game.util.FourDirection
import com.mygdx.game.util.EightDirection
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Point2

typealias Action = (World) -> Unit

fun moveBy(amount: Int, cardinality: FourDirection): Action = { world ->
    val moveBy =
            movePlayer(world.worldObjects.player.rect(), amount, world.terrain,
                    { t: Terrain -> t.prototype.attributes.passable }, amount,
                    cardinality)
    world.movePlayer(moveBy)
}

class MoveActions(val amount: Int) {
    val LEFT_UP: (World) -> Unit = { world ->
        moveBy(amount, FourDirection.UP)(world)
        moveBy(amount, FourDirection.LEFT)(world)
        world.changePlayerOrientation(EightDirection.NORTH_WEST)
    }
    val RIGHT_UP: (World) -> Unit = { world ->
        moveBy(amount, FourDirection.UP)(world)
        moveBy(amount, FourDirection.RIGHT)(world)
        world.changePlayerOrientation(EightDirection.NORTH_EAST)
    }
    val LEFT_DOWN: (World) -> Unit = { world ->
        moveBy(amount, FourDirection.DOWN)(world)
        moveBy(amount, FourDirection.LEFT)(world)
        world.changePlayerOrientation(EightDirection.SOUTH_WEST)
    }
    val RIGHT_DOWN: (World) -> Unit = { world ->
        moveBy(amount, FourDirection.DOWN)(world)
        moveBy(amount, FourDirection.RIGHT)(world)
        world.changePlayerOrientation(EightDirection.SOUTH_EAST)
    }

    val UP: (World) -> Unit = { world ->
        moveBy(amount, FourDirection.UP)(world)
        world.changePlayerOrientation(EightDirection.NORTH)
    }

    val DOWN: (World) -> Unit = { world ->
        moveBy(amount, FourDirection.DOWN)(world)
        world.changePlayerOrientation(EightDirection.SOUTH)
    }

    val LEFT: (World) -> Unit = { world ->
        moveBy(amount, FourDirection.LEFT)(world)
        world.changePlayerOrientation(EightDirection.WEST)
    }

    val RIGHT: (World) -> Unit = { world ->
        moveBy(amount, FourDirection.RIGHT)(world)
        world.changePlayerOrientation(EightDirection.EAST)
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
