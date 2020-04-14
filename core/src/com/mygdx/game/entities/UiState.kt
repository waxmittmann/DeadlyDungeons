package com.mygdx.game.entities


class UiState(showInventory: Boolean) {
    companion object {
        fun create(): UiState = UiState(false)
    }
}
