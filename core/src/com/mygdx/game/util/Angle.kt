package com.mygdx.game.util


class Angle internal constructor(val degrees: Int) {
    companion object Factory {
        fun create(degrees: Int): Angle = Angle(0).rotate(degrees)
    }

    fun rotate(degreesBy: Int): Angle {
        var degreesNew = degrees + degreesBy
        if (degreesNew < 0)
            degreesNew = 360 - (-degreesNew % 360)
        else if (degreesNew > 360)
            degreesNew %= 360
        return Angle(degreesNew)
    }
}