package main.scala.model

import main.scala.model.attributes.{LivePoints, Strength}
import main.scala.model.fighter.Fighter
import main.scala.model.intention.{Harmless, Intention}
import main.scala.model.items.magical.wind.MagicalWind
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
    /*val (o.width, o.height) = o match {
      case _:Fighter => (o.width/1.5, o.height/2)
      case _ => (o.width.toDouble, o.height)
    }
    val (this_width, this_height) = this match {
      case _:Fighter => (width/1.5, height/2)
      case _ => (width.toDouble, height)
    }*/
    if((x >= o.x && x <= o.x + o.width) || ( x <= o.x && x + width >= o.x)) {
      if ((y >= o.y && y <= o.y + o.width) || (y <= o.y && y + width >= o.y)) {
        if ((z >= o.z && z <= o.z + o.height) || (z <= o.z && z + height >= o.z)) {
          return true
        }
      }
    }
    false
  }
}
