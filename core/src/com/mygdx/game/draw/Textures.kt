package com.mygdx.game.draw

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

interface Textures {
    val rockDrawable: Drawable
    val mudDrawable: Drawable
    val yetiDrawable: Drawable
    val playerDrawable: Drawable
    val mountainDrawable: Drawable
    val bulletDrawable: Drawable
    val grassDrawable: Drawable

    val mountainTexture: TextureRegion
    val bagTexture: TextureRegion

    val debugCollection: TextureCollection
    val itemsCollection: TextureCollection
}

class DefaultTextures : Textures {
    // Animations
    private val bulletAtlas = TextureAtlas("BulletSheet.txt");
    private val bulletAnimation: Animation<TextureRegion> =
            Animation(0.033f, bulletAtlas.findRegions("Bullet2"),
                    Animation.PlayMode.LOOP)

    private val knightAtlas = TextureAtlas("KnightAnim.txt");
    private val knightAnimation: Animation<TextureRegion> =
            Animation(0.033f, knightAtlas.findRegions("Knight"),
                    Animation.PlayMode.LOOP)

    // Textures
    private val avatarTexture: TextureRegion =
            TextureRegion(Texture("Avatar.png"))
    private val yetiTexture: TextureRegion =
            TextureRegion(Texture("PixelArt.png"))
    override val mountainTexture: TextureRegion =
            TextureRegion(Texture("Mountain.jpg"))
    private val grassTexture: TextureRegion =
            TextureRegion(Texture("grass.png"))
    private val arrowTexture: TextureRegion =
            TextureRegion(Texture("Arrow.png"))
    private val boxedKnightTexture: TextureRegion =
            TextureRegion(Texture("BoxedKnight.png"))

    override val bagTexture: TextureRegion = TextureRegion(Texture("Bag.png"))

    // Texture collections
    override val debugCollection: TextureCollection =
            TextureCollection(Texture("DebugGrid25x25.png"), 5, 5)
    override val itemsCollection: TextureCollection =
            TextureCollection(Texture("Items25x25.png"), 5, 5)

    // Drawables
    override val rockDrawable: Drawable =
            DrawableFns.create(Texture("rock.png"), 1.0f) //Dims2
    override val mudDrawable: Drawable =
            DrawableFns.create(Texture("mud.png"), 1.0f)
    override val yetiDrawable: Drawable =
            DrawableFns.create(yetiTexture, 1.0f)
    override val playerDrawable: Drawable =
            DrawableFns.create(avatarTexture, 1.0f)
    override val mountainDrawable =
            DrawableFns.create(mountainTexture, 65f/30f)
    override val bulletDrawable: Drawable =
            DrawableAnimation(bulletAnimation, 0.0f, 1f)
    override val grassDrawable = DrawableFns.create(grassTexture, 1f)
}
