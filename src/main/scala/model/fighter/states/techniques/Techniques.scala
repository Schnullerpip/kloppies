package main.scala.model.fighter.states.techniques

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.earth.{StoneRain, StoneThrow}
import main.scala.model.fighter.states.techniques.fire.ThrowFireball
import main.scala.model.fighter.states.techniques.shock.SpeedPlus
import main.scala.model.fighter.states.techniques.wind.SummonWind

/**
 * Created by julian on 22.02.16.
 */
object Techniques {
  val MANA_USE_TECHNIQUE_LEVEL_1 = 0
  val MANA_USE_TECHNIQUE_LEVEL_2 = MANA_USE_TECHNIQUE_LEVEL_1 * 3
  val MANA_USE_TECHNIQUE_LEVEL_3 = MANA_USE_TECHNIQUE_LEVEL_1 * 6
  val MANA_USE_TECHNIQUE_LEVEL_4 = MANA_USE_TECHNIQUE_LEVEL_1 * 10
  val MANA_USE_TECHNIQUE_LEVEL_5 = MANA_USE_TECHNIQUE_LEVEL_1 * 15

  def newTechnique(name:String, f:Fighter) = {
    name match {
      case "ThrowFireball" => ThrowFireball(f)
      case "SummonWind" =>  SummonWind(f)
      case "SpeedPlus" => SpeedPlus(f)
      case "StoneRain" => StoneRain(f)
      case "StoneThrow" => StoneThrow(f)
      case _ => ThrowFireball(f)
    }
  }
}

object TechniqueLists {
  val fireTechniques = Seq("ThrowFireball")
  val waterTechniques = Seq()
  val earthTechniques = Seq("StoneRain", "StoneThrow")
  val windTechniques = Seq("SummonWind")
  val shockTechniques = Seq("SpeedPlus")
}
