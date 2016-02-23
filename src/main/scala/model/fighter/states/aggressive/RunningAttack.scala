package main.scala.model.fighter.states.aggressive

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{Normal, Running, FighterState}
import main.scala.model.ImageMatrix.RUNNING_HIT

/**
 * Created by julian on 19.02.16.
 */
case class RunningAttack(f:Fighter) extends FighterState(f) {
    f.images.set(RUNNING_HIT)
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
        f.state = if(f.moving)Running(f)else Normal(f)
      }
    }).start()

  private def ifAggressive(b: => Unit) = if(f.state.isInstanceOf[RunningAttack]) {Thread.sleep(1000/f.speed/2);b}

  override def hit      = {}
}
