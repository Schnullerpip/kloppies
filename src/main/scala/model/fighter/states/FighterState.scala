package main.scala.model.fighter.states

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.midair.{Falling, Landing, Levitate}
import main.scala.model.fighter.states.techniques.{Technique, UsingTechnique}
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
      f.state.init
  }

  override def inflictDamageTo(gameObject: GameObject, amount:Int = f.fighter_strength) = {
    if(f.intention == Harmful) {
      gameObject.state.hurtBy(f)(amount)
      f.intention = Harmless
    }
  }
  override def hurtBy(g:GameObject)(amount:Int=g.strength):Unit =
    if(f.vulnerable){
      if(amount >= f.mass/2) {
        f.state = if (amount < f.mass * 2)
          Hurt(f, g)(amount)
        else
          Falling(f, Some(g))(Some(amount))
      } else {
        //if the opponents strength is not even above the fighters mass, it will damage him, but not make him tumble
        f.takeDamage(amount)
      }
    }

  override def stop = {stopUp; stopLeft}

  override def landing(go:GameObject) = f.state = Landing(f)

  private def ifMoveable(b: => Unit) = if(f.moveable)b
  def moveUp = ifMoveable(f.y_velocity = -1 * f.speed)
  def moveDown = ifMoveable(f.y_velocity = f.speed)
  def moveLeft = ifMoveable(f.x_velocity = -1 * f.speed)
  def moveRight =ifMoveable(f.x_velocity = f.speed)
  def jump = ifMoveable(f.state = Loading(f))

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
