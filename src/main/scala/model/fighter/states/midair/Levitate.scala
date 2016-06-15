package main.scala.model.fighter.states.midair

import main.scala.model.GameObject
import main.scala.model.ImageMatrix.LEVITATING
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.{Technique, UsingTechnique}
import main.scala.model.fighter.states.{FighterState, Loading}
import main.scala.model.fighter.states.aggressive.LevitatingAttack
import main.scala.model.states.{AnimateMe, MidAir}

/**
 * Created by julian on 19.02.16.
 */
case class Levitate(f:Fighter) extends FighterState(f) with MidAir with AnimateMe{
  f.moveable = true
  f.images.set(LEVITATING)

  override def hit: Unit = f.state = LevitatingAttack(f)

  override def hurtBy(g:GameObject)(amount:Int = g.strength) = {
    if (f.vulnerable) {
      f.state = Falling(f, Some(g))(Some(amount))
    }
  }

  override def technique(t:Technique) = f.state = new UsingTechnique(f, t) with MidAir
  override def moveUp = {}
  override def moveDown = {}
  override def moveLeft = if(f.moveable)f.x_velocity = -1 * f.fighter_strength/2
  override def moveRight =if(f.moveable) f.x_velocity = f.fighter_strength/2
  override def jump = {if(f.moveable){
    f.state = new Loading(f) with MidAir
    f.state.init
  }}
}
