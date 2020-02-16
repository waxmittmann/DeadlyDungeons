package com.mygdx.game.draw

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.mygdx.game.draw.AnimationDrawable
import com.mygdx.game.draw.TextureDrawable

class Textures {
    var avatarTexture: Texture = Texture("Avatar.png")
    var yetiTexture: Texture = Texture("PixelArt.png")
    var mountainTexture: Texture = Texture("Mountain.jpg")
    var yetiDrawable = TextureDrawable(yetiTexture, 1f)
    var playerDrawable = TextureDrawable(avatarTexture, 1f)
    var bulletAtlas = TextureAtlas("BulletSheet.txt");
    val mountainDrawable = TextureDrawable(mountainTexture, 65f / 30f)
    var bulletAnimation: Animation<TextureRegion> = Animation(0.033f, bulletAtlas.findRegions("Bullet2"), Animation.PlayMode.LOOP)
    val bulletDrawable = AnimationDrawable(bulletAnimation, 0.0f, 1f)
}
