package main.scala.model.fighter.states.midair

import main.scala.model.GameObject
import main.scala.model.ImageMatrix.LEVITATING
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.{UsingTechnique, Technique}
import main.scala.model.fighter.states.{Defending, Hurt, FighterState}
import main.scala.model.fighter.states.aggressive.LevitatingAttack
import main.scala.model.states.{AnimateMe, MidAir}

/**
 * Created by julian on 19.02.16.
 */
case class Levitate(f:Fighter) extends FighterState(f) with MidAir with AnimateMe{
  f.moveable = true
  f.images.set(LEVITATING)

  override def hit: Unit = f.state = LevitatingAttack(f)

  override def hurtBy(go:GameObject) = {
    if (f.vulnerable) {
      f.state = Falling(f, go)
    }
  }
  override def landing = {f.state = Landing(f)}
  override def technique(t:Technique) = f.state = new UsingTechnique(f, t) with MidAir
  override def moveUp = {}
  override def moveDown = {}
  override def moveLeft = super.moveLeft
  override def moveRight =super.moveRight
  override def jump = {}
}
