package main.scala.dao

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.Technique

import scala.collection.mutable

/**
 * Created by julian on 23.02.16.
 */
case class StorableFighter(name:String, imagesName:String, xp:Int, hp:Int, strength:Int,
                           speed:Int, mana:Int, mass:Int, techniques:mutable.HashMap[String, Technique] ) {
  def toFighter:Fighter = {
    Fighter(name, imagesName, xp, hp, strength, speed, mana, mass, techniques)
  }

}
