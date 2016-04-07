package main.scala.model.fighter.states.techniques

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.earth.StoneRain
import main.scala.model.fighter.states.techniques.fire.ThrowFireball
import main.scala.model.fighter.states.techniques.shock.SpeedPlus
import main.scala.model.fighter.states.techniques.wind.SummonWind

/**
 * Created by julian on 22.02.16.
 */
object Techniques {
  val MANA_USE_TECHNIQUE_LEVEL_1 = 10
  val MANA_USE_TECHNIQUE_LEVEL_2 = 30
  val MANA_USE_TECHNIQUE_LEVEL_3 = 60
  val MANA_USE_TECHNIQUE_LEVEL_4 = 100
  val MANA_USE_TECHNIQUE_LEVEL_5 = 300

  def newTechnique(name:String, f:Fighter) = {
    name match {
      case "ThrowFireball" => ThrowFireball(f)
      case "SummonWind" =>  SummonWind(f)
      case "SpeedPlus" => SpeedPlus(f)
      case "StoneRain" => StoneRain(f)
      case _ => ThrowFireball(f)
    }
  }
}

object TechniqueLists {
  val fireTechniques = Seq("ThrowFireball")
  val waterTechniques = Seq()
  val earthTechniques = Seq("StoneRain")
  val windTechniques = Seq("SummonWind")
  val shockTechniques = Seq("SpeedPlus")
}
