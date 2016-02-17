package main.scala

import _root_.java.awt.Graphics2D
import java.io.File
import javax.imageio.ImageIO
import javax.swing.Timer

import scala.swing._

/**
 * Created by julian on 16.02.16.
 */
object PerformanceTest {

  def main(args: Array[String]): Unit = {
    val image = ImageIO.read(new File("images/Fighter1.png"))
    var i = 0
    val (x1, y1) = (0, 0)
    val (x2, y2) = (400, 300)

    val frame = new MainFrame {
      title = "performance test"
      centerOnScreen()
      contents = new BorderPanel {
        layout += new Panel {
          override def paint(g: Graphics2D): Unit = {
            //g.drawLine(x1+i, y1,x2-i, y2)
            g.drawImage(image, x1 + i, y1, null)
          }
        } -> BorderPanel.Position.Center
        listenTo()
      }
      size = new Dimension(400, 300)
      visible = true
    }

    new Timer(100, Swing.ActionListener { _ =>
      i += 5
      frame.repaint()
      frame.toolkit.sync()
    }).start()
  }
}
