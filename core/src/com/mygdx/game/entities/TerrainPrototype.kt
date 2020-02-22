package com.mygdx.game.entities

import com.mygdx.game.draw.Drawable


class TerrainAttributes(val passable: Boolean = true)

class TerrainPrototype(val name: String, val drawable: Drawable, attributes: TerrainAttributes = TerrainAttributes())

