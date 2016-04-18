package main.scala.model.fighter.states

import main.scala.model.fighter.Fighter
import main.scala.model.ImageMatrix._
import main.scala.model.fighter.states.aggressive.RunningAttack
import main.scala.model.fighter.states.midair.{Falling, Landing}
import main.scala.model.intention.Harmless
import main.scala.model.states.AnimateMe

/**
 * Created by julian on 14.02.16.
 */
case class Lying(f:Fighter) extends FighterState(f) with AnimateMe{
  f.collidable = false
  f.vulnerable = false
  f.moveable = false
  f.images.set(LYING)
  f.intention = Harmless

  new Thread(new Runnable {
    override def run(): Unit = {
      Thread.sleep(3000)
      f.moveable = true
      //f.state = Landing(f)
    }
  }).start()

  override def landing = {}
  override def hit = if(f.moveable){ f.collidable = true; f.state = RunningAttack(f)}

  override def moveUp = if(f.moveable) f.state = Landing(f)
  override def moveDown = moveUp
  override def moveLeft = moveUp
  override def moveRight = moveUp
  override def defend = moveUp
  override def levitate = Falling(f)
}
