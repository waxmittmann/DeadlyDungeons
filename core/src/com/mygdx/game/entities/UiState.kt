package com.mygdx.game.entities


class UiState(var showInventory: Boolean) {
    companion object {
        fun create(): UiState = UiState(false)
    }
}
