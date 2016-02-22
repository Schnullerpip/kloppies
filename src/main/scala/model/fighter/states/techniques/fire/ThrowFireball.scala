package main.scala.model.fighter.states.techniques.fire

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.{Techniques, Summoning, Technique}
import main.scala.model.items.magical.fire.FireBall
import main.scala.util.{Observable, Observer}

/**
 * Created by julian on 22.02.16.
 */
case class ThrowFireball(controller:Observer) extends Technique(controller) with Summoning with Observable{
  override val name: String = "ThrowFireball"
  override val manaUse: Int = Techniques.MANA_USE_TECHNIQUE_LEVEL_1
  addObserver(controller)

  /**
   * @param caster is the calling fighter, this is given the technique so it can
   *               infuse fighters attributes to technique
   **/
  override def act(caster: Fighter): Unit = {
    notifyObservers(FireBall(caster))
  }

}
