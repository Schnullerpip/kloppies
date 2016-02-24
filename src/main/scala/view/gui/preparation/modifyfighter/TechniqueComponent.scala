package main.scala.view.gui.preparation.modifyfighter

import javax.swing.ImageIcon
import javax.swing.border.LineBorder

import main.scala.model.fighter.states.techniques.Technique
import main.scala.view.gui.Defaults

import scala.swing._

/**
 * Created by julian on 24.02.16.
 */
class TechniqueComponent(technique:Technique) extends BorderPanel{
  border = new LineBorder(Defaults.REDDISH)
  preferredSize = new Dimension(200, 100)
  val t_name = new Label(technique.name)
  val t_image = new Label(){icon = new ImageIcon(technique.image)}
  //TODO looks extrmely messy
  val t_description = new ScrollPane(new TextArea(technique.description, 100, 100){
    preferredSize = new Dimension(100, 100)
    editable = false
  })

  layout += new FlowPanel(){contents += t_name} -> BorderPanel.Position.North

  layout += new FlowPanel(){
    contents += new FlowPanel() {
      contents += t_image
      contents += new GridPanel(1, 2) {
        contents += new Label("Mana use")
        contents += new Label(s"${technique.manaUse}")
      }
    }
    contents += t_description
  } -> BorderPanel.Position.Center
}
