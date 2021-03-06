package main.scala.model.fighter.states.midair

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.aggressive.LevitatingAttack
import main.scala.model.fighter.states.{FighterState, Loading}
import main.scala.model.fighter.states.techniques.{Technique, UsingTechnique}
import main.scala.model.states.MidAir

/**
  * Created by julian on 04.05.16.
  * describes the jumping procedure MidAir unlike Jumping which describes the Jumping PArt on the Floor
  */
case class UpJump(f:Fighter) extends FighterState(f) with MidAir{

  f.moveable = true
  var contin = true

  val moveThread = new Thread(new Runnable {
    override def run(): Unit = {
      f.images.next
      ifStillJumping {
        f.images.next
        ifStillJumping {
          f.images.next
          ifStillJumping {
            f.images.next
            ifStillJumping {
              f.images.next
              f.state = Levitate(f)
            }
          }
        }
      }
    }
  })
  moveThread.start()

  private def ifStillJumping(b: => Unit): Unit ={
    if(f.state.isInstanceOf[UpJump] && contin) {
      Thread.sleep(1000 / f.speed)
      b
    }
  }

  override def hit = {
    contin = false
    moveThread.stop()
    f.state = LevitatingAttack(f)
  }
  override def hurtBy(g:GameObject)(amount:Int = g.strength) = {
    contin = false
    moveThread.stop()
    super.hurtBy(g)(amount)
  }
  override def jump = {if(f.moveable){
    contin = false
    moveThread.stop()
    f.state = new Loading(f) with MidAir
  }}
  override def technique(t:Technique) = {
    f.state = new UsingTechnique(f, t) with MidAir
    f.state.init
  }
  override def moveUp = {}
  override def moveDown = {}
  override def moveLeft = if(f.moveable)f.x_velocity = -1 * f.fighter_strength/2
  override def moveRight =if(f.moveable) f.x_velocity = f.fighter_strength/2

}
