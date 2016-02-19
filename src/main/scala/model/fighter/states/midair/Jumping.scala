package main.scala.model.fighter.states.midair

import main.scala.model.ImageMatrix.JUMPING
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.FighterState

/**
 * Created by julian on 14.02.16.
 */
case class Jumping(f:Fighter) extends FighterState(f){
  f.images.set(JUMPING)

  new Thread(new Runnable {
    override def run(): Unit = {
      f.moveable = false
      f.x_velocity = 0
      f.y_velocity = 0
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
                  f.z_velocity = (f.strength * 2).toInt
                  f.state = Levitate(f)
                }
              }
            }
          }
        }
      }
    }
    f.moveable = true
  }).start()

  private def ifStillJumping(b: => Unit): Unit ={
    Thread.sleep(1000/f.speed/2)
    b
  }

  override def hit = {}
  override def moveUp =   {}
  override def moveDown = {}
  override def moveLeft = {}
  override def moveRight = {}
  override def jump = {}
}
