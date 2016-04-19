package main.scala.model.fighter.states.techniques.earth
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import scala.util.Random
import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.{Summoning, Technique, Techniques}
import main.scala.model.items.normal.{Stone, StoneFalling, StoneNormal}
import main.scala.model.items.state.Normal
import main.scala.util.sound.SoundDistributor
/**
  * Created by julian on 08.04.16.
  */
case class StoneThrow(c:Fighter) extends  Technique(c) with Summoning with EarthTechnique{
    override val name: String = "StoneThrow"

    override def act: Unit = {
      new Thread(new Runnable {
        override def run(): Unit = {
          var stones = Seq[Stone]()
          for(o <- 0 until 15) {
            val stone = new Stone(caster.x + caster.width/2 * (1 + caster.directionValue), caster.y, caster.z+caster.height/2) {
              x_velocity = 13*caster.directionValue
              z_velocity = 8+randomBlur(3)
              state = new StoneFallingMagical(this, caster)
            }
            caster.notifyObservers(stone)
            stones = stone +: stones
            Thread.sleep(150)
          }
          Thread.sleep(5000)
          stones.foreach{_.goKillYourself}
        }
      }).start()
    }

    override val manaUse: Int = Techniques.MANA_USE_TECHNIQUE_LEVEL_2
    override val description: String = "Stones will spawn out of your hand"
    override val image: BufferedImage = StoneRain.image

    private def randomBlur(max:Int) = Random.nextInt(max) * (if(Random.nextBoolean()) -1 else 1)

    private class StoneFallingMagical(stone:Stone, caster:Fighter) extends StoneFalling(stone) {
      stone.tangible = false
      override def actOnCollision(go:GameObject)={
        if(go != caster)
          super.actOnCollision(go)
      }
      override def landing = {
        val velocity = stone.z_velocity * {if(stone.z_velocity < 0 ) -1 else 1}
        if(velocity > stone.mass){
          stone.z_velocity = velocity/2
          SoundDistributor.play("deep_smash")
        }else{
          stone.x_velocity = 0
          stone.y_velocity = 0
          stone.z_velocity = 0
          stone.state = new StoneNormal(stone)
          SoundDistributor.play("deep_smash")
        }
  }
    }

  }

  object StoneThrow{
    lazy val image = ImageIO.read(new File("images/techniques/stonerain.png"))
  }
