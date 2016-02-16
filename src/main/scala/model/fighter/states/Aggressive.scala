package main.scala.model.fighter.states

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.states.Harmful

/**
 * Created by julian on 15.02.16.
 */
case class Aggressive(row:Int, f:Fighter) extends FighterState(f) with Harmful{
  f.images.set(row)
  override def actOnCollision(go:GameObject): Unit = {
    go.state match {
      case _ =>
    }
  }
  override def hit = f.state = Aggressive(row, f)
  override def moveUp =   {}
  override def moveDown = {}
  override def moveLeft = {}
  override def moveRight ={}

  override def stop = {
    f.state = Normal(f)
  }
}
