package com.mygdx.game.entities

import com.mygdx.game.util.EightDirection
import com.mygdx.game.util.geometry.Vec2

interface Attributes
interface HasOrientation {
    var orientation: EightDirection
}

interface HasVelocity {
    var velocity: Vec2
}

class PlayerAttributes(override var orientation: EightDirection,
                       var lastShot: Long) : Attributes, HasOrientation

class MobAttributes(override var orientation: EightDirection,
                    override var velocity: Vec2) : Attributes, HasOrientation, HasVelocity

class ProjectileAttributes(override var orientation: EightDirection,
                           override var velocity: Vec2) : Attributes, HasOrientation, HasVelocity



