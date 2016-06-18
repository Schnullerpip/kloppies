package main.scala.model.fighter.states.techniques.fire

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.{Effect, Technique, Techniques}
import main.scala.model.items.magical.fire.FireMine

import scala.util.Random

/**
  * Created by julian on 18.06.16.
  */
case class RainFireMines(c:Fighter) extends Technique(c) with FireTechnique with Effect{
  override val name: String = "RainFireMines"

  override def act: Unit = {
    new Thread(new Runnable {
      override def run(): Unit = {
        for(i <- 0 until 8){
          for(o <- 0 until 8){
            caster.notifyObservers(new FireMine(caster){
              x = caster.x + randomBlur(500)
              y = caster.y + randomBlur(150)
              z = 700
            })
          }
          Thread.sleep(200 + Random.nextInt(300))
        }
      }
    }).start()
  }
  private def randomBlur(max:Int) = Random.nextInt(max) * (if(Random.nextBoolean()) -1 else 1)

  override val manaUse: Int = Techniques.MANA_USE_TECHNIQUE_LEVEL_3
  override val description: String = "Summon a bunch of Fire Mines"
  override val image: BufferedImage = ImageIO.read(new File("images/techniques/rainFireMines.png"))
}
