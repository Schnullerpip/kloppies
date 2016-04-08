package main.scala.model.fighter.states.techniques.earth

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.{Effect, Technique, Techniques}
import main.scala.model.items.normal.{Stone, StoneFalling}

import scala.util.Random

/**
  * Created by julian on 07.04.16.
  */
case class StoneRain(c:Fighter) extends  Technique(c) with Effect with EarthTechnique{
  override val name: String = "StoneRain"

  override def act: Unit = {
    new Thread(new Runnable {
      override def run(): Unit = {
        var stones = Seq[Stone]()
        for(i <- 0 until 15) {
          for(o <- 0 until 15) {
            val stone = new Stone(caster.x + randomBlur(500), caster.y + randomBlur(150), 400) {
              state = new StoneFallingMagical(this, caster)
            }
            caster.notifyObservers(stone)
            stones = stone +: stones
          }
          Thread.sleep(200 + Random.nextInt(300))
        }
        Thread.sleep(3000)
        stones.foreach{_.goKillYourself}
      }
    }).start()
  }

  override val manaUse: Int = Techniques.MANA_USE_TECHNIQUE_LEVEL_2
  override val description: String = "Stones will spawn randomly in the sky and bury your Enemies alive"
  override val image: BufferedImage = StoneRain.image

  private def randomBlur(max:Int) = Random.nextInt(max) * (if(Random.nextBoolean()) -1 else 1)

  private class StoneFallingMagical(stone:Stone, caster:Fighter) extends StoneFalling(stone) {
    override def actOnCollision(go:GameObject)={
      if(go != caster && !go.isInstanceOf[Stone])
        super.actOnCollision(go)
    }
  }

}

object StoneRain{
  lazy val image = ImageIO.read(new File("images/techniques/stonerain.png"))
}
