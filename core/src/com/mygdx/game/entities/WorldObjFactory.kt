package com.mygdx.game.entities

import com.mygdx.game.draw.DrawState
import com.mygdx.game.util.Angle
import com.mygdx.game.util.FullDirection
import com.mygdx.game.util.Point2
import com.mygdx.game.util.Vec2
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin

class WorldObjFactory(private val prototype: Prototypes) {

//    fun createBullet(position: Point2, orientation: FullDirection): WorldObj<ProjectileAttributes> {
//        val mod = 9
//        val velocity = when (orientation) {
//            FullDirection.SOUTH -> Vec2(0, -1 * mod)
//            FullDirection.SOUTH_WEST -> Vec2(-1* mod, -1* mod)
//            FullDirection.WEST -> Vec2(-1*mod, 0)
//            FullDirection.NORTH_WEST -> Vec2(-1* mod, 1* mod)
//            FullDirection.NORTH -> Vec2(0, 1* mod)
//            FullDirection.NORTH_EAST -> Vec2(1* mod, 1* mod)
//            FullDirection.EAST -> Vec2(1* mod, 0)
//            FullDirection.SOUTH_EAST -> Vec2(1* mod, -1* mod)
//        }
//
//       return WorldObj(prototype.bullet, ProjectileAttributes(orientation, velocity), position, Angle(0), DrawState(0.0f))
//    }

    fun createBullet(position: Point2, angle: Angle): WorldObj<ProjectileAttributes> {
        val mod = 9

//        println("Angle here: " + )

//        val x = -sin(toRadians(angle.degrees.toDouble())) * mod
//        val y = cos(toRadians(angle.degrees.toDouble())) * mod

        println("Rotating by " + angle)

        val vec = Vec2(0, 1).rotate(angle)

        println("Vec: " + vec)

//        return WorldObj(prototype.bullet, ProjectileAttributes(FullDirection.WEST, Vec2(x.toInt(), y.toInt())), position, Angle(0), DrawState(0.0f))
        return WorldObj(prototype.bullet, ProjectileAttributes(FullDirection.WEST, vec), position, Angle(0), DrawState(0.0f))
    }

}
