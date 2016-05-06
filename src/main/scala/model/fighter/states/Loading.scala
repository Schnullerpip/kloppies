package main.scala.model.fighter.states

import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.aggressive.{LevitatingAttack, StandardAttack}
import main.scala.model.fighter.states.midair.{Jumping, Levitate}
import main.scala.model.states.MidAir

/**
  * Created by julian on 04.05.16.
  */
case class Loading(f:Fighter, kneelDown:Boolean = true, jump_last:Long = System.currentTimeMillis()) extends FighterState(f){


  var contin = true

  /**
    * loop through the last three loading pics to show a kind of tension
    * */
  private def loadingLoop = {
    var index = 4
    while (contin) {
      for (i <- 4 until 6) {
        ifStillLoading {
          f.images.set(ImageMatrix.LOADING, i)
        }
      }
    }
  }


  val moveTrhead = new Thread(new Runnable {
    override def run(): Unit = {
      if (kneelDown) {
        f.images.set(ImageMatrix.LOADING)
        ifStillLoading {
          f.images.next
          ifStillLoading {
            f.images.next
            ifStillLoading {
              f.images.next
              loadingLoop
            }
          }
        }
      } else {
        loadingLoop
      }
    }
  })
  moveTrhead.start()

  override def hit = {
    stop
    f.state = f.state match {
      case ma:MidAir => LevitatingAttack(f, (factor*f.full_strength).toInt)
      case _ => StandardAttack(f, (factor*f.full_strength).toInt)
    }
  }
  override def moveUp = {}
  override def moveDown = {}
  override def moveLeft = {}
  override def moveRight = {}
  override def jump = {
    stop
    f.state match {
      case ma:MidAir => f.state = Levitate(f)
      case _ =>
        f.state = new Jumping(f, factor)
    }
  }
  override def defend = {
    stop
    super.defend
  }
  override def landing(go:GameObject) = {
    stop
    f.state = new Loading(f, false, jump_last){
      f.z_velocity = 0
    }
  }
  override def stop = {
    contin = false
    moveTrhead.stop()
  }
  override def hurtBy(go:GameObject): Unit ={
    super.hurtBy(go)
  }

  private def factor = {
    val max = 1000.0
    val timestamp = System.currentTimeMillis()
    val diff = (timestamp - jump_last).toDouble
    val factor = {if(diff > max) max else diff} /max
    factor
  }

  private def ifStillLoading(b: => Unit): Unit = {
    if (f.state.isInstanceOf[Loading] && contin) {
      Thread.sleep(1000 / f.speed / 2)
      b
    }
  }
}
