package com.mygdx.game.ui.dragdrop

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload
import com.kotcrab.vis.ui.widget.VisImage
import com.mygdx.game.util.geometry.Rect2

/*

 */

class ActorFactory(val stage: Stage) {

    fun image(texture: TextureRegion, area: Rect2,
              addToStage: Boolean = true): Actor {
        val validTargetImage = VisImage(texture)
        validTargetImage.setBounds(area.lx.toFloat(), area.ly.toFloat(),
                area.width.toFloat(), area.height.toFloat())
        if (addToStage) stage.addActor(validTargetImage)
        return validTargetImage
    }

}


class DragDrop<S> {
    private val visDragDrop = DragAndDrop()

    inner class Source(val actor: Actor, val payload: S, val dragActor: Actor,
                       val validActor: Actor, val invalidActor: Actor)

    inner class Target(val actor: Actor, val dragFn: (S) -> (Boolean),
                       val resetFn: (S) -> Unit, val acceptedFn: (S) -> Unit)

    fun sameSource(actorFn: (Boolean) -> Actor, payload: S) =
            Source(actorFn(true), payload, actorFn(false), actorFn(false),
                    actorFn(false))


//    fun addSource(actor: Actor, payload: S, dragActor: Actor, validActor: Actor,
//                  invalidActor: Actor) = addSource(
//            Source(actor, payload, dragActor, validActor, invalidActor))

    fun addSource(source: Source) {
        println("adding source $source")

        visDragDrop.addSource(object : DragAndDrop.Source(source.actor) {
            override fun dragStart(event: InputEvent, x: Float, y: Float,
                                   pointer: Int): Payload {
                val payload = Payload()
                payload.setObject(source.payload)
                payload.dragActor = source.dragActor
                payload.validDragActor = source.validActor
                payload.invalidDragActor = source.invalidActor
                source.actor.isVisible = false
                return payload
            }

            override fun dragStop(event: InputEvent, x: Float, y: Float,
                                  pointer: Int, payload: Payload?,
                                  target: DragAndDrop.Target?) {
                if (target == null) {
                    source.actor.isVisible = true
                }
            }

        })
    }

//    fun addTarget(actor: Actor, dragFn: (S) -> (Boolean),
//                       resetFn: (S) -> Unit, acceptedFn: (S) -> Unit) {
//        return addTarget(Target(actor, dragFn, resetFn, acceptedFn))
//    }

    fun addTarget(target: Target) {
        visDragDrop.addTarget(object : DragAndDrop.Target(target.actor) {
            override fun drag(source: DragAndDrop.Source, payload: Payload,
                              x: Float, y: Float, pointer: Int): Boolean {
                return target.dragFn(payload.`object` as S)
            }

            override fun reset(source: DragAndDrop.Source, payload: Payload) {
                target.resetFn(payload.`object` as S)
            }

            override fun drop(source: DragAndDrop.Source, payload: Payload,
                              x: Float, y: Float, pointer: Int) {
                println("Accepted: " + payload.getObject() + " " + x + ", " + y)
                target.acceptedFn(payload.`object` as S)
            }
        })
    }

}