package main.scala.model.fighter.states

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.ImageMatrix.OUCH
import main.scala.model.fighter.states.midair.Falling
import main.scala.model.intention.Harmless

/**
 * Created by julian on 15.02.16.
 */
case class Hurt(f:Fighter, gameobject:GameObject, combo:Int = 0)(damageAmount:Int = gameobject.strength) extends FighterState(f){
  private val sleepTime = 125
  private var contin = true
  private val MAX_COMBO = 1
  val movethread = new Thread(new Runnable {
    override def run(): Unit = {
      if (contin && f.state.isInstanceOf[Hurt]) {
        Thread.sleep(sleepTime)
        f.images.next
        if (contin && f.state.isInstanceOf[Hurt]) {
          Thread.sleep(sleepTime)
          f.images.next
          if (contin && f.state.isInstanceOf[Hurt]) {
            Thread.sleep(sleepTime)
            f.images.next
            if (contin && f.state.isInstanceOf[Hurt]) {
              Thread.sleep(sleepTime)
              f.images.next
              if (contin && f.state.isInstanceOf[Hurt]) {
                Thread.sleep(sleepTime)
                f.images.next
                if (contin && f.state.isInstanceOf[Hurt]) {
                  Thread.sleep(sleepTime)
                  f.images.next
                  if (combo <= MAX_COMBO && f.state.isInstanceOf[Hurt] && contin) {
                    f.state = Normal(f)
                  } else f.state = Falling(f, Some(gameobject))
                }
              }
            }
          }
        }
      }
    }
  })
  f.intention = Harmless
  f.looksLeft = !gameobject.looksLeft
  f.takeDamage(damageAmount)
  f.images.set(OUCH)
  movethread.start()

  override def hurtBy(go:GameObject) = {
    contin = false
    movethread.stop()
    f.state = if(combo < MAX_COMBO)Hurt(f, go, combo +1)()else Falling(f, Some(go))
  }
  override def hit      = {}
  override def moveUp   = {}
  override def moveDown = {}
  override def moveLeft = {}
  override def moveRight= {}
}
