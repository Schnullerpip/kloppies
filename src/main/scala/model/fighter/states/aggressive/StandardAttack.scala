package main.scala.model.fighter.states.aggressive

import main.scala.model.GameObject
import main.scala.model.ImageMatrix.STANDING_HIT
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{FighterState, Normal}

/**
 * Created by julian on 15.02.16.
 */
case class StandardAttack(f:Fighter, strength_bonus:Int = 0) extends FighterState(f){
  f.images.set(STANDING_HIT)
  f.moveable = false
  /*start own animationthread*/
  val moveThread = new Thread(new Runnable {
    override def run(): Unit = {
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
                  val str = f.full_strength
                  f.full_strength += strength_bonus
                  f.intention = main.scala.model.intention.Harmful
                  f.images.next
                  Thread.sleep(1000/f.speed)
                  f.intention = main.scala.model.intention.Harmless
                  f.full_strength = str
                }
              }
            }
          }
        }
      }
      if(f.state.isInstanceOf[StandardAttack])f.state = Normal(f)
    }
  })
  moveThread.start()

  private def ifAggressive(b: => Unit) = if(f.state.isInstanceOf[StandardAttack]) {Thread.sleep(1000/f.speed/2);b}

  override def hurtBy(g:GameObject)(amount:Int = g.strength) = {if(amount >= f.mass)moveThread.stop(); super.hurtBy(g)(amount)}
  override def hit = {}

  override def stop = {
    f.state = Normal(f)
  }
}
