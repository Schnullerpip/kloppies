package main.scala.model.fighter.states

import main.scala.model.fighter.Fighter
import main.scala.model.ImageMatrix.{RUNNING, RUNNING_HIT}
import main.scala.model.states.AnimateMe

case class Running(f:Fighter) extends FighterState(f) with AnimateMe{
  f.images.set(RUNNING)

  override def hit = f.state = Aggressive(RUNNING_HIT, f)

  override def moveUp =   { super.moveUp; f.images.set(RUNNING) }
  override def moveDown = { super.moveDown; f.images.set(RUNNING) }
  override def moveLeft = { super.moveLeft; if(!f.looksLeft){f.looksLeft = true;f.images.set(RUNNING)}}
  override def moveRight ={ super.moveRight;if(f.looksLeft) {f.looksLeft = false; f.images.set(RUNNING)}}

  override def stop = {
    f.x_velocity = 0
    f.y_velocity = 0
    f.state = Normal(f)
  }
}
