package com.mygdx.game.entities

//import com.mygdx.game.entities.worldobj.WorldObj
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.entities.terrain.Terrain
import com.mygdx.game.entities.terrain.TerrainAttributes
import com.mygdx.game.entities.terrain.WeightedAllocator
import com.mygdx.game.entities.terrain.generateTerrain
import com.mygdx.game.entities.worldobj.WorldAabb
import com.mygdx.game.entities.worldobj.WorldObjFactory
import com.mygdx.game.entities.worldobj.WorldObject
import com.mygdx.game.entities.worldobj.WorldObjects
import com.mygdx.game.textures.Textures
import com.mygdx.game.util.EightDirection
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

fun createWorld(textures: Textures, playerPos: Point2,
                windowDims: Dims2): World {
    // Set up prototypes.
    val prototypes = Prototypes(textures)
    val worldObjFactory = WorldObjFactory(prototypes)

    // Create terrain.
    val randomTerrain = WeightedAllocator(
            listOf(Pair(80, prototypes.grass), Pair(80, prototypes.mud),
                    Pair(10, prototypes.rocks)))
    val terrain: List<MutableList<Terrain>> = generateTerrain(100, 100,
            prototypes.rocks) { _: Int, _: Int -> randomTerrain.allocate() }

    return World(playerPos, emptyList(), 0, worldObjFactory, 50, terrain,
            windowDims)
}

class World(playerPos: Point2, mobs: List<WorldObj<MobAttributes>>,
            var timeNow: Long, override val worldObjFactory: WorldObjFactory,
            override val tileSize: Int, override val terrain: List<List<Terrain>> = listOf(),
            windowDims: Dims2) : WorldLike {
    override val view: View
    override val worldObjects: WorldObjects

    val width: Int = terrain[0].size * tileSize
    val height: Int = terrain.size * tileSize

    init {
        val player: WorldObj<PlayerAttributes> =
                worldObjFactory.player(playerPos)
        view = View(player, windowDims)
        worldObjects = WorldObjects(player, mobs, emptyList())
    }
}

interface WorldObjectsSource {
    val worldObjects: WorldObjects
}

interface ViewSource {
    val view: View
}

interface TerrainSource {
    val terrain: List<List<Terrain>>
}

interface TileSizeSource {
    val tileSize: Int
}

interface WorldObjFactorySource {
    val worldObjFactory: WorldObjFactory
}

interface WorldLike : WorldObjectsSource, ViewSource, TerrainSource,
        TileSizeSource, WorldObjFactorySource

//object WorldObjectsSource: Source<WorldObject<out Any>> =Source<WorldObject<out Any>>() {}


//interface WorldObjectsSource {
//    fun worldObjects(): List<WorldObject<out Any>>
//}
//
//interface WorldObjectsSource {
//    fun worldObjects(): List<WorldObject<out Any>>
//}

//class WorldFns(val world: World) {
object WorldFns {



    // Does not check validity of move
    fun <S> movePlayer(src: S,
                       moveBy: Vec2): Unit where S : WorldObjectsSource, S : ViewSource {
        println("Moving player: ${src.worldObjects.player}")
        src.worldObjects.player.position =
                src.worldObjects.player.position.plus(moveBy)
        println("Moved player: ${src.worldObjects.player}")
        src.view.updateCamera()
//        view.plus(moveBy)
    }

    fun <S> changePlayerOrientation(src: S, direction: EightDirection) where
            S : WorldObjectsSource {
        src.worldObjects.player.attributes.orientation = direction
    }

    fun <S> addPlayerBullet(src: S) where
            S : WorldObjectsSource, S : WorldObjFactorySource {
        val midpoint = src.worldObjects.player.rect().midpoint()
        src.worldObjects.addProjectile(
                src.worldObjFactory.createBullet(midpoint,
                        src.worldObjects.player.rotation))
    }

//    fun setTime(time: Long) {
//        timeNow = time
//    }

    fun <S> updateWindowSize(src: S, newDims: Dims2) where S : ViewSource {
        src.view.setWindowDims(newDims)
    }

    fun <S> terrainWorldObjects(
            src: S): List<WorldObject<TerrainAttributes>> where S : TerrainSource, S : TileSizeSource, S : ViewSource =
            terrainPositionedDrawables(src.terrain, src.tileSize,
                    src.view.getViewRect())

    private fun terrainPositionedDrawables(terrains: List<List<Terrain>>,
                                           tileSize: Int,
                                           view: Rect2): List<WorldAabb<TerrainAttributes>> {
        val fromCol = max(floor(view.ly / tileSize).toInt() - 1, 0)
        val toCol =
                min(floor(view.uy() / tileSize).toInt() + 1, terrains.size - 1)

        val fromRow = max(floor(view.lx / tileSize).toInt() - 1, 0)
        val toRow = min(floor(view.ux() / tileSize).toInt() + 1,
                terrains[0].size - 1)

        return (fromCol..toCol).flatMap { c ->
            (fromRow..toRow).map { r ->
                val terrain = terrains[c][r]

                val terrainMidpoint = Point2(r * tileSize.toDouble(),
                        c * tileSize.toDouble()).minus(
                        Vec2(tileSize / 2.0, tileSize / 2.0))

                val d = SizedDrawable(terrain.drawable,
                        Dims2(tileSize.toFloat(), tileSize.toFloat()))

                WorldAabb(d, TerrainAttributes(), terrainMidpoint)
            }
        }
    }
}
