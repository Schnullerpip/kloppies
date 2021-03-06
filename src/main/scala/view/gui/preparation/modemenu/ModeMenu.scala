package main.scala.view.gui.preparation.modemenu

import java.awt.{Color, Font}
import javax.swing.ImageIcon
import javax.swing.border.LineBorder

import main.scala.model.fighter.Fighter
import main.scala.view.gui.Defaults
import main.scala.view.gui.preparation.modemenu.pvp.PvP_Mode
import main.scala.view.gui.preparation.modifyfighter.ModifyFighter

import scala.swing.event.MouseClicked
import scala.swing._

/**
 * Created by julian on 23.02.16.
 */
class ModeMenu(fighters:Seq[Fighter], frame:MainFrame) extends BorderPanel{
  val selfMM = this
  var modifyFighter = ModifyFighter(fighters.head, this)
  layout += new GridPanel(1, 3){
    contents += new Label("PvP"){
      peer.setFont(Defaults.FONT)
      border = new LineBorder(Color.BLACK)
      listenTo(mouse.clicks)
      reactions += {case e:MouseClicked => defaultActionProcedure(frame.contents = new PvP_Mode(fighters, frame))}}

    contents += new Label("Campaign"){
      peer.setFont(Defaults.FONT)
      border = new LineBorder(Color.BLACK)
      listenTo(mouse.clicks)
      reactions += {case e:MouseClicked => defaultActionProcedure(println("campaign was chosen"))}}

    contents += new Label("Options"){
      peer.setFont(Defaults.FONT)
      border = new LineBorder(Color.BLACK)
      listenTo(mouse.clicks)
      reactions += {case e:MouseClicked => defaultActionProcedure(println("options was chosen"))}}

  } -> BorderPanel.Position.North
  
  layout += modifyFighter -> BorderPanel.Position.Center
  
  layout += new FlowPanel{
    fighters.foreach{
      f => contents += new BoxPanel(Orientation.Vertical){
        contents += new Label{
          icon = new ImageIcon(f.image)
        }
        contents += new Label(f.name)
        listenTo(mouse.clicks)
        reactions += {
          case e: MouseClicked =>
            layout += defaultActionProcedureModifyFighter(ModifyFighter(f, selfMM)) -> BorderPanel.Position.Center
            revalidate()
            repaint()
        }
      }
    }
  } -> BorderPanel.Position.South

  def defaultActionProcedureModifyFighter(mf:ModifyFighter)={
    defaultActionProcedure()
    modifyFighter = mf
    revalidate()
    frame.repaint()
    mf
  }
  private def defaultActionProcedure[T](b: => T) = {
    modifyFighter.close()
    b
    frame.repaint()
  }
}
