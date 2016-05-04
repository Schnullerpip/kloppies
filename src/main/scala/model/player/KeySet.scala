package main.scala.model.player

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{FighterState, Loading, Running}

/**
 * Created by julian on 14.02.16.
 * Representation of a Keymapping, a player uses to control a Fighter
 */
case class KeySet(up:Char = 'w', down:Char = 's', left:Char = 'a',
                  right:Char = 'd', attack:Char = 'f', defense:Char = 'q',
                  jump:Char = 'e'){
  var (up_set, up_last), (down_set, down_last), (left_set, left_last), (right_set, right_last),
      (attack_set, attack_last), (defense_set, defense_last), (jump_set, jump_last) = (false, 0l)
  private val kBuffer = new KeyCombination()

  def released(key: Char, f: Fighter) = {
    val fighter_state = f.state.asInstanceOf[FighterState]
    key match {
      case `up` => up_set = false;        if(down_set)fighter_state.moveDown    else fighter_state.stopUp
      case `down` => down_set = false;    if(up_set)fighter_state.moveUp        else fighter_state.stopDown
      case `left` => left_set = false;    if(right_set)fighter_state.moveRight  else fighter_state.stopLeft
      case `right` => right_set = false;  if(left_set)fighter_state.moveLeft    else fighter_state.stopRight
      case `attack` => attack_set = false
      case `defense` => defense_set = false; fighter_state.stop
      case `jump` => jump_set = false; if(fighter_state.isInstanceOf[Loading]) fighter_state.stop
      case _ =>
    }
  }

  def pressed(key:Char, f:Fighter) = {
    val fighter_state = f.state.asInstanceOf[FighterState]
    key match {
      case `up` => up_set = true; checkKeyBuffer(key, f, fighter_state.moveUp)
      case `down` => down_set = true; checkKeyBuffer(key, f, fighter_state.moveDown)
      case `left` => left_set = true; checkKeyBuffer(key, f, fighter_state.moveLeft)
      case `right` => right_set = true; checkKeyBuffer(key, f, fighter_state.moveRight)
      case `attack` => attack_set = true; checkKeyBuffer(key, f, fighter_state.hit)
      case `defense` => if (!defense_set) checkKeyBuffer(key, f, {fighter_state.defend; defense_set = true})
      case `jump` => jump_set = true; checkKeyBuffer(key, f, fighter_state.jump)
      case _ =>
    }
  }

  private def checkKeyBuffer(key:Char, f:Fighter, Else: => Unit) = {
    val fighter_state = f.state.asInstanceOf[FighterState]
    val combination = kBuffer(key).replaceAll("right", "direction").replaceAll("left", "direction")
    var found = false
    var combi = ""
    f.techniques.keySet.foreach{
      case k if combination.contains(k.replaceAll("right", "direction").replaceAll("left", "direction")) =>
        found = true
        combi = k
      case _ =>
    }
    if(found) {
      fighter_state.technique(f.techniques(combi))
      kBuffer.queue.foreach{k =>
        kBuffer.queue dequeue()
        kBuffer.queue enqueue ""
      }
    }
    else
      Else
  }

  private class KeyCombination{
    var queue = scala.collection.mutable.Queue[String]("", "", "", "", "")
    def apply(newChar:Char) = {
      queue dequeue()
      queue enqueue {newChar match {
        case `up` => "up"
        case `down` => "down"
        case `left` | `right` => "direction"
        case `jump` => "jump"
        case `defense` => "defend"
        case `attack` => "attack"
        case _ => ""
      }}
      queue mkString
    }
  }

}
