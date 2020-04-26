package com.mygdx.game.draw

/*
fun worldPositionedDrawables(world: World): List<PositionedDrawable> {
    return terrainPositionedDrawables(world.terrain, world.tileSize,
            world.view.getViewRect()) + sceneObjectPositionedDrawables(
            world.worldObjects, world.view.getViewRect())
}


fun sceneObjectPositionedDrawables(worldObjects: WorldObjs,
                                   view: Rect2): List<PositionedDrawable> {
    return worldObjects.all().flatMap { wo: WorldObj<out Any> ->
        if (wo.position.x >= view.lx && wo.position.x < view.ux() && wo.position.y >= view.ly && wo.position.y <= view.uy()) {
            val translatedPosition =
                    wo.position.minus(view.lowerLeft().asVector())
            listOf(PositionedDrawable(wo.prototype.drawable,
                    wo.prototype.size.width, wo.prototype.size.height,
                    translatedPosition.x.toFloat(),
                    translatedPosition.y.toFloat(),
                    wo.rotation.degrees.toFloat(), wo.drawState))
        } else {
            emptyList()
        }
    } //+ PositionedDrawable(worldObjects.player.prototype.drawable, worldObjects.player.position.minus(view.lowerLeft()), worldObjects.player.drawState)
}
*/

// TODO: Replace
/*
fun terrainPositionedDrawables(terrains: List<List<Terrain>>, tileSize: Int,
                               view: Rect2): List<PositionedDrawable> {
    return (max(floor(view.ly / tileSize) - 1, 0)..min(
            floor(view.uy() / tileSize) + 1, terrains.size - 1)).flatMap { c ->
        (max(floor(view.lx / tileSize) - 1, 0)..min(
                floor(view.ux() / tileSize) + 1,
                terrains[0].size - 1)).map { r ->

            val terrain = terrains[c][r]
            val p = Point2(r * tileSize - view.lx, c * tileSize - view.ly)
            PositionedDrawable(terrain.prototype.drawable, tileSize.toFloat(),
                    tileSize.toFloat(), p.x.toFloat(), p.y.toFloat(), 0.0f,
                    terrain.drawState)
        }
    }
}
*/
