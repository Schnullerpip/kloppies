package main.scala.model.fighter.states.techniques.earth

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.Techniques
import main.scala.model.items.normal.{Rock, RockHurt, RockMoving}
import main.scala.model.states.MidAir
import main.scala.util.sound.SoundDistributor

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
        for(o <- 0 until 7) {
          val stone = new Rock(caster.x + {if(caster.looksLeft)-caster.width else caster.width}, caster.y, 0/*caster.z+caster.height/2*/) {
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
          SoundDistributor.play("crumble")
          stones = stone +: stones
          Thread.sleep(300)
        }
        Thread.sleep(5000)
        stones = stones.reverse
        while(stones nonEmpty){
          Thread.sleep(1000)
          stones.head.state = new RockHurt(stones.head, null)(stones.head.hp)
          stones = stones.slice(1, stones.size)
        }
      }
    }).start()

  }
  private def randomBlur(max:Int) = Random.nextInt(max) * (if(Random.nextBoolean()) -1 else 1)

}

object RockThrow {
  def apply(c:Fighter) = new RockThrow(c)
  lazy val image = ImageIO.read(new File("images/techniques/rock.png"))
}
