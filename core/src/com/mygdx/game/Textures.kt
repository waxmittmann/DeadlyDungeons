package com.mygdx.game

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*

class Textures {
    var avatarTexture: Texture = Texture("Avatar.png")
    var yetiTexture: Texture = Texture("PixelArt.png")
    var mountainTexture: Texture = Texture("Mountain.jpg")
    var yetiDrawable = TextureDrawable(yetiTexture, 50, 50)
    var playerDrawable = TextureDrawable(avatarTexture, 50, 50)
    var bulletAtlas = TextureAtlas("BulletSheet.txt");
    val mountainDrawable = TextureDrawable(mountainTexture, 650, 300)
    var bulletAnimation: Animation<TextureRegion> = Animation(0.033f, bulletAtlas.findRegions("Bullet2"), Animation.PlayMode.LOOP)
    val bulletDrawable = AnimationDrawable(bulletAnimation, 0.0f, 250, 250)
}
