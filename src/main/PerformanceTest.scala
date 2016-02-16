package main

import _root_.java.awt.Graphics2D
import javax.swing.UIManager
import com.sun.java.swing.plaf.motif.MotifLookAndFeel

import _root_.scala.swing.{Dimension, Panel, BorderPanel, MainFrame}

/**
 * Created by julian on 16.02.16.
 */
object PerformanceTest {

  def main(args:Array[String]): Unit ={
    var i =0
    val (x1, y1) = (0,0)
    val (x2, y2) = (400,300)

    val frame = new MainFrame{
      title = "performance test"
      centerOnScreen()
      contents = new BorderPanel{
        layout += new Panel{
          override def paint(g:Graphics2D): Unit ={
            g.drawLine(x1+i, y1,x2-i, y2)
          }
        } -> BorderPanel.Position.Center
        listenTo()
      }
      size = new Dimension(400, 300)
      visible = true
    }


    while(true){
      frame.repaint()
      frame.toolkit.sync()
      i += 1
      Thread.sleep(100)
    }
  }

}
