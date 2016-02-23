package main.scala.model.fighter

import java.awt.image.BufferedImage

import main.scala.model.attributes.{Mana, Speed, LivePoints}
import main.scala.model.fighter.states.{FighterState, Normal}
import main.scala.model.fighter.states.techniques.{UsingTechnique, Technique}
import main.scala.model._
import main.scala.model.states.{MidAir, State}

import scala.collection.mutable.HashMap

/**
 * Created by julian on 14.02.16.
 * A Fighter is the data representation of an object controlled by the Player
 * @param name is the name of the Fighter
 * @param strength will affect the damage punches produce or how far an item can be tossed
 * @param speed will affect ho fast a Fighter can be moved from A to B and also will affect how fast a Fighter
 *              can complete a summoning or an action like throwing an item
 * @param mana will affect how many techniques a fighter can use
 * @param imagesName the image matrix out of which the fighter takes its current image
 */
case class Fighter (var name:String,
               imagesName:String,
               rows: Int = 30,
               cols: Int = 7,
               var xp:Int = 0,
               override var hp:Int = 100,
               override var strength:Int = 10,
               override var speed:Int = 10,
               override var mana:Int = 80,
               override var mass:Int = 1,
               override var x:Int = 1,
               override var y:Int = 1,
               override var z:Int = 0
               ) extends GameObject with LivePoints with Speed with Mana{
  override var images = new ImageMatrix(imagesName, this, rows, cols)
  override var state:State = Normal(this)
  val techniques:HashMap[String, Technique] = HashMap()

  override def image: BufferedImage = images.currentImage

  override def gravity_affect(pace:Int) = {
    state match {
      case m:MidAir =>
        if(gravity_affected)z_velocity -= pace
        /*TODO replace with check for collision with stage instance*/
        if(z <= 0) {
          z = 0
          z_velocity = 0
          state.asInstanceOf[FighterState].landing
        }
      case _ =>
    }
  }
}
