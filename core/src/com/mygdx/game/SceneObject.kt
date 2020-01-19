package com.mygdx.game

import com.badlogic.gdx.graphics.Texture
import com.brashmonkey.spriter.Timeline

class SceneObject(val objectType: ObjectType, var xc: Int, var yc: Int) : HasBoundingBox {
    override fun getX(): Int {
        return xc
    }

    override fun getY(): Int {
        return yc
    }

    override fun getWidth(): Int {
        return objectType.width
    }

    override fun getHeight(): Int {
        return objectType.height
    }
}
