package main.scala.model.fighter.states.techniques

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.midair.{Landing, Levitate}
import main.scala.model.fighter.states.{FighterState, Normal}
import main.scala.model.intention.Harmless
import main.scala.model.ImageMatrix.{RUNNING_HIT, THROW_TECHNIQUE, USE_TECHNIQUE}
import main.scala.model.states.MidAir


/**
 * Created by julian on 22.02.16.
  * The FighterState that is responsible for performing techniques as ThrowFireball etc.
  * Basically it will check if the Technique sees itself as a Summoning or an Effect.
  *
  * A Summoning will perform a move sqeuence (animation) that will match the movement of throwing something (like a fireball)
  * An Effect will perform a move sequence (animation) that will match the movement of a prayer or a ritual
 */
case class UsingTechnique(fighter:Fighter, technique: Technique) extends FighterState(fighter){

  fighter.mana -= technique.manaUse
  fighter.intention = Harmless
  fighter.state.stop
  fighter.moveable = false
  fighter.vulnerable = true

  override def init = {
    val toRemove = this
    val moveThread = new Thread(new Runnable {
      override def run(): Unit = {
        ifNotInterrupted{
          fighter.images.next
          ifNotInterrupted{
            fighter.images.next
            ifNotInterrupted{
              fighter.images.next
              ifNotInterrupted{
                fighter.images.next
                ifNotInterrupted{
                  fighter.images.next
                  ifNotInterrupted{
                    fighter.images.next
                    Thread.sleep(1000/fighter.speed)
                    technique act
                  }
                }
              }
            }
          }
        }
        if(fighter.state == toRemove) {
          toRemove match {
            case m: MidAir => fighter.state = Levitate(fighter)
            case _ => fighter.state = Normal(fighter)
          }
        }
      }
    })

    technique match{
      case s:Summoning if this.isInstanceOf[MidAir] => fighter.images.set(RUNNING_HIT); moveThread.start()
      case s:Summoning => fighter.images.set(THROW_TECHNIQUE); moveThread.start()
      case e:Effect if this.isInstanceOf[MidAir] => fighter.images.set(USE_TECHNIQUE)
        fighter.z_velocity = fighter.z_velocity match {
          case ltz if ltz <= 0 => 0
          case gtz => gtz
        }
        moveThread.start()
      case e:Effect => fighter.images.set(USE_TECHNIQUE); moveThread.start()
      case _ => technique act
    }
  }

  private def ifNotInterrupted(f: => Unit) = if(fighter.state.isInstanceOf[UsingTechnique]){Thread.sleep(1000/fighter.speed);f}

  override def landing(go:GameObject) = {fighter.state = Landing(fighter)}
}
