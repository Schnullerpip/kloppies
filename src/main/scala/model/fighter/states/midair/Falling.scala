package main.scala.model.fighter.states.midair

import main.scala.model.GameObject
import main.scala.model.ImageMatrix.FALLING
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.aggressive.{LevitatingAttack, RunningAttack}
import main.scala.model.fighter.states.{FighterState, HitTheGround}
import main.scala.model.states.{AnimateMe, MidAir}

/**
 * Created by julian on 21.02.16.
  * The status for falling Fighters
  * If given an opponent it indicates, that the player is falling because he was hit.
  * In this case the fighter will fly away from his opponent according to its strength
 */
case class Falling(f:Fighter, opponent:Option[GameObject] = None)(amount:Option[Int] = {if(opponent isDefined)Some(opponent.get.strength) else None}) extends FighterState(f) with MidAir with AnimateMe {
  f.moveable = false
  f.images.set(FALLING)
  f.z_velocity = amount.getOrElse(0)/2
  f.x_velocity = amount.getOrElse(0)/2 * {if(opponent isDefined)opponent.get.directionValue else 1}
  val height = f.z/2 + amount.getOrElse(0)
  f.takeDamage(amount.getOrElse(0))
  f.vulnerable = false

  val recover_thread = new Thread(new Runnable {
    override def run(): Unit = {
      Thread.sleep(1000)
      if(f.state.isInstanceOf[Falling])f.moveable = true
    }
  })

  recover_thread.start()

  override def actOnCollision(go:GameObject) = {}
  override def landing(go:GameObject) = {f.state = HitTheGround(f, height)}
  override def hurtBy(gameObject: GameObject)(amount:Int = gameObject.strength) = f.state = Falling(f, Some(gameObject))()

  override def moveUp     = if(f.moveable)f.state = Levitate(f)
  override def moveDown   = moveUp
  override def moveLeft   = moveUp
  override def moveRight  = moveUp
  override def jump       = moveUp
  override def hit        = if(f.moveable)f.state = LevitatingAttack(f)
}
