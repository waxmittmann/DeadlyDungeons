package com.mygdx.game.actions

fun toggleInventory(): Mutation = { it.ui.showInventory = !it.ui.showInventory }
