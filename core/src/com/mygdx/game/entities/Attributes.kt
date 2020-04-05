package com.mygdx.game.entities

import com.mygdx.game.util.FullDirection
import com.mygdx.game.util.geometry.Vec2

interface Attributes
interface HasOrientation { var orientation: FullDirection }
interface HasVelocity { var velocity: Vec2 }

class PlayerAttributes(override var orientation: FullDirection, var lastShot: Long) : Attributes, HasOrientation
class MobAttributes(override var orientation: FullDirection, override var velocity: Vec2) : Attributes, HasOrientation, HasVelocity
class ProjectileAttributes(override var orientation: FullDirection, override var velocity: Vec2) : Attributes, HasOrientation, HasVelocity



