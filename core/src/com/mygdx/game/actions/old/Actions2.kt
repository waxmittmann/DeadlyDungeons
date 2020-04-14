package com.mygdx.game.actions.old

import com.badlogic.gdx.Screen
import com.badlogic.gdx.physics.box2d.World

interface Effect {}

interface GameEffect : Effect


class ShowMenu() : GameEffect
class CloseCurMenu() : GameEffect

class MutateState() : GameEffect

class AddWorldObject() : GameEffect
class RemoveWorldObject() : GameEffect

class ChangeScreen(val screen: Screen) : Effect


interface Handler {
    fun handle(effect: Effect): Unit
}

object HandleChangeScreen {

}


class Universe(val changeScreen: ChangeScreen, val world: World?)


fun processChangeScreen(effect: Effect) {

}


fun processEffect(effect: Effect) {

    val x = when (effect) {
        MutateState() -> ""
        ShowMenu() -> ""
        CloseCurMenu() -> ""
        else -> throw RuntimeException("")
    }

}
