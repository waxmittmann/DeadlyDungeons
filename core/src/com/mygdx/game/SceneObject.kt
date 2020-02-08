package com.mygdx.game

import com.badlogic.gdx.graphics.Texture
import com.brashmonkey.spriter.Timeline

//class SceneObject(val objectType: ObjectType, var xc: Int, var yc: Int) : HasBoundingBox {
class SceneObject(val drawable: Drawable, var xc: Int, var yc: Int) : HasBoundingBox {
    override fun getX(): Int {
        return xc
    }

    override fun getY(): Int {
        return yc
    }

    override fun getWidth(): Int {
        return drawable.width
    }

    override fun getHeight(): Int {
        return drawable.height
    }
}
