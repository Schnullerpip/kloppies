package main.scala.model.fighter.states

import main.scala.model.fighter.Fighter
import main.scala.model.ImageMatrix.HIT_THE_GROUND

/**
 * Created by julian on 22.02.16.
 */
case class HitTheGround(f:Fighter, height:Int=0) extends FighterState(f) {
  f.z_velocity = 0
  f.x_velocity = 0
  f.y_velocity = 0
  f.images.set(HIT_THE_GROUND)
  f.vulnerable = true
  f.takeDamage(height)
  f.vulnerable = false
  f.moveable = false
  private val sleepTime = 100
  new Thread(new Runnable {
    override def run(): Unit = {
      Thread.sleep(sleepTime)
      f.images.next
      Thread.sleep(sleepTime)
      f.images.next
      Thread.sleep(sleepTime)
      f.images.next
      Thread.sleep(sleepTime)
      f.images.next
      Thread.sleep(sleepTime)
      f.images.next
      Thread.sleep(sleepTime)
      f.images.next
      f.state = Lying(f)
    }
  }).start()

  override def hit: Unit = {}
}
