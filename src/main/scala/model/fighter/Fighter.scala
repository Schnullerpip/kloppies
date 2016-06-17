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
 *
 * @param name is the name of the Fighter
 * @param fighter_strength will affect the damage punches produce or how far an item can be tossed
 * @param fighter_speed will affect ho fast a Fighter can be moved from A to B and also will affect how fast a Fighter
 *              can complete a summoning or an action like throwing an item
 * @param fighter_mana will affect how many techniques a fighter can use
 * @param imagesName the image matrix out of which the fighter takes its current image
 */
case class Fighter (var name:String,
                    imagesName:String,
                    var xp:Int = 0,
                    var fighter_hp:Int = 10,
                    var fighter_strength:Int = 10,
                    var fighter_speed:Int = 10,
                    var fighter_mana:Int = 10,
                    var fighter_mass:Int = 10,
                    techniques:HashMap[String, Technique] = HashMap(),
                    rows: Int = 32,
                    cols: Int = 7,
                    override var x:Int = 1,
                    override var y:Int = 1,
                    override var z:Int = 0
               ) extends GameObject with LivePoints with Speed with Mana{
  override var hp:Int = fighter_hp*100
  override var mana:Int = fighter_mana *10
  override var full_strength:Int = fighter_strength
  override var speed:Int = fighter_speed
  override var mass:Int = fighter_mass
  override var images = new ImageMatrix(imagesName, this, rows, cols)
  override var state:State = Normal(this)
  steppable = true

  def increase_full_hp: Unit ={
    fighter_hp +=1
    hp = fighter_hp *10
  }

  def increase_full_strength: Unit ={
    fighter_strength += 1
    fighter_strength = fighter_strength
  }

  def increase_full_speed{
    fighter_speed += 1
    speed = fighter_speed
  }

  def increase_full_mana: Unit ={
    fighter_mana += 1
    mana = fighter_mana*10
  }

  def increase_full_mass: Unit ={
    fighter_mass += 1
    mass = fighter_mass
  }

  override def toString() = name + "\t" + state

  override def image: BufferedImage = images.currentImage
  def newTechnique(t:Technique, combination:String) = techniques.put(combination, t)
}
