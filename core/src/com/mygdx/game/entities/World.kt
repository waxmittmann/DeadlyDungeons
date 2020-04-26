package com.mygdx.game.entities

import com.mygdx.game.draw.DrawableV2
import com.mygdx.game.draw.Textures
import com.mygdx.game.entities.terrain.Terrain
import com.mygdx.game.entities.terrain.TerrainAttributes
import com.mygdx.game.entities.terrain.WeightedAllocator
import com.mygdx.game.entities.terrain.generateTerrain
import com.mygdx.game.entities.worldobj.*
//import com.mygdx.game.entities.worldobj.WorldObj
import com.mygdx.game.scenegraph.Leaf
import com.mygdx.game.scenegraph.SimpleDrawable
import com.mygdx.game.scenegraph.WorldDrawable
import com.mygdx.game.util.EightDirection
import com.mygdx.game.util.geometry.*
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

fun createWorld(textures: Textures, playerPos: Point2, windowDims: Dims2):
        World {
    // Set up prototypes.
    val prototypes = Prototypes(textures)
    val worldObjFactory = WorldObjFactory(prototypes)

    // Create terrain.
    val randomTerrain = WeightedAllocator(
            listOf(Pair(80, prototypes.grass), Pair(80, prototypes.mud),
                    Pair(10, prototypes.rocks)))
    val terrain: List<MutableList<Terrain>> = generateTerrain(100, 100,
            prototypes.rocks) { _: Int, _: Int -> randomTerrain.allocate() }

    return World(playerPos, emptyList(), 0, worldObjFactory, 50,
            terrain, windowDims)
}

class World(playerPos: Point2, mobs: List<WorldObj<MobAttributes>>,
            var timeNow: Long, private val worldObjFactory: WorldObjFactory,
            val tileSize: Int, val terrain: List<List<Terrain>> = listOf(),
            windowDims: Dims2) {
    val view: View
    val worldObjects: WorldObjs

    init {
        val player: WorldObj<PlayerAttributes> =
                worldObjFactory.player(playerPos)
        view = View(playerPos, windowDims)
//        view = View(playerPos, player.prototype.boundaryDims, Dims2(10000f,
//                1600f))
        worldObjects = WorldObjs(player, mobs, emptyList())
    }

    val width: Int = terrain[0].size * tileSize
    val height: Int = terrain.size * tileSize

    // Does not check validity of move
    fun movePlayer(moveBy: Vec2) {
        worldObjects.player.position = worldObjects.player.position.plus(moveBy)
        view.plus(moveBy)
    }

    fun changePlayerOrientation(direction: EightDirection) {
        worldObjects.player.attributes.orientation = direction
    }

    fun addPlayerBullet() {
        val midpoint = worldObjects.player.rect.midpoint()
        worldObjects.addProjectile(worldObjFactory.createBullet(midpoint,
                worldObjects.player.rotation))
    }

    fun setTime(time: Long) {
        timeNow = time
    }

    fun updateWindowSize(newDims: Dims2) {
        view.setWindowDims(newDims)
    }

//    fun terrainPositionedDrawables(terrains: List<List<Terrain>>, tileSize: Int, view: Rect2): List<DrawableV2.SizedDrawable> {
//    private fun terrainPositionedDrawables(terrains: List<List<Terrain>>, tileSize: Int, view: Rect2): List<WorldSceneNode<TerrainAttributes>> {
    private fun terrainPositionedDrawables(terrains: List<List<Terrain>>, tileSize: Int, view: Rect2):
        List<WorldAabb<TerrainAttributes>> {
        return (max(floor(view.ly / tileSize).toInt() - 1,
                0)..min(
                floor(view.uy() / tileSize).toInt() + 1, terrains.size - 1)).flatMap { c ->
            (max(floor(view.lx / tileSize).toInt() - 1, 0)..min(
                    floor(view.ux() / tileSize).toInt() + 1,
                    terrains[0].size - 1)).map { r ->

                val terrain = terrains[c][r]

                val p = Point2(r * tileSize - view.lx, c * tileSize - view.ly)
                val d = DrawableV2.SizedDrawable(terrain.drawable, Dims2(tileSize.toFloat(),
                        tileSize.toFloat()))
//                class WorldObjV2<S>(val prototype: SceneNode, val attributes: S,
//                                    var position: Point2, var rotation: Angle) : AsRect {
//                WorldSceneNode(Leaf(d), TerrainAttributes(), p)
                WorldAabb(d, TerrainAttributes(), p)

//                PositionedDrawable(terrain.prototype.drawable, tileSize.toFloat(),
//                        tileSize.toFloat(), p.x.toFloat(), p.y.toFloat(), 0.0f,
//                        terrain.drawState)
            }
        }
    }

//    fun terrainDrawable() = terrainPositionedDrawables(terrain, tileSize, view.getViewRect())
//
//    fun terrainDrawableAsLeaf(): List<LeafDrawable> =
    fun terrainWorldObjects(): List<WorldObject<TerrainAttributes>> =
            terrainPositionedDrawables(terrain, tileSize, view.getViewRect())

//    fun drawables(): List<SimpleDrawable> =
//            SimpleDrawable()

}

