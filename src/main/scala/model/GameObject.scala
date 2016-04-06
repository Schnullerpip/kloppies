package main.scala.model

import main.scala.model.attributes.{LivePoints, Strength}
import main.scala.model.intention.{Harmless, Intention}
import main.scala.model.items.magical.wind.Wind
import main.scala.model.states.{MidAir, State}
import main.scala.util.Observable

/**
 * Created by julian on 14.02.16.
 * A GameObject basically describes the minimum requirements for an entity to be "in" the Game
 * Anything, that is in the game needs to have data on position and size so there can be collision detection
 */
trait GameObject extends Size with Mass with Direction with Strength with LivePoints with Observable{
  var state:State
  var intention:Intention = Harmless
  var images:ImageMatrix
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
      if ((y >= o.y && y <= o.y + o.width/2) || (y <= o.y && y + width/2 >= o.y)) {
        if ((z >= o.z && z <= o.z + o.height) || (z <= o.z && z + height >= o.z)) {
          return true
        }
      }
    }
    false
  }
}
