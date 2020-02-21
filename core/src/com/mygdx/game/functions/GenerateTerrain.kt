package com.mygdx.game.functions

import com.mygdx.game.draw.DrawState
import com.mygdx.game.entities.Terrain
import com.mygdx.game.entities.TerrainPrototype


class RandomAllocator(private val selection: List<TerrainPrototype>) {
    fun allocate(r: Int, c: Int): TerrainPrototype {
        return selection.random()
    }
}

fun generateTerrain(rowsNr: Int, colsNr: Int, statelessAllocator: (Int, Int) -> TerrainPrototype): Array<Array<Terrain>> {
    return (0..colsNr).map { col ->
        (0..rowsNr).map { row ->
            Terrain(statelessAllocator(row, col), DrawState(0f, 1.0f))
        }.toTypedArray()
    }.toTypedArray()
}
