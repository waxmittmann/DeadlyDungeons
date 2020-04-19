package com.mygdx.game.entities.worldobj

import arrow.core.k
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Box2D
import com.badlogic.gdx.physics.box2d.World
import com.mygdx.game.draw.Drawable
import com.mygdx.game.util.geometry.Angle
import com.mygdx.game.util.geometry.Dims2
import com.mygdx.game.util.geometry.Rect2
import com.mygdx.game.util.geometry.Vec2


interface HierarchicalPrototype

class WorldObjPrototype(val name: String, val drawable: Drawable,
                        val size: Dims2) //: HierarchicalPrototype

//class PrototypeLeaf : HierarchicalPrototype()
//class PrototypeTransform(val rotation: Angle, val transform: Vec2,
//                         children: List<HierarchicalPrototype>) : HierarchicalPrototype



//class PrototypeLeaf(val drawable: Drawable, val size: Dims2) : HierarchicalPrototype
class PrototypeLeaf(val textureRegion: TextureRegion, val size: Dims2) : HierarchicalPrototype

class PrototypeRotate(val rotation: Angle,
                      val children: List<HierarchicalPrototype>) :
        HierarchicalPrototype

class PrototypeTranslate(val translation: Vec2,
                         val children: List<HierarchicalPrototype>) :
        HierarchicalPrototype


class TransformedPrototype(val textureRegion: TextureRegion, val body: Body, val
boundingRect: Rect2, val matrix: Matrix4)


//fun draw(sb: Batch, texture: TextureRegion, x: Float, y: Float, width: Float,
//         height: Float, originX: Float = 0.0f, originY: Float = 0.0f,
//         scaleX: Float = 1.0f, scaleY: Float = 1.0f, rotation: Float = 0.0f,
//         clockwise: Boolean = true) {
//    sb.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY,
//            90 - rotation, clockwise)
//}

fun draw(batch: Batch, hp: TransformedPrototype) {
    val prevMatrix = Matrix4(batch.transformMatrix)
    batch.transformMatrix = hp.matrix.mul(batch.transformMatrix)
    batch.draw(hp.textureRegion, 0f, 0f, hp.boundingRect.widthF, hp
            .boundingRect.heightF)
    batch.transformMatrix = prevMatrix
}

//https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_collision.html
//fun resolveHierarchicalPrototype(curMatrix: Matrix4, hp: HierarchicalPrototype):
//        List<TransformedPrototype> {
//
//    when (hp) {
//        is PrototypeTranslate -> {
//            val newMatrix = Matrix4(curMatrix).trn(hp.translation.xF, hp
//                    .translation
//                    .yF, 0f)
//            hp.children.k().flatMap {
//                resolveHierarchicalPrototype(newMatrix, hp).k()
//            }.toList()
//        }
//        is PrototypeRotate -> {
//            val newMatrix = Matrix4(curMatrix).rotate(0f, 0f, 1f, hp.rotation
//                    .radians.toFloat())
//            hp.children.k().flatMap {
//                resolveHierarchicalPrototype(newMatrix, hp).k()
//            }.toList()
//        }
//        is PrototypeLeaf -> {
//            val world: World = World(Vector2(0f, 0f), true)
//            val bodyDef: BodyDef = BodyDef()
//            bodyDef.active = true
////            bodyDef.position
//            bodyDef.type
//            world.createBody()
////            val body = Body(world)
//            listOf(TransformedPrototype(hp.textureRegion))
//        }
//    }
//
//}
//
