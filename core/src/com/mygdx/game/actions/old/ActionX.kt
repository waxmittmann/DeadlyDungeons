package com.mygdx.game.actions.old

import com.mygdx.game.entities.World

class UiState()

interface GameState {
    val world: World?
    val ui: UiState
}

class StartedGameState(override val world: World,
                       override val ui: UiState) : GameState

class InitialGameState(override val ui: UiState) : GameState {
    override val world: World? = null
}

typealias ActionX = (GameState) -> Unit

//typealias VoidAction = (Unit) -> Unit
//typealias WorldAction = (World) -> Unit
//typealias UiAction = (Ui) -> Unit

/*




 */
