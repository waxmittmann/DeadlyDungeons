package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.g3d.Attribute
import com.mygdx.game.collision.CollisionDetector
import com.mygdx.game.draw.DrawState
import com.mygdx.game.draw.Drawable
import com.mygdx.game.draw.ObjectDrawer
import com.mygdx.game.draw.Textures
import com.mygdx.game.entities.*
import com.mygdx.game.functions.WorldPositionedDrawables
import com.mygdx.game.util.Angle
import com.mygdx.game.util.Point2
import com.mygdx.game.util.Rect2
import kotlin.random.Random

// Run via KotlinLauncher.
class Game : ApplicationAdapter() {
    private val inputHandler = InputHandler()
    private val collisionDetector = CollisionDetector()
    private lateinit var batch: SpriteBatch
    private lateinit var world: World
    private lateinit var textures: Textures

    override fun create() {
        batch = SpriteBatch()
        textures = Textures()

//        class TerrainPrototype(val name: String, val drawable: Drawable)
        val terrainProto = TerrainPrototype("terrain", textures.yetiDrawable)

        var terrain = arrayOf<Array<Terrain>>()
        for (i in 0..100) {
            var array = arrayOf<Terrain>()
            for (j in 0..100) {
                array += Terrain(terrainProto, DrawState(1f, 0f))
            }
            terrain += array
        }

        val player = WorldObj(WorldObjPrototype("player", textures.playerDrawable, 50, 50, Attributes()),
                Attributes(),
                Point2(0, 0), DrawState(1f, 0f), Angle.create(0))
        world = World(50, WorldObjs(player, emptyList()), terrain)
        Gdx.graphics.setWindowedMode(500, 500)
    }

    var stateTime: Float = 0.1f

    override fun render() {
        stateTime += Gdx.graphics.deltaTime;

        Gdx.gl.glClearColor(1.0f, 1.0f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val drawer = ObjectDrawer()
        batch.enableBlending();
        batch.begin()
        drawer.draw(batch, WorldPositionedDrawables(world, Rect2(0, 0, 500, 500)))
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }
}

