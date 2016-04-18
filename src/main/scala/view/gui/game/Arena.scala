package main.scala.view.gui.game

import java.awt.{Color, Graphics2D, RenderingHints}
import javax.swing.border.LineBorder

import main.scala.controller.Controller
import main.scala.model.map.Stage
import main.scala.model.{GameObject, Position}
import main.scala.util.Observer
import main.scala.util.sound.SoundDistributor

import scala.swing._
import scala.swing.event.{KeyPressed, KeyReleased}

/**
 * Created by julian on 15.02.16.
 */
case class Arena(controller:Controller, timer:javax.swing.Timer) extends Observer{

  /*------------Component that shows the data for each Player's fighter-------------*/
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
  /*----------------------------------------------------------------------------------*/



  /*-----------The panel showing each GameObject and the Background-----*/
  val gamePanel = new BorderPanel {
      layout += new Panel {
        override def paint(g: Graphics2D): Unit = {
          g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
          val allElements:Seq[Position] = sort(controller.gameMap.elements ++: controller.gameMap.stages)
          allElements.foreach{
            case s:Stage if s.image.isDefined => g.drawImage(s.image.get, s.x, s.y, null)
            case s:Stage =>
              val col = g.getColor
              g.setColor(s.style.get.color)
              g.fillRect(s.x, s.y, s.width, s.height)
              g.setColor(col)
            case go:GameObject if go.images != null =>
              /*----draw shadow----*/
              g.setColor(new Color(0,0,0,0.30f))
              g.fillOval(go.x, go.y-(go.height*0.1).toInt, go.width, 10)
              /*-------------------*/
              g.drawImage(go.image, go.x, go.y-go.height-go.z, null)
            /*case go:GameObject => only for debugging invisible objects
              val col = g.getColor
              g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.30f))
              g.fillRect(go.x, go.y, go.width, go.width)
              g.setColor(col)*/
            case _ => }
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
  /*---------------------------------------------------------------------*/



  new Frame {
    title = "Fight Kloppies!"
    centerOnScreen()
    contents = gamePanel
    maximize()
    visible = true
    override def closeOperation(): Unit ={
      SoundDistributor.stop("fight_music")
      timer.stop()
      controller.shutDown()
      controller.animators.foreach{_.stopTimer()}
      super.closeOperation()
      System.exit(0)//TODO sollte auf Main.scala ausgelagert werden
    }
  }

  SoundDistributor.stopAll
  SoundDistributor.loop("fight_music")

  controller addObserver this
  controller init

  private def sort(seq:Seq[Position]):Seq[Position] = {
    if(seq.length < 2) seq
    else {
      val pivot = seq(seq.length/2)
      sort(seq.filter(pivot>)) ++ seq.filter(pivot===) ++ sort(seq.filter(pivot<))
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
