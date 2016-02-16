package main.scala.model.fighter.states

import main.scala.model.fighter.Fighter
import main.scala.model.ImageMatrix.STANDING_HIT

/**
 * Created by julian on 16.02.16.
 */
case class Defending(f:Fighter) extends FighterState(f){
  override def hit = f.state = Aggressive(STANDING_HIT, f)
  override def moveUp =   {}
  override def moveDown = {}
  override def moveLeft = {}
  override def moveRight ={}

  override def stop = {
    f.state = Normal(f)
  }
}
