package com.mygdx.game.util

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.drawing.Drawable
import com.mygdx.game.drawing.DrawableTexture

fun fakeDrawable(): Drawable {
    val tex = Texture(1, 1, Pixmap.Format.RGB888)
    return DrawableTexture(tex, 1f)
}