package main.scala.model.fighter.states.midair

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.aggressive.RunningAttack
import main.scala.model.fighter.states.{FighterState, Hurt, Loading, Normal}
import main.scala.model.ImageMatrix.LANDING
import main.scala.model.intention.Harmless

/**
 * Created by julian on 19.02.16.
 */
case class Landing(f:Fighter) extends FighterState(f){
  f.groundContact = true
  f.images.set(LANDING)
  f.x_velocity = 0
  f.y_velocity = 0
  f.z_velocity = 0
  f.intention = Harmless
  f.moveable = false

  val movethread = new Thread(new Runnable {
    override def run(): Unit = {
      ifNotHurt{
        f.images.next
        ifNotHurt{
          f.images.next
          ifNotHurt{
            f.images.next
            ifNotHurt{
              f.images.next
              ifNotHurt{
                f.images.next
                ifNotHurt{
                  f.images.next
                  f.state = Normal(f)
                }
              }
            }
          }
        }
      }
    }
  })
  movethread.start()
  def ifNotHurt(b: => Unit){if(!f.state.isInstanceOf[Hurt]){Thread.sleep(1000/f.speed);b}}

  override def hit      = {movethread.stop(); f.state = RunningAttack(f)}
  override def jump = {movethread.stop(); f.state = Loading(f)}
  override def moveLeft = if(!f.looksLeft) f.looksLeft = true
  override def moveRight = if(f.looksLeft) f.looksLeft = false
}

