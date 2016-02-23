package main.scala.model.fighter.states.techniques.fire

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.{Techniques, Summoning, Technique}
import main.scala.model.items.magical.fire.FireBall

/**
 * Created by julian on 22.02.16.
 * @param caster is the calling fighter, this is given the technique so it can
 *               infuse fighters attributes to technique
 */
case class ThrowFireball(caster:Fighter) extends Technique(caster) with Summoning{
  override val name: String = "ThrowFireball"
  override val manaUse: Int = Techniques.MANA_USE_TECHNIQUE_LEVEL_1

  override def act: Unit = {
    caster.notifyObservers(FireBall(caster))
  }

}
