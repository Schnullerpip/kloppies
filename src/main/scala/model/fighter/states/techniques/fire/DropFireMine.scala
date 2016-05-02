package main.scala.model.fighter.states.techniques.fire

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.{Technique, Techniques}
import main.scala.model.items.magical.fire.FireMine

/**
  * Created by julian on 02.05.16.
  */
class DropFireMine(caster:Fighter) extends Technique(caster) with FireTechnique{
  override val image: BufferedImage = DropFireMine.image
  override val name: String = "DropFireMine"

  override def act: Unit = {
    caster.notifyObservers(new FireMine(caster))
    caster.y_velocity += caster.strength
    caster.state.levitate
  }

  override val manaUse: Int = Techniques.MANA_USE_TECHNIQUE_LEVEL_1
  override val description: String = "Drops an exploding mine under you hitting opponents on contact"
}

object DropFireMine{
  val image = ImageIO.read(new File("images/techniques/explosion.png"))
  def apply(caster:Fighter) = new DropFireMine(caster)
}
