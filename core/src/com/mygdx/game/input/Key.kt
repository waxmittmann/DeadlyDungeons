package com.mygdx.game.input

interface Key

enum class PressedSpecial : Key {
    UP, DOWN, LEFT, RIGHT, SPACE
}

class CharKey(char: Char) : Key

object UnknownKey : Key
