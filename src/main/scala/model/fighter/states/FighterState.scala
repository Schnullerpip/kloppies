package main.scala.model.fighter.states

import java.awt.image.BufferedImage

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.intention.Harmful
import main.scala.model.states.{Harmful, State}

/**
 * Created by julian on 14.02.16.
 * the Superclass for all states a fighter can hold
 */
abstract class FighterState(f:Fighter) extends State(f){
  override def currentImage:BufferedImage = f.image
  /**
   * actOnCollision is to be interpreted passively.
   * */
  override def actOnCollision(g: GameObject): Unit ={
    if(g.intention == Harmful){
      f.takeDamage(g)
    }
    g.state match {
      case s:Harmful => f.state = Hurt(f, g)
    }
  }

  def hit

  override def stop = {}

  def moveUp = f.y_velocity = -1 * f.speed
  def moveDown = f.y_velocity = f.speed
  def moveLeft = f.x_velocity = -1 * f.speed
  def moveRight =f.x_velocity = f.speed

  def defend = f.state = Defending(f)
  def jump = f.state = Jumping(f)
}
