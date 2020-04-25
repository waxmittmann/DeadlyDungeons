package com.mygdx.game.entities.terrain


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

class WeightedAllocator(
        private val selection: List<Pair<Int, TerrainPrototype>>) {
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

fun generateTerrain(rowsNr: Int, colsNr: Int,
                    surroundWithWalls: TerrainPrototype?,
                    statelessAllocator: (Int, Int) -> TerrainPrototype): MutableList<MutableList<Terrain>> {
    val terrainMap = (0 until colsNr).map { col ->
        (0 until rowsNr).map { row ->
            Terrain(statelessAllocator(row, col))
        }.toMutableList()
    }.toMutableList()

    surroundWithWalls?.let { terrain ->
        (0 until colsNr).map { col ->
            terrainMap[col][0] = Terrain(terrain)
            terrainMap[col][rowsNr - 1] = Terrain(terrain)
        }
        (0 until rowsNr).map { row ->
            terrainMap[0][row] = Terrain(terrain)
            terrainMap[colsNr - 1][row] = Terrain(terrain)
        }
    }

    return terrainMap
}
