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
          val allElements:Seq[Position] = sort(controller.gameMap.elements)
          allElements.foreach{
            case go:Stage if go.images == null =>
              val col = g.getColor
              g.setColor(go.style.get.color)
              g.fillRect(go.x, go.y, go.width, go.length)
              g.setColor(col)
            case go:GameObject if go.images != null =>

              /*----draw shadow----*/
              if(go.drawsShadow){
                g.setColor(new Color(0,0,0,0.30f))
                g.fillOval(go.x, go.y-(go.height*0.2).toInt, go.width, go.width/3)
              }
              /*-------------------*/

              /*----draw Image-----------*/
              g.drawImage(go.image, go.x, go.y-go.height-go.z, null)
              /*-------------------------*/

              /*---for collision debugging---*/
              //drawCube(g, go, 0.3f)
              /*-----------------------------*/

              /*--for state debugging*/
              //g.drawString(go.state.toString, go.x, go.y-go.height-go.z)
              /*---------------------*/

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


  val mainPanel = new BorderPanel {
    layout += gamePanel -> BorderPanel.Position.Center
  }



  new Frame {
    peer.setUndecorated(true)
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

  private def drawCube(g:Graphics2D, go:GameObject, f:Float=0.2f) = {
    val(w, h, l) = (go.width, go.height, go.length)
    val x = go.x
    def y = go.y-go.z

    val angle = 90
    val w_part = Math.cos(angle) * l
    val h_part = Math.sin(angle) * l

    /*draw side facing horizon*/
    for(i <- x until (x + w)){
      g.setColor(new Color(0, 0, 1, f))
      g.drawLine(i+w_part, y-h_part, i+w_part, y+h-h_part)
    }

    /*draw bottom side*/
    for(i <- x until (x + w)){
      g.setColor(new Color(0, 1, 0, f))
      g.drawLine(i, y+h, i+w_part, y+h-h_part)
    }

    /*draw side facing viewer*/
    for(i <- x until (x + w)){
      g.setColor(new Color(0, 0, 1, f))
      g.drawLine(i, y, i, y+h)
    }

    /*draw upper side*/
    for(i <- x until (x + w)){
      g.setColor(new Color(0, 1, 0, f))
      g.drawLine(i, y, i+w_part, y-h_part)
    }

    implicit def dToi(d:Double):Int = d.toInt
  }
}
