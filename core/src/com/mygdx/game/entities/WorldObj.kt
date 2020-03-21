package com.mygdx.game.entities

import com.mygdx.game.draw.DrawState
import com.mygdx.game.util.Angle
import com.mygdx.game.util.Point2
import com.mygdx.game.util.Rect2

class WorldObj(val prototype: WorldObjPrototype, val attributes: Attributes, var position: Point2, val drawState: DrawState, val orientation: Angle) : AsRect {
    override fun rect(): Rect2 = Rect2(position.x, position.y, prototype.width, prototype.height)
}

