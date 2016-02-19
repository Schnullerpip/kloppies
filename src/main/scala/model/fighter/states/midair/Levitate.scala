package main.scala.model.fighter.states.midair

import main.scala.model.GameObject
import main.scala.model.ImageMatrix.LEVITATING
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.FighterState
import main.scala.model.fighter.states.aggressive.LevitatingAttack
import main.scala.model.map.Stage
import main.scala.model.states.{AnimateMe, MidAir}

/**
 * Created by julian on 19.02.16.
 */
case class Levitate(f:Fighter) extends FighterState(f) with MidAir with AnimateMe{
  f.images.set(LEVITATING)

  override def hit: Unit = f.state = LevitatingAttack(f)

  override def moveUp = {}
  override def moveDown = {}
  override def moveLeft = super.moveLeft
  override def moveRight =super.moveRight
  override def jump = {}

  override def actOnCollision(go:GameObject): Unit ={
    go match {
      case s:Stage =>
        f.z_velocity = 0
        f.moveTo(f.x, f.y, s.z)
    }
  }
}
