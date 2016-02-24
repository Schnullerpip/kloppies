package main.scala.view.gui.preparation.modifyfighter

import java.awt.Color
import javax.swing.border.LineBorder

import main.scala.model.fighter.Fighter

import scala.swing.{Dimension, Label}
import scala.swing.event.MouseClicked

/**
 * Created by julian on 24.02.16.
 */
class LabelButton(f:Fighter, b:() => Unit) extends Label("+") {
  preferredSize = new Dimension(50, 25)
  maximumSize = preferredSize
  border = new LineBorder(Color.BLACK)
  listenTo(mouse.clicks)
  reactions += {
    case e: MouseClicked =>
      ifEnoughXP { b() }
      revalidate()
      repaint()
  }
  private def ifEnoughXP(b: => Unit): Unit ={
    if(/*f.xp >= 100*/true)b //TODO
  }

}
