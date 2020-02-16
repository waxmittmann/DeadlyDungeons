package com.mygdx.game.draw

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.mygdx.game.draw.AnimationDrawable
import com.mygdx.game.draw.TextureDrawable
import com.mygdx.game.util.Dims2

class Textures {
    var avatarTexture: Texture = Texture("Avatar.png")
    var yetiTexture: Texture = Texture("PixelArt.png")
    var mountainTexture: Texture = Texture("Mountain.jpg")
    val grassTexture: Texture = Texture("grass.png")
    var yetiDrawable = TextureDrawable(yetiTexture, Dims2(1, 1))
    var playerDrawable = TextureDrawable(avatarTexture, Dims2(1, 1))
    var bulletAtlas = TextureAtlas("BulletSheet.txt");
    val mountainDrawable = TextureDrawable(mountainTexture, Dims2(65, 30))
    var bulletAnimation: Animation<TextureRegion> = Animation(0.033f, bulletAtlas.findRegions("Bullet2"), Animation.PlayMode.LOOP)
    val bulletDrawable = AnimationDrawable(bulletAnimation, 0.0f, Dims2(1, 1))
    val grassDrawable = TextureDrawable(grassTexture, Dims2(1, 1))
}
