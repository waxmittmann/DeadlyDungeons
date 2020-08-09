package com.mygdx.game.textures

import com.mygdx.game.drawing.DrawableFns
import com.mygdx.game.drawing.SizedDrawable
import com.mygdx.game.util.geometry.Dims2

class SizedDrawables(textures: Textures) {
    val singlePixel = SizedDrawable(DrawableFns.create(textures.singlePixel, 1f), Dims2(1f, 1f))
}