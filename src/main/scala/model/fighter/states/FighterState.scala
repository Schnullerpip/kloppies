package main.scala.model.fighter.states

import java.awt.image.BufferedImage

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.midair.Jumping
import main.scala.model.intention.Harmful
import main.scala.model.states.{Harmful, State}

/**
 * Created by julian on 14.02.16.
 * the Superclass for all states a fighter can hold
 */
abstract class FighterState(f:Fighter) extends State(f){
  /**
   * actOnCollision is to be interpreted passively.
   * */
  override def actOnCollision(g: GameObject): Unit ={}

  def hit

  override def stop = {}

  def moveUp = f.y_velocity = -1 * f.speed
  def moveDown = f.y_velocity = f.speed
  def moveLeft = f.x_velocity = -1 * f.speed
  def moveRight =f.x_velocity = f.speed

  def stopUp = f.y_velocity = 0
  def stopDown = stopUp
  def stopLeft = f.x_velocity = 0
  def stopRight = stopLeft

  def defend = f.state = Defending(f)
  def jump = f.state = Jumping(f)
}
