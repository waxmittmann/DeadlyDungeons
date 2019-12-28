package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class SomeGame : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var img: Texture
    lateinit var yetiTexture: Texture
    lateinit var ot: ObjectType
    lateinit var items: List<SceneObject>

    override fun create() {
        batch = SpriteBatch()
        img = Texture("badlogic.jpg")
        yetiTexture = Texture("PixelArt.png")
        ot = ObjectType(yetiTexture, 50, 50)
        items = listOf(SceneObject(ot, 0, 0), SceneObject(ot, 100, 100))
    }

    override fun render() {

        Gdx.gl.glClearColor(1f, 1f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val drawer = ObjectDrawer()
        batch.begin()
        drawer.draw(batch, items)
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

