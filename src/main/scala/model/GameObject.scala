package main.scala.model

import main.scala.model.attributes.{LivePoints, Strength}
import main.scala.model.intention.{Harmless, Intention}
import main.scala.model.states.{MidAir, State}
import main.scala.util.Observable

/**
 * Created by julian on 14.02.16.
 * A GameObject basically describes the minimum requirements for an entity to be "in" the Game
 * Anything, that is in the game needs to have data on position and size so there can be collision detection
 */
trait GameObject extends Mass with Direction with Strength with LivePoints with Observable{
  var intention:Intention = Harmless
  var images:ImageMatrix
  var state:State
  var collidable = true
  var tangible = true

  def image = images.currentImage

  override def gravity_affect(pace:Int = 1) = state match {
    case m:MidAir =>
      super.gravity_affect(pace)
    case _ =>
  }

  def colliding(o:GameObject):Boolean = {
    if((x >= o.x && x <= o.x + o.width) || ( x <= o.x && x + width >= o.x)) {
      if ((y >= o.y && y <= o.y + o.length) || (y <= o.y && y + length >= o.y)) {
        if ((z >= o.z && z <= o.z + o.height) || (z <= o.z && z + height >= o.z)) {
          return true
        }
      }
    }
    false
  }

  def collidingX(go:GameObject)(f: => Boolean = true):Boolean =
    if((x >= go.x && x <= go.x + go.width*0.75) || ( x <= go.x && x + width*0.75 >= go.x)) {
      f
    }else false

  def collidingY(go:GameObject)(f: => Boolean = true):Boolean =
    if((y >= go.y && y <= go.y + go.length) || ( y <= go.y && y + length >= go.y)) {
      f
    }else false


}
