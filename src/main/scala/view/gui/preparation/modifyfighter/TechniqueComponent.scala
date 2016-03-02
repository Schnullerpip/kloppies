package main.scala.view.gui.preparation.modifyfighter

import java.awt.Color
import javax.swing.ImageIcon
import javax.swing.border.LineBorder

import main.scala.model.fighter.states.techniques.Technique
import main.scala.model.fighter.states.techniques.earth.EarthTechnique
import main.scala.model.fighter.states.techniques.fire.FireTechnique
import main.scala.model.fighter.states.techniques.shock.ShockTechnique
import main.scala.model.fighter.states.techniques.water.WaterTechnique
import main.scala.model.fighter.states.techniques.wind.WindTechnique
import main.scala.view.gui.Defaults

import scala.swing._

/**
 * Created by julian on 24.02.16.
 */
class TechniqueComponent(technique:Technique) extends BoxPanel(Orientation.Vertical){
  private val f = technique.caster
  private val combination:Option[String] = {
    val tech = f.techniques.filter(_._2.name == technique.name)
    if(tech nonEmpty)
      Some("["+tech.head._1+"]")
    else
      None
  }
  border = new LineBorder(Color.BLACK)
  preferredSize = new Dimension(80, 50)
  technique match {
    case f:FireTechnique =>
      background = Defaults.REDDISH
    case w:WaterTechnique =>
      background = Color.BLUE
    case e:EarthTechnique =>
      background = Color.LIGHT_GRAY
    case wi:WindTechnique =>
      background = Color.WHITE
    case s:ShockTechnique =>
      background = Color.YELLOW
    case _ =>
  }

  val t_name = new Label(technique.name + s" ${combination.getOrElse("")}")
  val t_image = new Label(){icon = new ImageIcon(technique.image)}
  val t_description = new Label("<html><p>"+technique.description+"</p></html>"){
    opaque = true
    //editable = false
    technique match {
      case f:FireTechnique =>
        background = Defaults.REDDISH
        foreground = Color.WHITE
      case w:WaterTechnique =>
        background = Color.BLUE
        foreground = Color.WHITE
      case e:EarthTechnique =>
        background = Color.LIGHT_GRAY
        foreground = Color.BLACK
      case wi:WindTechnique =>
        background = Color.WHITE
        foreground = Color.BLACK
      case s:ShockTechnique =>
        background = Color.YELLOW
        foreground = Color.BLACK
      case _ =>
    }
  }

  contents += t_name
  contents += t_image
  contents += t_description
}
