package main.scala.view.gui.game

import java.awt.{Color, Graphics2D}

import main.scala.model.fighter.Fighter

import scala.swing.{Dimension, Component}

/**
 * Created by julian on 20.02.16.
 */
case class FighterStatusBar(f:Fighter, getter:() => Int, color:Color) extends Component {
  private val full = getter()
  val width = 300
  preferredSize = new Dimension(width, 20)
  override def paint(g:Graphics2D) = {
    super.paint(g)
    g.setColor(Color.GRAY)
    g.fillRect(0, 0, width, 20)
    g.setColor(color)
    val value = (width*(getter()/full.toDouble)).toInt
    g.fillRect(0, 0, value, 20)
    g.setColor(Color.BLACK)
    g.drawString(s"${getter()}", width/2-5, 10)
    //println(s"${f.name}\t${getter()}\t$hp")
  }
}

