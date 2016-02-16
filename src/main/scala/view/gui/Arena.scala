package main.scala.view.gui

import java.awt.Graphics2D

import main.scala.controller.Controller
import main.scala.model.GameObject
import main.scala.util.Observer

import scala.swing._
import scala.swing.event.{KeyReleased, KeyPressed}

/**
 * Created by julian on 15.02.16.
 */
case class Arena(controller:Controller, timer:javax.swing.Timer) extends Observer{
  controller.addObserver(this)

  val textarea = new TextArea()
  var entries = 0
  val consolePanel = new BoxPanel(Orientation.Vertical){
    contents += textarea
  }

  val gamePanel = new BorderPanel {
      layout += new Panel {
        override def paint(g: Graphics2D): Unit = {
          g.drawImage(controller.gameMap.backGround, 0, 0, null)
          controller.gameMap.elements.foreach { go =>
            g.drawImage(go.image, go.x, go.y, null)
            textarea.append(s" current Image: ${go.images.image_coordinate.c.toString}\n")
            entries += 1
            if(entries > 40) {entries = 0; textarea.peer.setText("")}
          }
        }
      } -> BorderPanel.Position.Center
      layout += consolePanel -> BorderPanel.Position.West
      focusable = true
      requestFocus()
      listenTo(keys)
      reactions += {
        case e: KeyPressed =>
          controller.players.foreach { p => p.keySet.pressed(e.peer.getKeyChar, p.fighter) }
        case r: KeyReleased =>
          controller.players.foreach { p => p.keySet.released(r.peer.getKeyChar, p.fighter)}

      }
    }

  val frame = new Frame {
    title = "Fight!"
    centerOnScreen()
    contents = gamePanel
    maximize()
    visible = true
    override def closeOperation(): Unit ={
      timer.stop()
      super.closeOperation()
    }
  }

  override def update: Unit = {
    gamePanel.repaint()
    gamePanel.toolkit.sync()
  }
  override def update(go:GameObject): Unit = {
    gamePanel.peer.repaint(go.x, go.y, go.width, go.height)
    gamePanel.toolkit.sync()
  }
}
