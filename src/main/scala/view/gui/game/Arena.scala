package main.scala.view.gui.game

import java.awt.{Color, Graphics2D}
import javax.swing.border.LineBorder

import main.scala.controller.Controller
import main.scala.model.GameObject
import main.scala.util.Observer

import scala.swing._
import scala.swing.event.{KeyPressed, KeyReleased}

/**
 * Created by julian on 15.02.16.
 */
case class Arena(controller:Controller, timer:javax.swing.Timer) extends Observer{
  controller.addObserver(this)

  val statusPanel = new FlowPanel{
    opaque = false
    focusable = false
    controller.players.foreach{ p =>
      val f = p.fighter
      contents += new GridPanel(3, 2){
        //preferredSize = new Dimension(350, 50)
        border = new LineBorder(Color.BLACK)
        contents += new Label(f.name)
        contents += new Component{}

        contents += new Label("HP")
        contents += FighterStatusBar(f, f.hp _, Color.RED)

        contents += new Label("MANA")
        contents += FighterStatusBar(f, f.mana _, Color.BLUE)
      }
    }
  }

  val gamePanel = new BorderPanel {
      layout += new Panel {
        override def paint(g: Graphics2D): Unit = {
          g.drawImage(controller.gameMap.backGround, 0, 0, null)
          controller.gameMap.elements.foreach { go =>
            g.drawImage(go.image, go.x, go.y-go.z, null)
          }
        }
      } -> BorderPanel.Position.Center

      layout += statusPanel -> BorderPanel.Position.South
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

  new Frame {
    title = "Fight!"
    centerOnScreen()
    contents = gamePanel
    maximize()
    visible = true
    override def closeOperation(): Unit ={
      timer.stop()
      controller.shutDown()
      controller.animators.foreach{_.stopTimer()}
      super.closeOperation()
      System.exit(0)//TODO sollte auf Main.scala ausgelagert werden
    }
  }

  override def update: Unit = {
    Swing.onEDT {
      gamePanel.repaint()
      gamePanel.toolkit.sync()
    }
  }
  override def update(go:GameObject): Unit = {
    Swing.onEDT{
      gamePanel.peer.repaint(go.x, go.y, go.width, go.height)
      gamePanel.toolkit.sync()
    }
  }
}
