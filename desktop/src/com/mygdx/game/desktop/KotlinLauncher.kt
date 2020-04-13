package com.mygdx.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.mygdx.game.Main

fun main() {
    val config = LwjglApplicationConfiguration()
    LwjglApplication(Main(), config)
}
