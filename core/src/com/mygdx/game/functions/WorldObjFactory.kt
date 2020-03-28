package com.mygdx.game.functions

import com.mygdx.game.draw.DrawState
import com.mygdx.game.draw.Textures
import com.mygdx.game.entities.*
import com.mygdx.game.util.FullDirection
import com.mygdx.game.util.Point2

class WorldObjFactory(private val prototype: Prototypes) {

    //    class WorldObj(val prototype: WorldObjPrototype, val attributes: Attributes, var position: Point2, val drawState: DrawState) : AsRect {
    fun createBullet(position: Point2, orientation: FullDirection) {
        val bullet: WorldObj = WorldObj(prototype.bullet, Attributes(orientation), position, DrawState(0.0f))


    }

}