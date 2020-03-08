package com.mygdx.game.input

interface Key

enum class PressedSpecial : Key {
    UP, DOWN, LEFT, RIGHT
}

class CharKey(char: Char) : Key

object UnknownKey : Key