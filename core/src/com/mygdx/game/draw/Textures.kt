package com.mygdx.game.draw

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.mygdx.game.draw.AnimationDrawable
import com.mygdx.game.draw.TextureDrawable
import com.mygdx.game.util.Dims2

interface Textures {
    val rockDrawable: Drawable
    val mudDrawable: Drawable
    val yetiDrawable: Drawable
    val playerDrawable: Drawable
    val mountainDrawable: Drawable
    val bulletDrawable: Drawable
    val grassDrawable: Drawable
}

class DefaultTextures : Textures {
    // Animations
    private val bulletAtlas = TextureAtlas("BulletSheet.txt");
    private val bulletAnimation: Animation<TextureRegion> = Animation(0.033f, bulletAtlas.findRegions("Bullet2"), Animation.PlayMode.LOOP)

    // Textures
    private val avatarTexture: Texture = Texture("Avatar.png")
    private val yetiTexture: Texture = Texture("PixelArt.png")
    private val mountainTexture: Texture = Texture("Mountain.jpg")
    private val grassTexture: Texture = Texture("grass.png")

    // Drawables
    override val rockDrawable: Drawable = TextureDrawable(Texture("rock.png"), Dims2(1, 1))
    override val mudDrawable: Drawable = TextureDrawable(Texture("mud.png"), Dims2(1, 1))
    override val yetiDrawable: Drawable = TextureDrawable(yetiTexture, Dims2(1, 1))
    override val playerDrawable: Drawable = TextureDrawable(avatarTexture, Dims2(1, 1))
    override val mountainDrawable = TextureDrawable(mountainTexture, Dims2(65, 30))
    override val bulletDrawable: Drawable = AnimationDrawable(bulletAnimation, 0.0f, Dims2(1, 1))
    override val grassDrawable = TextureDrawable(grassTexture, Dims2(1, 1))
}
