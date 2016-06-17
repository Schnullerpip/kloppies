package main.scala.model.fighter.states.aggressive

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{FighterState, Normal, Running}
import main.scala.model.ImageMatrix.RUNNING_HIT
import main.scala.util.sound.SoundDistributor

/**
 * Created by julian on 19.02.16.
 */
case class RunningAttack(f:Fighter, strength_bonus:Int = 0) extends FighterState(f) {
    f.images.set(RUNNING_HIT)
    val moveThread = new Thread(new Runnable {
      override def run(): Unit = {
        //f.moveable = false
        ifAggressive {
          f.images.next
          ifAggressive {
            f.images.next
            ifAggressive {
              f.images.next
              SoundDistributor.play("hit_woosh")
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
        f.state = if(f.moving)Running(f)else Normal(f)
      }
    })
  moveThread.start()

  private def ifAggressive(b: => Unit) = if(f.state.isInstanceOf[RunningAttack]) {Thread.sleep(1000/f.speed/2);b}
  override def hurtBy(g:GameObject)(amount:Int = g.strength) = {if(amount >= f.mass)moveThread.stop(); super.hurtBy(g)(amount)}

  override def hit      = {}
}
