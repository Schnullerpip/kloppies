package main.scala.view.gui.game

import java.awt.{Color, Graphics2D, RenderingHints}
import javax.swing.border.LineBorder

import main.scala.controller.Controller
import main.scala.model.GameObject
import main.scala.util.Observer
import main.scala.util.sound.SoundDistributer

import scala.swing._
import scala.swing.event.{KeyPressed, KeyReleased}

/**
 * Created by julian on 15.02.16.
 */
case class Arena(controller:Controller, timer:javax.swing.Timer) extends Observer{

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
          controller.gameMap.elements.reverse.foreach {
            case go if go.images != null =>
              g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
              g.setColor(new Color(0,0,0,0.30f))
              g.fillOval(go.x, go.y+go.height-10, go.width, 10)
              g.drawImage(go.image, go.x, go.y-go.z, null)
            case _ =>
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
      SoundDistributer.stop("fight_music")
      timer.stop()
      controller.shutDown()
      controller.animators.foreach{_.stopTimer()}
      super.closeOperation()
      System.exit(0)//TODO sollte auf Main.scala ausgelagert werden
    }
  }

  SoundDistributer.loop("fight_music")

  controller addObserver this
  controller init

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
