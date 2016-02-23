package main.scala.model.fighter.states.midair

import main.scala.model.GameObject
import main.scala.model.ImageMatrix.FALLING
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{FighterState, HitTheGround}
import main.scala.model.states.{AnimateMe, MidAir}

/**
 * Created by julian on 21.02.16.
 */
case class Falling(f:Fighter, opponent:GameObject) extends FighterState(f) with MidAir with AnimateMe {
  f.z_velocity += opponent.strength
  f.x_velocity += opponent.strength * {if(opponent.looksLeft) -1 else 1}
  val height = f.z + opponent.strength
  f.vulnerable = false
  f.moveable = false
  f.images.set(FALLING)

  override def actOnCollision(go:GameObject) = {}
  override def landing = {f.state = HitTheGround(f, height)}
  override def hit     = {}
}
