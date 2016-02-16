package main.scala.model.player

import main.scala.model.fighter.Fighter

/**
 * Created by julian on 14.02.16.
 * Representation ofa Player that is controlling a Fighter
 */
case class Player(var fighter: Fighter,var keySet:KeySet = KeySet())
