package main.scala.model.fighter.techniques

import main.scala.model.fighter.Fighter

/**
 * Created by julian on 14.02.16.
 * Fighters can perform techniques, which are represented by this abstract class
 * Every technique can be called by the act methode
 * @param fighter is the calling fighter, this is given the technique so it can
 * infuse fighters attributes to technique
 */
abstract class Technique(val fighter: Fighter){
  def act
}
