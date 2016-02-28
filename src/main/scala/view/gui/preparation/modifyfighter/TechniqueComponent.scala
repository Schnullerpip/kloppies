package main.scala.view.gui.preparation.modifyfighter

import javax.swing.ImageIcon
import javax.swing.border.LineBorder

import main.scala.model.fighter.states.techniques.Technique
import main.scala.view.gui.Defaults

import scala.swing._

/**
 * Created by julian on 24.02.16.
 */
class TechniqueComponent(technique:Technique) extends GridPanel(3,1){
  border = new LineBorder(Defaults.REDDISH)
  preferredSize = new Dimension(200, 100)

  val t_name = new Label(technique.name)
  val t_image = new Label(){icon = new ImageIcon(technique.image)}
  val t_description = new ScrollPane(new TextArea(technique.description, 100, 100){
    editable = false
  })

  contents += t_name
  contents += t_image
  contents += t_description
}
