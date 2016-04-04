package main.scala.model.fighter.states

import main.scala.model.fighter.Fighter
import main.scala.model.ImageMatrix._
import main.scala.model.fighter.states.midair.Landing
import main.scala.model.states.AnimateMe

/**
 * Created by julian on 14.02.16.
 */
case class Lying(f:Fighter) extends FighterState(f) with AnimateMe{
  f.collidable = false
  f.vulnerable = false
  f.moveable = false
  f.images.set(LYING)
  new Thread(new Runnable {
    override def run(): Unit = {
      Thread.sleep(3000)
      f.vulnerable = true
      f.collidable = true
      f.state = Landing(f)
    }
  }).start()
  override def landing = {}
  override def hit = {}
}
