package com.mygdx.game.functions

import com.mygdx.game.draw.Drawable
import com.mygdx.game.draw.PositionedDrawable
import com.mygdx.game.entities.*
import com.mygdx.game.util.Point2
import com.mygdx.game.util.Rect2


fun WorldPositionedDrawables(world: World, view: Rect2): List<PositionedDrawable> {
//    return TerrainPositionedDrawables(world.terrain, world.tileSize, view) +
 return           SceneObjectPositionedDrawables(world.worldObjects, view)
}

fun SceneObjectPositionedDrawables(worldObjects: WorldObjs, view: Rect2): List<PositionedDrawable> {
    println("Rendering player at " +  worldObjects.player.position.minus(view.lowerLeft()))
    return worldObjects.all().flatMap { wo: WorldObj ->
        if (wo.position.x >= view.lx && wo.position.x < view.ux() && wo.position.y >= view.ly && wo.position.y <= view.uy()) {
            val translatedPosition = wo.position.minus(view.lowerLeft())
            listOf(PositionedDrawable(wo.prototype.drawable, translatedPosition, wo.drawState))
        } else {
            emptyList()
        }
    } + PositionedDrawable(worldObjects.player.prototype.drawable, worldObjects.player.position.minus(view.lowerLeft()), worldObjects.player.drawState)
}

fun TerrainPositionedDrawables(terrains: Array<Array<Terrain>>, tileSize: Int, view: Rect2): List<PositionedDrawable> {
    return ((view.ly / tileSize)..(view.uy() / tileSize)).flatMap { c ->
        ((view.lx / tileSize)..(view.ux() / tileSize)).map { r ->
            val terrain = terrains[c][r]
            val p = Point2(r * tileSize - view.lx, c * tileSize - view.ly)
            PositionedDrawable(terrain.prototype.drawable, p, terrain.drawState)
        }
    }
}
