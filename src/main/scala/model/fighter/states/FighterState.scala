package main.scala.model.fighter.states

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.midair.Jumping
import main.scala.model.fighter.states.techniques.{Effect, Summoning, UsingTechnique, Technique}
import main.scala.model.intention.{Harmless, Harmful}
import main.scala.model.states.State

/**
 * Created by julian on 14.02.16.
 * the Superclass for all states a fighter can hold
 */
abstract class FighterState(f:Fighter) extends State(f){
  /**
   * actOnCollision is to be interpreted passively.
   * */
  override def actOnCollision(g: GameObject): Unit ={
    if(f.intention == Harmful) {
      if (g.vulnerable) {
        f.state.asInstanceOf[FighterState].inflictDamageTo(g)
      }
    }
  }

  def hit = {}
  def technique(technique: Technique) =ifMoveable{
    if(f.mana >= technique.manaUse)
      f.state = UsingTechnique(f, technique)
  }

  override def inflictDamageTo(gameObject: GameObject, amount:Int = f.strength) = {
        gameObject.state.asInstanceOf[FighterState].hurtBy(f)
        f.intention = Harmless
  }
  override def hurtBy(go:GameObject) = if(f.vulnerable){f.state = Hurt(f, go)()}

  override def stop = {stopUp; stopLeft}

  def landing = {}

  private def ifMoveable(b: => Unit) = if(f.moveable)b
  def moveUp = ifMoveable(f.y_velocity = -1 * f.speed)
  def moveDown = ifMoveable(f.y_velocity = f.speed)
  def moveLeft = ifMoveable(f.x_velocity = -1 * f.speed)
  def moveRight =ifMoveable(f.x_velocity = f.speed)
  def jump = ifMoveable(f.state = Jumping(f))

  def stopUp = f.y_velocity = 0
  def stopDown = stopUp
  def stopLeft = f.x_velocity = 0
  def stopRight = stopLeft

  def defend = f.state = Defending(f)
}
