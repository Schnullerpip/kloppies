package main.scala.model.fighter

import java.awt.image.BufferedImage

import main.scala.model.attributes.{Mana, Speed, LivePoints}
import main.scala.model.fighter.states.Normal
import main.scala.model.fighter.states.techniques.Technique
import main.scala.model._
import main.scala.model.states.State

import scala.collection.mutable.HashMap

/**
 * Created by julian on 14.02.16.
 * A Fighter is the data representation of an object controlled by the Player
 * @param name is the name of the Fighter
 * @param full_strength will affect the damage punches produce or how far an item can be tossed
 * @param full_speed will affect ho fast a Fighter can be moved from A to B and also will affect how fast a Fighter
 *              can complete a summoning or an action like throwing an item
 * @param full_mana will affect how many techniques a fighter can use
 * @param imagesName the image matrix out of which the fighter takes its current image
 */
case class Fighter (var name:String,
               imagesName:String,
               var xp:Int = 0,
               var full_hp:Int = 10,
               var full_strength:Int = 10,
               var full_speed:Int = 10,
               var full_mana:Int = 10,
               var full_mass:Int = 10,
               techniques:HashMap[String, Technique] = HashMap(),
               rows: Int = 30,
               cols: Int = 7,
               override var x:Int = 1,
               override var y:Int = 1,
               override var z:Int = 0
               ) extends GameObject with LivePoints with Speed with Mana{
  override var hp:Int = full_hp*10
  override var mana:Int = full_mana *10
  override var strength:Int = full_strength
  override var speed:Int = full_speed
  override var mass:Int = full_mass
  override var images = new ImageMatrix(imagesName, this, rows, cols)
  override var state:State = Normal(this)

  def increase_full_hp: Unit ={
    full_hp +=1
    hp = full_hp *10
  }

  def increase_full_strength: Unit ={
    full_strength += 1
    strength = full_strength
  }

  def increase_full_speed{
    full_speed += 1
    speed = full_speed
  }

  def increase_full_mana: Unit ={
    full_mana += 1
    mana = full_mana*10
  }

  def increase_full_mass: Unit ={
    full_mass += 1
    mass = full_mass
  }

  override def toString() = name + "\t" + state

  override def image: BufferedImage = images.currentImage
  def newTechnique(t:Technique, combination:String) = techniques.put(combination, t)
}
