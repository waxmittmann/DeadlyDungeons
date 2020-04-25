package com.mygdx.game.draw

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.util.geometry.Dims2

typealias Drawable = DrawableV2.Drawable
typealias TextureDrawable = DrawableV2.Drawable.DrawableTextureRegion
typealias AnimationDrawable = DrawableV2.Drawable.DrawableAnimation

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
            TextureDrawable(TextureRegion(Texture("rock.png")), 1.0f) //Dims2
//    (1f,
//                    1f))
    override val mudDrawable: Drawable =
            TextureDrawable(TextureRegion(Texture("mud.png")), 1.0f)
//    Dims2(1f,
//                    1f))
    override val yetiDrawable: Drawable =
            TextureDrawable(yetiTexture, 1.0f) //Dims2(1f, 1f))

    override val playerDrawable: Drawable =
            TextureDrawable(avatarTexture, 1.0f) //Dims2(1f, 1f))
//    override val playerDrawable = TextureDrawable(arrowTexture, Dims2(1f, 1f))
//    override val playerDrawable = TextureDrawable(arrowTexture, Dims2(50f, 50f))
//    override val playerDrawable = TextureDrawable(arrowTexture, Dims2(1f, 1f))

    //    override val playerDrawable = TextureDrawable(boxedKnightTexture, Dims2(1f, 1f))
//    override val playerDrawable = AnimationDrawable(knightAnimation, 0.0f, Dims2(1f, 1f))
    override val mountainDrawable =
            TextureDrawable(mountainTexture, 65f/30f) //Dims2(65f, 30f))
    override val bulletDrawable: Drawable =
            AnimationDrawable(bulletAnimation, 0.0f, 1f) //Dims2(1f, 1f))
    override val grassDrawable = TextureDrawable(grassTexture, 1f) //Dims2(1f,
//    1f))
//    override val arrowDrawable = TextureDrawable(arrowTexture, Dims2(1f, 1f))
}
