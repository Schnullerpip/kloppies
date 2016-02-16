package main.scala.model.fighter

import java.awt.image.BufferedImage

import main.scala.model.ImageMatrix.ImageCoordinate
import main.scala.model.attributes.{Mana, Speed, LivePoints}
import main.scala.model.fighter.animations.Struggle
import main.scala.model.fighter.states.{FighterState, Normal}
import main.scala.model.fighter.techniques.Technique
import main.scala.model._
import main.scala.model.states.State

/**
 * Created by julian on 14.02.16.
 * A Fighter is the data representation of an object controlled by the Player
 * @param name is the name of the Fighter
 * @param strength will affect the damage punches produce or how far an item can be tossed
 * @param speed will affect ho fast a Fighter can be moved from A to B and also will affect how fast a Fighter
 *              can complete a summoning or an action like throwing an item
 * @param mana will affect how many techniques a fighter can use
 * @param techniques stores all the techniques a fighter can use
 * @param imagePath the image matrix out of which the fighter takes its current image
 */
case class Fighter (var name:String,
               imagePath:String,
               rows: Int = 17,
               cols: Int = 7,
               override var hp:Int = 100,
               override var strength:Int = 1,
               override var speed:Int = 1,
               override var mana:Int = 1,
               var techniques:Map[String, Technique] = Map(),
               override var mass:Int = 1,
               override var x:Int = 1,
               override var y:Int = 1,
               override var z:Int = 1
               ) extends GameObject with LivePoints with Speed with Mana{
  override var images = new ImageMatrix(imagePath, this, rows, cols)
  override var state:State = Normal(this)
  override def takeDamage(go:GameObject) = {
    hp -= go.strength
    Struggle(this, go)
  }
}
