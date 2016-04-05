package main.scala.model.fighter.states.midair

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.aggressive.StandardAttack
import main.scala.model.fighter.states.{Normal, Hurt, FighterState}
import main.scala.model.ImageMatrix.LANDING

/**
 * Created by julian on 19.02.16.
 */
case class Landing(f:Fighter) extends FighterState(f){
  f.images.set(LANDING)
  f.x_velocity = 0
  f.y_velocity = 0
  f.moveable = false
  f.collidable = false

  val movethread = new Thread(new Runnable {
    override def run(): Unit = {
      //ifNotHurt{
      //  f.images.next
      //  ifNotHurt{
      //    f.images.next
      //    ifNotHurt{
      //      f.images.next
      //      ifNotHurt{
      //        f.images.next
      //        ifNotHurt{
      //          f.images.next
      //          ifNotHurt{
      //            f.images.next
      //            Thread.sleep(1000/f.speed/2)
      //          }
      //        }
      //      }
      //    }
      //  }
      //}
      Thread.sleep(1000/f.speed)
      f.images.next
      Thread.sleep(1000/f.speed)
      f.images.next
      Thread.sleep(1000/f.speed)
      f.images.next
      Thread.sleep(1000/f.speed)
      f.images.next
      Thread.sleep(1000/f.speed)
      f.images.next
      Thread.sleep(1000/f.speed)
      f.images.next
      Thread.sleep(1000/f.speed/2)
      f.state = Normal(f)
    }
  })
  movethread.start()
  def ifNotHurt(b: => Unit){if(!f.state.isInstanceOf[Hurt]){Thread.sleep(1000/f.speed);b}}

  override def actOnCollision(go:GameObject)={}
  override def hit      = {movethread.stop(); f.state = StandardAttack(f)}
}
