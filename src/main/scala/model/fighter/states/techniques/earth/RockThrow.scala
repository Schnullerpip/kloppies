package main.scala.model.fighter.states.techniques.earth

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.Techniques
import main.scala.model.items.normal.{Rock, RockMoving}
import main.scala.model.states.MidAir

import scala.util.Random

/**
  * Created by julian on 20.04.16.
  */
class RockThrow(c:Fighter) extends StoneThrow(c){
  override val name:String = "RockThrow"
  override val description = "Rocks will shoot out of your hands"
  override val manaUse:Int = Techniques.MANA_USE_TECHNIQUE_LEVEL_3
  override val image:BufferedImage = RockThrow.image

  override def act:Unit = {
    new Thread(new Runnable {
      override def run(): Unit = {
        var stones = Seq[Rock]()
        for(o <- 0 until 1) {
          val stone = new Rock(caster.x + {if(caster.looksLeft)-caster.width else caster.width}, caster.y, caster.z+caster.height/2) {
            looksLeft = caster.looksLeft
            x_velocity = 13*caster.directionValue
            y_velocity = 3*randomBlur(1)
            z_velocity = 8+randomBlur(6)
            state = new RockMoving(this) with MidAir{
              override def actOnCollision(go:GameObject) = {
                if(go != caster){
                  super.actOnCollision(go)
                }
              }
            }
          }
          caster.notifyObservers(stone)
          stones = stone +: stones
          Thread.sleep(250)
        }
        Thread.sleep(5000)
        stones.foreach{_.goKillYourself}
      }
    }).start()

  }
  private def randomBlur(max:Int) = Random.nextInt(max) * (if(Random.nextBoolean()) -1 else 1)

}

object RockThrow {
  def apply(c:Fighter) = new RockThrow(c)
  lazy val image = ImageIO.read(new File("images/techniques/rock.png"))
}
