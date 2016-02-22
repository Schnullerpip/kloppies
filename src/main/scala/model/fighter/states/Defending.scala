package main.scala.model.fighter.states

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.aggressive.StandardAttack
import main.scala.model.ImageMatrix.DEFENDING

/**
 * Created by julian on 16.02.16.
 */
case class Defending(f:Fighter) extends FighterState(f){
  f.images.set(DEFENDING)
  f.moveable = false
  var reflexes = true
  var dodge = true

  override def hit = f.state = StandardAttack(f)
  override def hurtBy(go:GameObject) = {
    println(s"$go -> ${go.looksLeft} -> ${f.name}")
    f.looksLeft = !go.looksLeft
    if (go.strength > f.strength || !reflexes) {
      super.hurtBy(go)
    } else if(reflexes){
      if (dodge) {
        new Thread(new Runnable {
    override def run(): Unit = {
      reflexes = false
      dodge = false
      ifNotHurt {
        f.images.set(DEFENDING)
        Thread.sleep(1000 / f.speed)
        ifNotHurt{
          f.images.next
          Thread.sleep(1000/f.speed)
          ifNotHurt{
            f.images.next
            Thread.sleep(1000/f.speed)
            reflexes = true
          }
        }
      }
    }
  }).start()
      } else{
        new Thread(new Runnable {
    override def run(): Unit = {
      dodge = true
      reflexes = false
      ifNotHurt {
        f.images.set(DEFENDING, 3)
        Thread.sleep(1000 / f.speed)
        ifNotHurt{
          f.images.next
          Thread.sleep(1000 / f.speed)
          ifNotHurt {
            f.images.next
            Thread.sleep(1000 / f.speed)
            ifNotHurt {
              f.images.next
              Thread.sleep(1000 / f.speed)
              reflexes = true
            }
          }
        }
      }
    }
  }).start()
      }
    }
  }

  override def stop = {
    f.state = Normal(f)
  }

  private def ifNotHurt(b: =>Unit){if(!f.state.isInstanceOf[Hurt])b}
}
