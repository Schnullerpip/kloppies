package main.scala.model.fighter.states

import main.scala.model.fighter.Fighter
import main.scala.model.states.{AnimateMe, MidAir}
import main.scala.model.ImageMatrix.{JUMPING, JUMPING_HIT}

/**
 * Created by julian on 14.02.16.
 */
case class Jumping(f:Fighter) extends FighterState(f) with MidAir with AnimateMe{
  f.images.set(JUMPING)
  override def hit = f.state = Aggressive(JUMPING_HIT, f)

  override def moveUp =   {}
  override def moveDown = {}
  override def moveLeft = { super.moveLeft; f.images.set(JUMPING)}
  override def moveRight ={ super.moveRight; f.images.set(JUMPING)}
}
