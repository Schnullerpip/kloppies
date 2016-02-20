package main.scala.model.fighter.states.midair

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{Normal, Hurt, FighterState}
import main.scala.model.ImageMatrix.LANDING

/**
 * Created by julian on 19.02.16.
 */
case class Landing(f:Fighter) extends FighterState(f){
  f.images.set(LANDING)
  f.x_velocity = 0
  f.y_velocity = 0

  new Thread(new Runnable {
    override def run(): Unit = {
      f.moveable = false
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
                  Thread.sleep(1000/f.speed/2)
                }
              }
            }
          }
        }
      }
      f.moveable = true
      f.state = Normal(f)
    }
  }).start()
  def ifNotHurt(b: => Unit){if(!f.state.isInstanceOf[Hurt]){Thread.sleep(1000/f.speed);b}}

  override def hit: Unit = {}
  override def moveUp =   {}
  override def moveDown = {}
  override def moveLeft = {}
  override def moveRight ={}
  override def jump = {}
}
