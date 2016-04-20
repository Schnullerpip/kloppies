package main.scala.model.fighter.states.techniques

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.midair.{Landing, Levitate}
import main.scala.model.fighter.states.{Normal, FighterState}
import main.scala.model.intention.Harmless
import main.scala.model.ImageMatrix.{THROW_TECHNIQUE, USE_TECHNIQUE, RUNNING_HIT}
import main.scala.model.states.{MidAir, AnimateMe}

/**
 * Created by julian on 22.02.16.
 */
case class UsingTechnique(fighter:Fighter, technique: Technique) extends FighterState(fighter){
  technique match{
    case s:Summoning if this.isInstanceOf[MidAir] => fighter.images.set(RUNNING_HIT)
    case s:Summoning => fighter.images.set(THROW_TECHNIQUE)
    case e:Effect if this.isInstanceOf[MidAir] => fighter.images.set(USE_TECHNIQUE)
    case e:Effect => fighter.images.set(USE_TECHNIQUE)
    case _ =>
  }

  fighter.mana -= technique.manaUse
  fighter.intention = Harmless
  fighter.moveable = false
  fighter.vulnerable = true

  val toRemove = this
  new Thread(new Runnable {
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
  }).start()

  private def ifNotInterrupted(f: => Unit) = if(fighter.state.isInstanceOf[UsingTechnique]){Thread.sleep(1000/fighter.speed);f}

  override def landing(go:GameObject) = {fighter.state = Landing(fighter)}
}
