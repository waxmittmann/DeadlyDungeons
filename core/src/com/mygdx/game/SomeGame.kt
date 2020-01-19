package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.danilopianini.util.FlexibleQuadTree

class SomeGame : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var img: Texture
    lateinit var yetiTexture: Texture
    lateinit var ot: ObjectType
    lateinit var mobs: List<SceneObject>
    lateinit var player: SceneObject
    private val inputHandler = InputHandler()
    private val collisionDetector = CollisionDetector()

    private val quadTree = FlexibleQuadTree<SceneObject>()

    override fun create() {
        batch = SpriteBatch()
        img = Texture("badlogic.jpg")
        yetiTexture = Texture("PixelArt.png")
        ot = ObjectType(yetiTexture, 50, 50)
        mobs = listOf(SceneObject(ot, 0, 0), SceneObject(ot, 100, 100))
        quadTree.insert(mobs[0], 0.0, 0.0)
        quadTree.insert(mobs[1], 100.0, 100.0)

        player = SceneObject(ot, 0, 0)
    }

    override fun render() {
        inputHandler.handleInput(player)

        println("Collisions:")
        for (collided in collisionDetector.check(player, mobs)) {
            println(collided)
        }

//        quadTree.query()

        Gdx.gl.glClearColor(1f, 1f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val drawer = ObjectDrawer()
        batch.begin()
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

