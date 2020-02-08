package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import javafx.scene.Scene
import org.danilopianini.util.FlexibleQuadTree
import java.util.stream.Collectors
import kotlin.random.Random


class SomeGame : ApplicationAdapter() {
    private lateinit var bulletTexture: Texture
//    private lateinit var ot2: ObjectType
    private lateinit var playerDrawable: TextureDrawable
    private lateinit var avatarTexture: Texture
    lateinit var mountain: SceneObject
    lateinit var batch: SpriteBatch
    lateinit var img: Texture
    lateinit var yetiTexture: Texture
    lateinit var mountainTexture: Texture
//    lateinit var ot: ObjectType
    lateinit var yetiDrawable: TextureDrawable
    lateinit var mobs: Set<SceneObject>
    var bullets: Set<SceneObject> = setOf()
    lateinit var player: SceneObject
    private val inputHandler = InputHandler()
    private val collisionDetector = CollisionDetector()

    lateinit var bulletAtlas: TextureAtlas

    private lateinit var bulletSprite: Sprite;

    private val quadTree = FlexibleQuadTree<SceneObject>()

    private var lastSpawn: Long = -1

    private lateinit var bulletAnimation: Animation<TextureRegion>;

    override fun create() {
        batch = SpriteBatch()
        img = Texture("badlogic.jpg")
        yetiTexture = Texture("PixelArt.png")
        mountainTexture = Texture("Mountain.jpg")
        avatarTexture = Texture("Avatar.jpeg")
        bulletTexture = Texture("Bullet.png")
//        ot = ObjectType(yetiTexture, 50, 50)
        yetiDrawable = TextureDrawable(yetiTexture, 50, 50)
//        ot2 = ObjectType(avatarTexture, 50, 50)
        playerDrawable = TextureDrawable(avatarTexture, 50, 50)

        mobs = setOf(SceneObject(yetiDrawable, 0, 0), SceneObject(yetiDrawable, 100, 100))
//        quadTree.insert(mobs[0], 0.0, 0.0)
//        quadTree.insert(mobs[1], 100.0, 100.0)

        bulletAtlas = TextureAtlas("BulletSheet.txt");
        bulletSprite = bulletAtlas.createSprite("Bullet2")

        player = SceneObject(playerDrawable, 0, 0)

        mountain = SceneObject(TextureDrawable(mountainTexture, 650, 300), 0, 200);


        bulletAtlas.regions.forEach { r -> println(r.name) }
//...

        println("REgion: " + bulletAtlas.findRegion("Bullet2_1"))

        //...
        bulletAnimation = Animation(0.033f, bulletAtlas.findRegions("Bullet2"), PlayMode.LOOP)

        println("Key Frames: " + bulletAnimation.keyFrames.size)

        Gdx.graphics.setWindowedMode(500, 500)

    }

    var stateTime: Float = 0.1f

    override fun render() {
        stateTime += Gdx.graphics.deltaTime;

        val curTime = System.nanoTime()
        if (mobs.size < 50 && curTime - lastSpawn > 1000000000) {
            mobs = mobs.plus(SceneObject(yetiDrawable, Random.nextInt(0, 450), Random.nextInt(0, 450)))
            lastSpawn = curTime
        }

        val action = inputHandler.handleInput(player)
        println(action)
        if (action == "fire") {
            println("done it")
//            val bullet = SceneObject(ObjectType(bulletTexture, 20, 20), player.xc, player.yc)
//            val bullet = SceneObject(ObjectType(bulletTexture, 250, 250), 150, 150)
            val bullet = SceneObject(TextureDrawable(bulletTexture, 250, 250), 150, 150)
//            val bullet = SceneObject(ObjectType(yetiTexture, 50, 50), 50, 50)
            println(bullet)
            bullets = bullets.plus(bullet)
            println(bullets.size)
        }

//        println("Collisions:")
//        for (collided in collisionDetector.check(player, mobs)) {
//            println(collided)
//        }

        val collisions = collisionDetector.check(player, mobs)
        mobs = mobs.stream().filter { o -> !collisions.contains(o) }.collect(Collectors.toSet())

        Gdx.gl.glClearColor(1.0f, 1.0f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val drawer = ObjectDrawer()
        batch.begin()
        drawer.draw(batch, stateTime, mountain)
        drawer.draw(batch, stateTime, mobs)
        drawer.draw(batch, stateTime, player)
        drawer.draw(batch, stateTime, bullets)
//        drawer.draw(batch, stateTime, bulletSprite)
        drawer.draw(batch, stateTime, SceneObject(AnimationDrawable(bulletAnimation, 0.0f, 40, 40), 20, 20))
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }
}

fun main() {
    println("Hello World!")
    SomeGame().create()
    println("After")
}

