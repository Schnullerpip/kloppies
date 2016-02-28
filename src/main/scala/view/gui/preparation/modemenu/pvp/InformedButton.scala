package main.scala.view.gui.preparation.modemenu.pvp

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.ImageIcon

import scala.swing.Button

/**
 * Created by julian on 25.02.16.
 */
class InformedButton(b:() => BufferedImage) extends Button {
  listenTo(mouse.clicks)
  override def paint(g:Graphics2D): Unit ={
    icon = new ImageIcon(b())
    super.paint(g)
  }
}
