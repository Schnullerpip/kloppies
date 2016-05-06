package main.scala.model.fighter.states.aggressive

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{Normal, FighterState}
import main.scala.model.fighter.states.midair.Levitate
import main.scala.model.states.MidAir
import main.scala.model.ImageMatrix.LEVITATING_HIT

/**
 * Created by julian on 19.02.16.
 */
case class LevitatingAttack(f:Fighter, strength_bonus:Int = 0) extends FighterState(f) with MidAir {
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
                  val str = f.full_strength
                  f.full_strength += strength_bonus
                  f.intention = main.scala.model.intention.Harmful
                  f.images.next
                  Thread.sleep(1000 / f.speed)
                  f.intention = main.scala.model.intention.Harmless
                  f.full_strength = str
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
}
