package main.scala.model.fighter.states.midair

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.FighterState

/**
 * Created by julian on 19.02.16.
 */
class Landing(f:Fighter) extends FighterState(f){
  override def hit: Unit = {}
}
