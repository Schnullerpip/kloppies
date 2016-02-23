package main.scala.model.fighter.states.techniques

import main.scala.model.fighter.Fighter
import main.scala.util.{Observer, Observable}

/**
 * Created by julian on 14.02.16.
 * Fighters can perform techniques, which are represented by this abstract class
 * Every technique can be called by the act methode
 * @param caster is the calling fighter, this is given the technique so it can
 * infuse fighters attributes to technique
 */
abstract class Technique(caster:Fighter){
  val name:String
  val manaUse:Int
  def act
}
