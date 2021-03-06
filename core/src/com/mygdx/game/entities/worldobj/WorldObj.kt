package com.mygdx.game.entities.worldobj

import com.mygdx.game.draw.DrawState
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.AsRect
import com.mygdx.game.util.geometry.Point2
import com.mygdx.game.util.geometry.Rect2

//class WorldObj<S>(val prototype: WorldObjPrototype, val attributes: S, var position: Point2, var rotation: Angle, val drawState: DrawState) : AsRect {
class WorldObj<S>(val prototype: WorldObjPrototype, val attributes: S,
                  var position: Point2, var rotation: Angle,
                  val drawState: DrawState) : AsRect {
    override fun rect(): Rect2 =
            Rect2(position.x, position.y, prototype.size.width.toDouble(),
                    prototype.size.height.toDouble())

    override fun toString(): String {
        return "Position: $position, Dims: (${prototype.size.width}, ${prototype.size.height}), Rotation: $rotation"
    }
}

