package main.scala.model.player

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.FighterState

/**
 * Created by julian on 14.02.16.
 * Representation of a Keymapping, a player uses to control a Fighter
 */
case class KeySet(up:Char = 'w', down:Char = 's', left:Char = 'a',
                  right:Char = 'd', attack:Char = 'f', defense:Char = 'q',
                  jump:Char = 'e'){
  private var up_set, down_set, left_set, right_set, attack_set, defense_set, jump_set = false
  private val kBuffer = new KeyCombination()

  def released(key: Char, f: Fighter) = {
    val fighter_state = f.state.asInstanceOf[FighterState]
    key match {
      case this.up => up_set = false; fighter_state.stopUp
      case this.down => down_set = false; fighter_state.stopDown
      case this.left => left_set = false; fighter_state.stopLeft
      case this.right => right_set = false; fighter_state.stopRight
      case this.attack => attack_set = false
      case this.defense => defense_set = false; fighter_state.stop
      case this.jump => jump_set = false; //fighter_state.jump
      case _ =>
    }
  }

  def pressed(key:Char, f:Fighter) = {
    val fighter_state = f.state.asInstanceOf[FighterState]
    val combination = kBuffer(key)
    if(f.techniques.contains(combination))
      fighter_state.technique(f.techniques(combination))
    else {
      key match {
        case this.up => up_set = true; fighter_state.moveUp
        case this.down => down_set = true; fighter_state.moveDown
        case this.left => left_set = true; fighter_state.moveLeft
        case this.right => right_set = true; fighter_state.moveRight
        case this.attack => attack_set = true; fighter_state.hit
        case this.defense => defense_set = true; fighter_state.defend
        case this.jump => jump_set = true; fighter_state.jump
        case _ =>
      }
    }
  }

  private class KeyCombination{
    val queue = scala.collection.mutable.Queue[Char](' ', ' ', ' ', ' ', ' ')
    def apply(newChar:Char) = {
      queue dequeue()
      queue enqueue {newChar match {
        case `up` => 'u'
        case `down` => 'd'
        case `left` => 'l'
        case `right` => 'r'
        case `jump` => 'j'
        case `defense` => 'D'
        case `attack` => 'a'
        case _ => ' '
      }}
      queue mkString
    }
  }

}
