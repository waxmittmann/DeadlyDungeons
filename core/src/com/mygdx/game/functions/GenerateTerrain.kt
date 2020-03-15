package com.mygdx.game.functions

import com.mygdx.game.draw.DrawState
import com.mygdx.game.entities.Terrain
import com.mygdx.game.entities.TerrainPrototype


class ModulusAllocator(private val selection: List<TerrainPrototype>) {
    fun allocate(r: Int, c: Int): TerrainPrototype {
        return selection[(r + c) % selection.size]
    }
}

class RandomAllocator(private val selection: List<TerrainPrototype>) {
    fun allocate(r: Int, c: Int): TerrainPrototype {
        return selection.random()
    }
}

class WeightedAllocator(private val selection: List<Pair<Int, TerrainPrototype>>) {
    private val selectionList: List<TerrainPrototype> = {
        selection.flatMap { p ->
            (0..p.first).map {
                p.second
            }
        }
    }()

    fun allocate(): TerrainPrototype {
        return selectionList.random()
    }
}

fun generateTerrain(rowsNr: Int, colsNr: Int, surroundWithWalls: TerrainPrototype?,
                    statelessAllocator: (Int, Int) -> TerrainPrototype): MutableList<MutableList<Terrain>> {
    val terrainMap = (0 until colsNr).map { col ->
        (0 until rowsNr).map { row ->
            Terrain(statelessAllocator(row, col), DrawState(0f))
        }.toMutableList()
    }.toMutableList()

    surroundWithWalls?.let { terrain ->
        (0 until colsNr).map { col ->
            terrainMap[col][0] = Terrain(terrain, DrawState(0f))
            terrainMap[col][rowsNr-1] = Terrain(terrain, DrawState(0f))
        }
        (0 until rowsNr).map { row ->
            terrainMap[0][row] = Terrain(terrain, DrawState(0f))
            terrainMap[colsNr-1][row] = Terrain(terrain, DrawState(0f))
        }
    }

    return terrainMap
}
