package main.scala.view.gui.preparation.modifyfighter

import main.scala.model.ImageMatrix
import java.awt._
import main.scala.model.fighter.Fighter
import main.scala.view.gui.Defaults
import scala.swing.{Font, MainFrame, Dimension, Component}

case class FighterGraph(f:Fighter, length:Int = 100) extends Component {
  val (width, height) = (length, length)
  val offset = 30
  preferredSize = new Dimension(width+offset, height+offset+10)
  //border = new LineBorder(Color.BLACK)
  opaque = false


  override def paint(g:Graphics2D) = {
    super.paint(g)

    val attributes = Seq(f.full_hp, f.full_strength, f.full_speed, f.full_mana, f.full_mass)
    val greatest = attributes.max
    val degree = 360/attributes.size
    def calcLength(attribute:Int):Double = {
      length/2 * attribute/greatest.toDouble
    }
    val unifiedAttributes = attributes.map{calcLength}
    def sin(n:Int) = Math.sin(45)
    def cos(n:Int) = Math.cos(45)

    val (p1, p2, p3, p4, p5) = ((length/2, length/2 - unifiedAttributes(0)+ offset),
      (length/2+cos(1)*unifiedAttributes(1), length/2 - sin(1)*unifiedAttributes(1)+ offset),
      (length/2+sin(2)*unifiedAttributes(2), length/2 + cos(2)*unifiedAttributes(2)+ offset),
      (length/2-sin(3)*unifiedAttributes(3), length/2 + cos(3)*unifiedAttributes(3)+ offset),
      (length/2-cos(4)*unifiedAttributes(4), length/2 - sin(4)*unifiedAttributes(4)+ offset))

    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(Defaults.OCKER)
    g.drawOval(0, 0+ offset, length, length)
    var ovalwidth = width/2
    g.drawOval(width/2-ovalwidth/2, width/2-ovalwidth/2+ offset, ovalwidth, ovalwidth)
    ovalwidth = width/8
    g.drawOval(width/2-ovalwidth/2, width/2-ovalwidth/2+ offset, ovalwidth, ovalwidth)
    g.setFont(new Font(g.getFont.getFontName, Font.PLAIN, 8))
    g.drawString(s"${greatest/2}", width/2, height/4*3+offset - 2)
    g.drawString(s"$greatest", width/2, height+offset-2)

    g.setColor(Defaults.REDDISH)
    g.setStroke(new BasicStroke(3))
    g.drawLine(p1._1, p1._2, p2._1, p2._2)
    g.drawLine(p2._1, p2._2, p3._1, p3._2)
    g.drawLine(p3._1, p3._2, p4._1, p4._2)
    g.drawLine(p4._1, p4._2, p5._1, p5._2)
    g.drawLine(p5._1, p5._2, p1._1, p1._2)

    g.setColor(Color.BLACK)
    g.setFont(new Font(g.getFont.getFontName, Font.PLAIN, 12))
    g.drawString("HP", p1._1-5, p1._2)
    g.drawString("ST", p2._1, p2._2)
    g.drawString("SP", p3._1, p3._2)
    g.drawString("MN", p4._1-10, p4._2)
    g.drawString("MS", p5._1-10, p5._2)

  }

  implicit def DtoInt(d:Double):Int = d.toInt
}

object FighterGraphTest extends App{
  ImageMatrix("images/fighters/fighter_stickfigure.png")
  ImageMatrix("images/items/magical/fireball.png", 6, 6, 50, 50)
  val f = Fighter("julian", "fighter_stickfigure.png")

  new MainFrame{
    contents = FighterGraph(f, 200)
    visible = true
  }

}
