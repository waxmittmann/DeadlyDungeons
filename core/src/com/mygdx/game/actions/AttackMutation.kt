package com.mygdx.game.actions

val attackMutation: Mutation = fromWorld { world ->
    if (world.worldObjects.player.attributes.lastShot + 200 < world.timeNow) {
        world.worldObjects.player.attributes.lastShot = world.timeNow
        world.addPlayerBullet()
    }
}