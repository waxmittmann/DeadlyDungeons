package com.mygdx.game.functions

import com.mygdx.game.draw.PositionedDrawable
import com.mygdx.game.entities.*
import com.mygdx.game.util.Point2
import com.mygdx.game.util.Rect2
import kotlin.math.max
import kotlin.math.min


//fun worldPositionedDrawables(world: World, view: Rect2): List<PositionedDrawable> {
fun worldPositionedDrawables(world: World): List<PositionedDrawable> {
    return terrainPositionedDrawables(world.terrain, world.tileSize, world.view) +
sceneObjectPositionedDrawables(world.worldObjects, world.view)
}

fun sceneObjectPositionedDrawables(worldObjects: WorldObjs, view: Rect2): List<PositionedDrawable> {
//    println("Rendering player at " +  worldObjects.player.position.minus(view.lowerLeft()))
    return worldObjects.all().flatMap { wo: WorldObj ->
        if (wo.position.x >= view.lx && wo.position.x < view.ux() && wo.position.y >= view.ly && wo.position.y <= view.uy()) {
            val translatedPosition = wo.position.minus(view.lowerLeft())
            listOf(PositionedDrawable(wo.prototype.drawable,
                    wo.prototype.width.toFloat(), wo.prototype.height.toFloat(),
                    translatedPosition.x.toFloat(), translatedPosition.y.toFloat(), wo.drawState))
        } else {
            emptyList()
        }
    } //+ PositionedDrawable(worldObjects.player.prototype.drawable, worldObjects.player.position.minus(view.lowerLeft()), worldObjects.player.drawState)
}

fun terrainPositionedDrawables(terrains: Array<Array<Terrain>>, tileSize: Int, view: Rect2): List<PositionedDrawable> {
//    println("Drawing to " + min((view.uy() / tileSize)+1, terrains.size-1) + ", " + min((view.ux() / tileSize)+1, terrains[0].size-1))
    return (max((view.ly / tileSize)-1, 0)..min((view.uy() / tileSize)+1, terrains.size-1)).flatMap { c ->
        (max((view.lx / tileSize)-1, 0)..min((view.ux() / tileSize)+1, terrains[0].size-1)).map { r ->
            val terrain = terrains[c][r]
            val p = Point2(r * tileSize - view.lx, c * tileSize - view.ly)
            PositionedDrawable(terrain.prototype.drawable, tileSize.toFloat(), tileSize.toFloat(), p.x.toFloat(), p.y.toFloat(), terrain.drawState)
        }
    }
}
