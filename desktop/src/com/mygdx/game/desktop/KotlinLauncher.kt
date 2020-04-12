package com.mygdx.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.mygdx.game.Main
import com.mygdx.game.UiGame

fun main() {
    val config = LwjglApplicationConfiguration()
//    LwjglApplication(SomeGame(), config)
//    LwjglApplication(Game(), config)
//    LwjglApplication(UiGame(), config)
//    LwjglApplication(UiGame(), config)
    LwjglApplication(Main(), config)
}
