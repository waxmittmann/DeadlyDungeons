package com.mygdx.game

interface HasBoundingBox {
    fun getX(): Int
    fun getY(): Int
    fun getWidth(): Int
    fun getHeight(): Int

    fun collision(other: HasBoundingBox): Boolean {
        if (getX() > other.getX() + other.getWidth())
            return false
        if (other.getX() > getX() + getWidth())
            return false
        if (getY() > other.getY() + other.getHeight())
            return false
        if (other.getY() > getY() + getHeight())
            return false
        return true
    }
}
