package main.scala.model.fighter.states.techniques.wind

import java.awt.image.BufferedImage

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.{Techniques, Effect, Technique}

/**
 * Created by julian on 25.02.16.
 */
class SummonWind(caster:Fighter) extends Technique(caster) with Effect{
  override val name: String = "SummonWind"

  override def act: Unit = ???

  override val manaUse: Int = Techniques.MANA_USE_TECHNIQUE_LEVEL_1
  override val description: String = "Summons wind around the caster either accelerating objects towards the casters direction" +
    "or slowing down the movement of objects opposing the caster. Or just move all the motionless objects around him/her."
  override val image: BufferedImage = _
}
