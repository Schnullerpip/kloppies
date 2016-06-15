package main.scala.model.fighter.states.techniques.shock

import java.awt.image.BufferedImage

import main.scala.model.ImageMatrix
import main.scala.model.attributes.Speed
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.{Effect, Techniques, Technique}
import main.scala.model.items.magical.shock.ElectricSpark

/**
 * Created by julian on 02.03.16.
 */
case class SpeedPlus( f:Fighter) extends Technique(f) with Effect with ShockTechnique with Speed{
  override val name: String = "SpeedPlus"
  override val manaUse: Int = Techniques.MANA_USE_TECHNIQUE_LEVEL_1
  override val description: String = "Improves speed for 10 Seconds"
  override val image: BufferedImage = SpeedPlus.image
  override var speed = 10

  override def act: Unit = {
    f.notifyObservers(ElectricSpark(f.x, f.y+1, f.z))
    new Thread(new Runnable {
      override def run(): Unit = {
        f.speed += speed
        Thread.sleep(10000)
        f.speed -= speed
      }
    }).start()
  }
}

object SpeedPlus {
  ImageMatrix("images/items/magical/speedplus.png", 2, 6, 50, 50)
  val image = ImageMatrix.matrices("speedplus.png").head(2)
}
