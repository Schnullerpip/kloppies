package main.scala.model.fighter.states.aggressive

import main.scala.model.GameObject
import main.scala.model.attributes.LivePoints
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{Hurt, Normal, FighterState}
import main.scala.model.fighter.states.midair.Levitate
import main.scala.model.intention.Harmless
import main.scala.model.states.MidAir
import main.scala.model.ImageMatrix.LEVITATING_HIT

/**
 * Created by julian on 19.02.16.
 */
case class LevitatingAttack(f:Fighter) extends FighterState(f) with MidAir {
  f.images.set(LEVITATING_HIT)
  new Thread(new Runnable {
    override def run(): Unit = {
      f.moveable = false
      ifAggressive {
        f.images.next
        ifAggressive {
          f.images.next
          ifAggressive {
            f.images.next
            ifAggressive {
              f.images.next
              ifAggressive {
                f.images.next
                ifAggressive {
                  f.intention = main.scala.model.intention.Harmful
                  f.images.next
                  Thread.sleep(1000 / f.speed)
                  f.intention = main.scala.model.intention.Harmless
                }
              }
            }
          }
        }
      }
      f.z match {
        case 0 => f.state = Normal(f)
        case _ => f.state = Levitate(f)
      }
    }
  }).start()

  private def ifAggressive(b: => Unit) = if(f.state.isInstanceOf[LevitatingAttack]) {Thread.sleep(1000/f.speed/2);b}
  
  override def hit      = {}
  override def moveUp   = {}
  override def moveDown = {}
  override def moveLeft = {}
  override def moveRight= {}
  override def jump     = {}
}
