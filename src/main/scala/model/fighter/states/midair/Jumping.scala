package main.scala.model.fighter.states.midair

import main.scala.model.GameObject
import main.scala.model.ImageMatrix.JUMPING
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.FighterState

/**
 * Created by julian on 14.02.16.
 */
case class Jumping(f:Fighter, fact:Double = 1.0) extends FighterState(f){
  var contin = true
  val moveThread = new Thread(new Runnable {
    override def run(): Unit = {
      f.moveable = false
      /*the jumping strength will be at least half the fighters strength so a noticable jump will be performed even when 'jump' key was pressed almost unnoticeable*/
      val factor = {
        if (fact < 0.5)
          0.5
        else fact
      }
      f.images.set(JUMPING)
      ifStillJumping {
        f.images.next
        ifStillJumping {
          f.z_velocity = (2*f.strength * factor).toInt
          f.state = UpJump(f)
        }
      }
    }
  })
  moveThread.start()

  private def ifStillJumping(b: => Unit): Unit ={
    if(f.state.isInstanceOf[Jumping] && contin) {
      Thread.sleep(1000 / f.speed / 2)
      b
    }
  }

  override def hit = {}
  override def hurtBy(g:GameObject)(amount:Int = g.strength) = {
    contin = false
    moveThread.stop()
    super.hurtBy(g)(amount)
  }
}
