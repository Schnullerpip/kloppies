package main.scala.model.fighter.states.techniques.wind

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.{Techniques, Effect, Technique}
import main.scala.model.items.magical.wind.Wind

/**
 * Created by julian on 25.02.16.
 */
case class SummonWind(caster:Fighter) extends Technique(caster) with Effect{
  override val name: String = "SummonWind"

  override val manaUse: Int = Techniques.MANA_USE_TECHNIQUE_LEVEL_1
  override val description: String = """Summons wind around the caster either accelerating objects towards the casters direction
    or slowing down the movement of objects opposing the caster. Or just move all the motionless objects around him/her."""
  override val image: BufferedImage = ImageIO.read(new File("images/items/magical/wind.png"))

  override def act: Unit = {
    val wind = Wind(caster)
    caster.notifyObservers(wind)

    new Thread(new Runnable {
      override def run(): Unit = {
        Thread.sleep(10000)
        wind.goKillYourself
      }
    }).start()
  }
}
