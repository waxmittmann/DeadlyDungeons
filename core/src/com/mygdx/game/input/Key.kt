package com.mygdx.game.input

interface Key

enum class PressedSpecial : Key {
    MoveUp, MoveDown, MoveLeft, MoveRight, Attack, ToggleInventory
}

object UnknownKey : Key
