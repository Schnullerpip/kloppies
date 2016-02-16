package main.scala.model.fighter.states

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.ImageMatrix.OUCH
import main.scala.model.states.Harmful

/**
 * Created by julian on 15.02.16.
 */
case class Hurt(f:Fighter, gameobject:GameObject) extends FighterState(f){
  f.takeDamage(gameobject)
  f.images.set(OUCH)
  override def actOnCollision(g: GameObject): Unit = {
    g.state match {
      case s: Harmful => f.state = Hurt(f, g)
    }
  }

  override def hit: Unit = {}
  override def moveUp =   {}
  override def moveDown = {}
  override def moveLeft = {}
  override def moveRight ={}
}
