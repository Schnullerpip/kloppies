package main.scala.model.fighter.states

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.midair.{Falling, Jumping, Landing, Levitate}
import main.scala.model.fighter.states.techniques.{Effect, Summoning, Technique, UsingTechnique}
import main.scala.model.intention.{Harmful, Harmless}
import main.scala.model.states.{MidAir, State}
import main.scala.util.sound.SoundDistributor

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
      if (g.vulnerable && g.tangible && g.collidable) {
        SoundDistributor.play("small_punch")
        f.state.asInstanceOf[FighterState].inflictDamageTo(g)
      }
    }
    super.actOnCollision(g)
  }

  def hit = {}
  def technique(technique: Technique) =ifMoveable{
    if(f.mana >= technique.manaUse)
      f.state = UsingTechnique(f, technique)
  }

  override def inflictDamageTo(gameObject: GameObject, amount:Int = f.strength) = {
        if(f.intention == Harmful) {
          gameObject.state.hurtBy(f)
          f.intention = Harmless
        }
  }
  override def hurtBy(go:GameObject):Unit =
    if(f.vulnerable){
      f.state = if(go.strength < f.mass*2)
        Hurt(f, go)()
      else
        Falling(f, Some(go))
    }

  override def stop = {stopUp; stopLeft}

  override def landing =
    f.state = Landing(f)

  private def ifMoveable(b: => Unit) = if(f.moveable)b
  def moveUp = ifMoveable(f.y_velocity = -1 * f.speed)
  def moveDown = ifMoveable(f.y_velocity = f.speed)
  def moveLeft = ifMoveable(f.x_velocity = -1 * f.speed)
  def moveRight =ifMoveable(f.x_velocity = f.speed)
  def jump = ifMoveable(f.state = Jumping(f))

  def stopUp = ifMoveable(f.y_velocity = 0)
  def stopDown = ifMoveable(stopUp)
  def stopLeft = ifMoveable(f.x_velocity = 0)
  def stopRight = ifMoveable(stopLeft)

  override def levitate = f.state = Levitate(f)

  def defend = {
    ifMoveable{f.state = if(this.isInstanceOf[MidAir]) new Defending(f) with MidAir else Defending(f)}
  }

  override def toString() = this.getClass.getName
}
