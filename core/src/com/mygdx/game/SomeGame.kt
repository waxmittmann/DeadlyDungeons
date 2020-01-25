package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.danilopianini.util.FlexibleQuadTree
import java.util.stream.Collectors
import kotlin.random.Random

class SomeGame : ApplicationAdapter() {
    lateinit var mountain: SceneObject
    lateinit var batch: SpriteBatch
    lateinit var img: Texture
    lateinit var yetiTexture: Texture
    lateinit var mountainTexture: Texture
    lateinit var ot: ObjectType
    lateinit var mobs: Set<SceneObject>
    lateinit var player: SceneObject
    private val inputHandler = InputHandler()
    private val collisionDetector = CollisionDetector()

    private val quadTree = FlexibleQuadTree<SceneObject>()


    private var lastSpawn: Long = -1

    override fun create() {
        batch = SpriteBatch()
        img = Texture("badlogic.jpg")
        yetiTexture = Texture("PixelArt.png")
        mountainTexture = Texture("Mountain.jpg")
        ot = ObjectType(yetiTexture, 50, 50)
        mobs = setOf(SceneObject(ot, 0, 0), SceneObject(ot, 100, 100))
//        quadTree.insert(mobs[0], 0.0, 0.0)
//        quadTree.insert(mobs[1], 100.0, 100.0)

        player = SceneObject(ot, 0, 0)

        mountain = SceneObject(ObjectType(mountainTexture, 650, 300), 0, 200);

        Gdx.graphics.setWindowedMode(500, 500)

    }

    override fun render() {

        val curTime = System.nanoTime()
        if (mobs.size < 50 && curTime - lastSpawn > 1000000000) {
            mobs = mobs.plus(SceneObject(ot, Random.nextInt(0, 450), Random.nextInt(0, 450)))
            lastSpawn = curTime
        }

        inputHandler.handleInput(player)

//        println("Collisions:")
//        for (collided in collisionDetector.check(player, mobs)) {
//            println(collided)
//        }

        val collisions = collisionDetector.check(player, mobs)
        mobs = mobs.stream().filter { o -> !collisions.contains(o) }.collect(Collectors.toSet())

        Gdx.gl.glClearColor(1.0f, 1.0f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val drawer = ObjectDrawer()
        batch.begin()
        drawer.draw(batch, mountain)
        drawer.draw(batch, mobs)
        drawer.draw(batch, player)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }
}

fun main() {
    println("Hello World!")
    SomeGame().create()
    println("After")
}

