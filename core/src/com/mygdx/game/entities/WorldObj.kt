package com.mygdx.game.entities

import com.mygdx.game.draw.DrawState
import com.mygdx.game.util.Angle
import com.mygdx.game.util.Point2
import com.mygdx.game.util.Rect2

class WorldObj<S>(val prototype: WorldObjPrototype, val attributes: S, var position: Point2,  var rotation: Angle, val drawState: DrawState) : AsRect {
    override fun rect(): Rect2 = Rect2(position.x, position.y, prototype.width, prototype.height)
}

