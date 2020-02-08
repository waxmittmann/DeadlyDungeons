package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import javafx.scene.Scene
import org.danilopianini.util.FlexibleQuadTree
import java.util.stream.Collectors
import kotlin.random.Random

class SomeGame : ApplicationAdapter() {
    lateinit var mountain: SceneObject
    lateinit var batch: SpriteBatch
    lateinit var mobs: Set<SceneObject>
    var bullets: Set<SceneObject> = setOf()
    lateinit var player: SceneObject

    private val inputHandler = InputHandler()
    private val collisionDetector = CollisionDetector()

    private var lastSpawn: Long = -1

    private lateinit var textures: Textures

    override fun create() {
        textures = Textures()
        batch = SpriteBatch()

        mobs = setOf(SceneObject(textures.yetiDrawable, 0, 0), SceneObject(textures.yetiDrawable, 100, 100))
        player = SceneObject(textures.playerDrawable, 0, 0)
        mountain = SceneObject(textures.mountainDrawable, 0, 200);

        Gdx.graphics.setWindowedMode(500, 500)
    }

    var stateTime: Float = 0.1f

    override fun render() {
        stateTime += Gdx.graphics.deltaTime;

        val curTime = System.nanoTime()
        if (mobs.size < 50 && curTime - lastSpawn > 1000000000) {
            mobs = mobs.plus(SceneObject(textures.yetiDrawable, Random.nextInt(0, 450), Random.nextInt(0, 450)))
            lastSpawn = curTime
        }

        val action = inputHandler.handleInput(player)
        println(action)
        if (action == "fire") {
            println("done it")
            val bullet = SceneObject(textures.bulletDrawable, 150, 150)
            println(bullet)
            bullets = bullets.plus(bullet)
            println(bullets.size)
        }

        val collisions = collisionDetector.check(player, mobs)
        mobs = mobs.stream().filter { o -> !collisions.contains(o) }.collect(Collectors.toSet())

        Gdx.gl.glClearColor(1.0f, 1.0f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val drawer = ObjectDrawer()
        batch.enableBlending();
        batch.begin()

        val toDraw: MutableList<SceneObject> = mutableListOf()
        toDraw += mountain
        toDraw += mobs
        toDraw += player
        toDraw += bullets
        toDraw += SceneObject(textures.bulletDrawable, 10, 10)

        drawer.draw(batch, Gdx.graphics.deltaTime, toDraw)

//        drawer.draw(batch, stateTime, mountain)
//        drawer.draw(batch, stateTime, mobs)
//        drawer.draw(batch, stateTime, player)
//        drawer.draw(batch, stateTime, bullets)
//        drawer.draw(batch, stateTime, SceneObject(textures.bulletDrawable, 10, 10))
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }
}

fun main() {
    println("Hello World!")
    SomeGame().create()
    println("After")
}

