package main.scala.model.fighter.states.aggressive

import main.scala.model.GameObject
import main.scala.model.ImageMatrix.STANDING_HIT
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{FighterState, Normal}

/**
 * Created by julian on 15.02.16.
 */
case class StandardAttack(f:Fighter) extends FighterState(f){
  f.images.set(STANDING_HIT)
  /*start own animationthread*/
  new Thread(new Runnable {
    override def run(): Unit = {
      f.moveable = false
      ifAggressive{
        f.images.next
        ifAggressive{
          f.images.next
          ifAggressive{
            f.images.next
            ifAggressive{
              f.images.next
              ifAggressive{
                f.images.next
                ifAggressive{
                  f.intention = main.scala.model.intention.Harmful
                  f.images.next
                  Thread.sleep(1000/f.speed)
                  f.intention = main.scala.model.intention.Normal
                }
              }
            }
          }
        }
      }
      f.state = Normal(f)
    }
  }).start()

  private def ifAggressive(b: => Unit) = if(f.state.isInstanceOf[StandardAttack]) {Thread.sleep(1000/f.speed/2);b}


  override def actOnCollision(go:GameObject): Unit = {
    go.state match {
      case _ =>
    }
  }
  override def hit = {}
  override def moveUp =   {}
  override def moveDown = {}
  override def moveLeft = {}
  override def moveRight ={}

  override def stop = {
    f.state = Normal(f)
  }
}
