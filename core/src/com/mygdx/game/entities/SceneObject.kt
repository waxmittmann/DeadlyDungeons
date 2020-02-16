package com.mygdx.game.entities

import com.mygdx.game.draw.Drawable
import com.mygdx.game.collision.HasBoundingBox

//class SceneObject(val objectType: ObjectType, var xc: Int, var yc: Int) : HasBoundingBox {
class SceneObject(val drawable: Drawable, val scaleMult: Float, var xc: Int, var yc: Int) : HasBoundingBox {
    override fun getX(): Int {
        return xc
    }

    override fun getY(): Int {
        return yc
    }

    override fun getWidth(): Float {
        return drawable.widthToHeight * scaleMult
    }

    override fun getHeight(): Float {
        return (1f / drawable.widthToHeight) * scaleMult
    }
}
