package main.scala.model.fighter.states.midair

import main.scala.model.GameObject
import main.scala.model.ImageMatrix.FALLING
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{FighterState, HitTheGround}
import main.scala.model.states.{AnimateMe, MidAir}

/**
 * Created by julian on 21.02.16.
 */
case class Falling(f:Fighter, opponent:Option[GameObject] = None) extends FighterState(f) with MidAir with AnimateMe {
  f.z_velocity += {if(opponent isDefined) opponent.get.strength else 0}
  f.x_velocity += {if(opponent isDefined) opponent.get.strength * {if(opponent.get.looksLeft) -1 else 1} else 0}
  val height = f.z/2 + {if(opponent isDefined) opponent.get.strength else 0}
  f.vulnerable = false
  f.moveable = false
  f.images.set(FALLING)

  override def actOnCollision(go:GameObject) = {}
  override def landing = {f.state = HitTheGround(f, height)}
  override def hit     = {}
}
