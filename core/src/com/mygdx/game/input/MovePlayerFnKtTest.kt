package com.mygdx.game.input

import com.mygdx.game.util.FourDirection
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class MovePlayerFnKtTest {

    @Test
    fun movePlayerMovesFullAmountWhenNotBlocked() {
        // Setup
        val initialPos = Rect2.create(0, 0, 50, 50)
        val terrain: List<List<Boolean>> = listOf(listOf(true), listOf(true))

        // Run
        val result =
                movePlayer(initialPos, 50, terrain, { b: Boolean -> b }, 50,
                        FourDirection.UP)

        // Check
        Assertions.assertEquals(Vec2.create(0, 50), result)
    }

    @Test
    fun movePlayerMovesUntilBlockingTerrainWhenOneDistantVerticallyUp() {
        // Setup
        val initialPos = Rect2.create(0, 25, 50, 50)
        val terrain: List<List<Boolean>> =
                listOf(listOf(true), listOf(true), listOf(false))

        // Run
        val result =
                movePlayer(initialPos, 50, terrain, { b: Boolean -> b }, 100,
                        FourDirection.UP)

        // Check
        Assertions.assertEquals(Vec2.create(0, 25), result)
    }

    @Test
    fun movePlayerMovesUntilBlockingTerrainWhenOneDistantVerticallyDown() {
        // Setup
        val initialPos = Rect2.create(0, 125, 50, 50)
        val terrain: List<List<Boolean>> =
                listOf(listOf(false), listOf(true), listOf(true))

        // Run
        val result =
                movePlayer(initialPos, 50, terrain, { b: Boolean -> b }, 100,
                        FourDirection.DOWN)

        // Check
        Assertions.assertEquals(Vec2.create(0, -75), result)
    }

    @Test
    fun movePlayerMovesUntilBlockingTerrainWhenOneDistantHorizontallyFromZero() {
        // Setup
        val initialPos = Rect2.create(0, 0, 50, 50)
        val terrain: List<List<Boolean>> = listOf(listOf(true, true, false))

        // Run
        val result =
                movePlayer(initialPos, 50, terrain, { b: Boolean -> b }, 100,
                        FourDirection.RIGHT)

        // Check
        Assertions.assertEquals(Vec2.create(50, 0), result)
    }

    @Test
    fun movePlayerMovesUntilBlockingTerrainWhenOneDistantHorizontally() {
        // Setup
        val initialPos = Rect2.create(25, 0, 50, 50)
        val terrain: List<List<Boolean>> = listOf(listOf(true, true, false))

        // Run
        val result =
                movePlayer(initialPos, 50, terrain, { b: Boolean -> b }, 100,
                        FourDirection.RIGHT)

        // Check
        Assertions.assertEquals(Vec2.create(25, 0), result)
    }

    @Test
    fun movePlayerDoesntMoveIfBlocked() {
        // Setup
        val initialPos = Rect2.create(0, 25, 50, 50)
        val terrain: List<List<Boolean>> =
                listOf(listOf(false), listOf(true)).reversed()

        // Run
        val result =
                movePlayer(initialPos, 50, terrain, { b: Boolean -> b }, 50,
                        FourDirection.UP)

        // Check
        Assertions.assertEquals(Vec2.create(0, 0), result)
    }
}