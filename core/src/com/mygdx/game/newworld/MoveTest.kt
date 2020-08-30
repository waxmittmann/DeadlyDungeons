package com.mygdx.game.newworld

import arrow.core.extensions.option.foldable.get
import arrow.core.getOrElse
import arrow.core.or
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.headless.*
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.newworld.BBFunctions
import com.mygdx.game.newworld.PositionedDrawable
import com.mygdx.game.newworld.calcBoundingBox
import com.mygdx.game.newworld.getValue
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.MaybeHasDims2
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.hamcrest.Matchers.*

object MoveTest {
    private const val error: Double = 0.001

    @BeforeAll
    @JvmStatic
    fun setup() {
        // Set up headless application to avoid 'linker' errors.
        HeadlessApplication(object : ApplicationAdapter() {
            override fun create() {}
        }, HeadlessApplicationConfiguration())
    }

    @Test
    fun lowerLeftPointIsRight() {
        val ll = LowerLeft(Rect2(-10.0, -10.0, 10.0, 10.0))
        Assertions.assertEquals(ll.p, Point2(-10.0, -10.0))
    }

    fun makeTerrain(x: Int, y: Int): List<List<Terrain>> {
        return (0..y).map { yAt ->
            (0..x).map { xAt ->
                makeEmptyTerrain(TestTerrain("$xAt, $yAt"))
            }.toList()
        }
    }

    @Test
    fun lowerLeftBordersNone() {
        val ll = LowerLeft(Rect2(11.0, 11.0, 8.0, 8.0))
        val grid = Grid(makeTerrain(10, 10), 10)

        assertThat(ll.adjacentTerrain(grid), empty())
    }

    @Test
    fun lowerLeftBordersLowerLeft() {
        val ll = LowerLeft(Rect2(10.0, 10.0, 10.0, 10.0))
        val grid = Grid(makeTerrain(10, 10), 10)

        assertThat(ll.adjacentTerrain(grid),
                containsInAnyOrder(grid.terrain[0][0], grid.terrain[1][0], grid.terrain[0][1]))
    }

    @Test
    fun lowerLeftBordersLeft() {
        val ll = LowerLeft(Rect2(10.0, 5.0, 10.0, 10.0))
        val grid = Grid(makeTerrain(10, 10), 10)
        assertThat(ll.adjacentTerrain(grid), containsInAnyOrder(grid.getTile(0, 0), grid.getTile(0, 1)))
    }


    @Test
    fun lowerLeftOfLargeObjectBordersMany() {
        val ll = LowerLeft(Rect2(10.0, 10.0, 21.0, 21.0))
        val grid = Grid(makeTerrain(10, 10), 10)
        assertThat(ll.adjacentTerrain(grid),
                containsInAnyOrder(grid.getTile(0, 0),
                        grid.getTile(0, 1), grid.getTile(0, 2), grid.getTile(0, 3),
                        grid.getTile(1, 0), grid.getTile(2, 0), grid.getTile(3, 0)))
    }

}