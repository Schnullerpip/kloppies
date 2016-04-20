package main.scala.model.fighter.states.midair

import main.scala.model.GameObject
import main.scala.model.ImageMatrix.JUMPING
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.FighterState

/**
 * Created by julian on 14.02.16.
 */
case class Jumping(f:Fighter) extends FighterState(f){
  f.images.set(JUMPING)

  val movethread = new Thread(new Runnable {
    override def run(): Unit = {
      f.moveable = false
      ifStillJumping{
        f.images.next
        ifStillJumping{
          f.images.next
          ifStillJumping{
            f.images.next
            ifStillJumping{
              f.images.next
              ifStillJumping{
                f.images.next
                ifStillJumping{
                  f.images.next
                  f.z_velocity = f.fighter_strength
                  f.state = Levitate(f)
                }
              }
            }
          }
        }
      }
    }
    f.moveable = true
  })
  movethread.start()

  private def ifStillJumping(b: => Unit): Unit ={
    if(f.state.isInstanceOf[Jumping]) {
      Thread.sleep(1000 / f.speed / 2)
      b
    }
  }

  override def hit = {}
  override def hurtBy(go:GameObject) = {
    movethread.stop()
    super.hurtBy(go)
  }
}
