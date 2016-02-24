package main.scala.view.gui.preparation.modifyfighter

import java.awt.Font
import javax.swing.{Timer, ImageIcon}

import main.scala.model.ImageMatrix
import main.scala.model.fighter.Fighter

import scala.swing._
import scala.swing.event.MouseClicked

/**
 * Created by julian on 23.02.16.
 */
case class ModifyFighter(f:Fighter) extends BorderPanel{
  val fighter_image = new Label{
    f.images.set(ImageMatrix.RUNNING)
    icon = new ImageIcon(f.image)
  }
  preferredSize = new Dimension(150, 150)
  val f_name = new Label(s"${f.name}")
  f_name.peer.setFont(new Font(f_name.peer.getFont.getName, Font.PLAIN, 25))
  layout += new BoxPanel(Orientation.Vertical){
    contents += new FlowPanel{ contents += f_name }
    contents += new FlowPanel{
      contents += fighter_image
    }
  } -> BorderPanel.Position.North

  layout += new FlowPanel{
    contents += new BoxPanel(Orientation.Vertical){
      contents += new BorderPanel(){add(new Label("HP"), BorderPanel.Position.Center)}
      contents += new BorderPanel(){add(new Label("STRENGTH"), BorderPanel.Position.Center)}
      contents += new BorderPanel(){add(new Label("SPEED"), BorderPanel.Position.Center)}
      contents += new BorderPanel(){add(new Label("MANA"), BorderPanel.Position.Center)}
      contents += new BorderPanel(){add(new Label("MASS"), BorderPanel.Position.Center)}}
    contents += new BoxPanel(Orientation.Vertical) {
      contents += new BorderPanel() {
        add(new InformedLabel(f.hp _), BorderPanel.Position.Center)
        add(new Button("+") {
          listenTo(mouse.clicks)
          reactions += { case e: MouseClicked => ifEnoughXP {
            f.increase_full_hp
          }; revalidate(); repaint()
          }
        }, BorderPanel.Position.East)
      }
      contents += new BorderPanel() {
        add(new InformedLabel(f.strength _), BorderPanel.Position.Center)
        add(new Button("+") {
          listenTo(mouse.clicks)
          reactions += { case e: MouseClicked => ifEnoughXP {
            f.increase_full_strength
          }; revalidate(); repaint()
          }
        }, BorderPanel.Position.East)
      }
      contents += new BorderPanel() {
        add(new InformedLabel(f.speed _), BorderPanel.Position.Center)
        add(new Button("+") {
          listenTo(mouse.clicks)
          reactions += { case e: MouseClicked => ifEnoughXP {
            f.increase_full_speed
          }; revalidate(); repaint()
          }
        }, BorderPanel.Position.East)
      }
      contents += new BorderPanel() {
        add(new InformedLabel(f.mana _), BorderPanel.Position.Center)
        add(new Button("+") {
          listenTo(mouse.clicks)
          reactions += { case e: MouseClicked => ifEnoughXP {
            f.increase_full_mana
          }; revalidate(); repaint()
          }
        }, BorderPanel.Position.East)
      }
      contents += new BorderPanel() {
        add(new InformedLabel(f.mass _), BorderPanel.Position.Center)
        add(new Button("+") {
          listenTo(mouse.clicks)
          reactions += { case e: MouseClicked => ifEnoughXP {
            f.increase_full_mass
          }; revalidate(); repaint()
          }
        }, BorderPanel.Position.East)
      }
    }
    contents += FighterGraph(f)
  } -> BorderPanel.Position.Center

  val timer = new Timer(1000/f.speed, Swing.ActionListener{ _ =>
    f.name + "\t" + f.images.next
    fighter_image.icon = new ImageIcon(f.image)
    revalidate()
    repaint()
  })
  timer.start()

  def close = {
    stopTimer
    f.images.set(ImageMatrix.STANDING)
  }
  private def stopTimer = if(timer.isRunning)timer.stop()
  private def ifEnoughXP(b: => Unit): Unit ={
    if(/*f.xp >= 100*/true)b
  }
}
