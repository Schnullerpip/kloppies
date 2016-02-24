package main.scala.view.gui.preparation.modemenu

import java.awt.{Color, Font}
import javax.swing.ImageIcon
import javax.swing.border.LineBorder

import main.scala.model.fighter.Fighter
import main.scala.view.gui.preparation.modifyfighter.ModifyFighter

import scala.swing.event.MouseClicked
import scala.swing._

/**
 * Created by julian on 23.02.16.
 */
class ModeMenu(fighters:Seq[Fighter]) extends BorderPanel{
  var modifyFighterInstance = ModifyFighter(fighters.head)
  layout += new GridPanel(1, 3){
    contents += new Label("PvP"){
      peer.setFont(ModeMenu.font)
      border = new LineBorder(Color.BLACK)
      listenTo(mouse.clicks)
      reactions += {case e:MouseClicked => defaultActionProcedure(println("PvP was chosen"))}}

    contents += new Label("Campaign"){
      peer.setFont(ModeMenu.font)
      border = new LineBorder(Color.BLACK)
      listenTo(mouse.clicks)
      reactions += {case e:MouseClicked => defaultActionProcedure(println("campaign was chosen"))}}

    contents += new Label("Options"){
      peer.setFont(ModeMenu.font)
      border = new LineBorder(Color.BLACK)
      listenTo(mouse.clicks)
      reactions += {case e:MouseClicked => defaultActionProcedure(println("options was chosen"))}}

  } -> BorderPanel.Position.North
  
  layout += modifyFighterInstance -> BorderPanel.Position.Center
  
  layout += new FlowPanel{
    fighters.foreach{
      f => contents += new Label{
        icon = new ImageIcon(f.image)
        listenTo(mouse.clicks)
        reactions += {
          case e: MouseClicked =>
            layout += defaultActionProcedureModifyFighter(ModifyFighter(f)) -> BorderPanel.Position.Center
            revalidate()
            repaint()
        }
      }
    }
  } -> BorderPanel.Position.South

  private def defaultActionProcedureModifyFighter(mf:ModifyFighter)={
    defaultActionProcedure()
    modifyFighterInstance = mf
    mf
  }
  private def defaultActionProcedure[T](b: => T) = {
    modifyFighterInstance.close
  }
}

object ModeMenu {
  val font = new Font(Font.SANS_SERIF, Font.PLAIN, 25)
}
