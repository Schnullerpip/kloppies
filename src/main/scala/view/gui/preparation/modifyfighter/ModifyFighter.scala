package main.scala.view.gui.preparation.modifyfighter

import java.awt.Font
import javax.swing.border.LineBorder
import javax.swing.{ImageIcon, ScrollPaneConstants, Timer}

import main.scala.model.ImageMatrix
import main.scala.model.fighter.Fighter
import main.scala.view.gui.Defaults
import main.scala.view.gui.preparation.modemenu.ModeMenu
import main.scala.view.gui.preparation.modifyfighter.techniqueTree.TechniqueTreeMenu

import scala.swing.ScrollPane.BarPolicy
import scala.swing._
import scala.swing.event.MouseClicked

/**
 * Created by julian on 23.02.16.
 * The Standard class for fighter exhibition and modification
 */
case class ModifyFighter(f:Fighter, parent:ModeMenu) extends BorderPanel{
  val fighter_image = new Label{
    f.images.set(ImageMatrix.RUNNING)
    icon = new ImageIcon(f.image)
  }
  preferredSize = new Dimension(150, 150)
  val f_name = new Label(s"${f.name}")
  f_name.peer.setFont(new Font(f_name.peer.getFont.getName, Font.PLAIN, 25))

  layout += new BoxPanel(Orientation.Vertical){
    contents += new FlowPanel{ contents += f_name }
    contents += new FlowPanel{ contents += fighter_image }
  } -> BorderPanel.Position.North

  layout += new GridPanel(1, 2){
    contents += new BoxPanel(Orientation.Vertical) {
      border = new LineBorder(Defaults.OCKER)
      contents += new FlowPanel {
        contents += FighterGraph(f, 300)
      }

      contents += new GridPanel(5, 3) {
        contents += new Label("HP")
        contents += new InformedLabel(f.hp _)
        contents += new LabelButton(f, f.increase_full_hp _)

        contents += new Label("STRENGTH")
        contents += new InformedLabel(f.fighter_strength _)
        contents += new LabelButton(f, f.increase_full_strength _)

        contents += new Label("SPEED")
        contents += new InformedLabel(f.speed _)
        contents += new LabelButton(f, f.increase_full_speed _){
          reactions += {
            case e:MouseClicked => timer.setDelay(1000/f.speed)
          }
        }

        contents += new Label("MANA")
        contents += new InformedLabel(f.mana _)
        contents += new LabelButton(f, f.increase_full_mana _)

        contents += new Label("MASS")
        contents += new InformedLabel(f.mass _)
        contents += new LabelButton(f, f.increase_full_mass _)
      }
    }
    contents += new ScrollPane() {
      verticalScrollBarPolicy = BarPolicy.AsNeeded
      horizontalScrollBarPolicy = BarPolicy.Never

      contents = new BoxPanel(Orientation.Vertical) {
        border = new LineBorder(Defaults.OCKER)
        contents += new Button("+"){
          listenTo(mouse.clicks)
          reactions += {
            case e:MouseClicked =>
               new TechniqueTreeMenu(f, parent)
          }
        }
        f.techniques.values.foreach {
          contents += new TechniqueComponent(_)
        }
      }
    }
  } -> BorderPanel.Position.Center


  /*TIMER THAT RUNS THE ANIMATION FOR THE CHARACTERS IMAGE IN THE MODIFYFIGHTERS COMPONENT*/
  val timer = new Timer(1000/f.speed, Swing.ActionListener{ _ =>
    f.images.next
    fighter_image.icon = new ImageIcon(f.image)
    repaint()
  })
  timer.start()

  def close() = {
    stopTimer()
    f.images.set(ImageMatrix.STANDING)
  }
  private def stopTimer() = if(timer.isRunning)timer.stop()
}
