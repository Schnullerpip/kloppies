package main.scala.view.gui.preparation.modifyfighter

import java.awt.Graphics2D

import scala.swing.Label

/**
 * Created by julian on 24.02.16.
 */
class InformedLabel(b:() => Int) extends Label(b().toString) {
  override def paint(g:Graphics2D) = {
    text = b().toString
    super.paint(g)
  }
}
