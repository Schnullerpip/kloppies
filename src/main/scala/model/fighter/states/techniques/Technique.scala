package main.scala.model.fighter.states.techniques

import main.scala.model.fighter.Fighter
import main.scala.util.{Observer, Observable}

/**
 * Created by julian on 14.02.16.
 * Fighters can perform techniques, which are represented by this abstract class
 * Every technique can be called by the act methode
 */
abstract class Technique(val observer:Observer) extends Observable{
  val name:String
  val manaUse:Int
  /**
   * @param caster is the calling fighter, this is given the technique so it can
   * infuse fighters attributes to technique
   * */
  def act(caster:Fighter)
}
