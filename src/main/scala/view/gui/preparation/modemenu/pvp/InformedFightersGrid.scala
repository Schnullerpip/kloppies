package main.scala.view.gui.preparation.modemenu.pvp

import javax.swing.{Timer, ImageIcon}
import main.scala.model.fighter.Fighter
import scala.swing.event.{MouseEntered, MouseClicked}
import scala.swing._

class InformedFightersGrid(f:Seq[Fighter]) extends GridPanel(0, 6){
  private val fighters = f
  var fighter:Option[Fighter] = if(f isEmpty) None else Some(fighters.head)
  var timers = Seq[Timer]()
  def drop = {
    timers foreach {_ stop()}
    if(fighter isDefined)
      fighters filter (_ != fighter.get)
    else
      Seq()
  }
  private def fightersView() = fighters.foreach { f =>
    val l = new Label() {
      icon = new ImageIcon(f.image)
      listenTo(mouse.clicks, mouse.moves)
      reactions += {
        case e: MouseClicked =>
          fighter = Some(f)
        case e: MouseEntered => //TODO little speachbubble with fighters name
      }
    }

    /*-----------The fighters image and its name--------*/
    contents += new BoxPanel(Orientation.Vertical){
      contents += l
      contents += new Label(f.name)
    }
    /*--------------------------------------------------*/

    /*the timer. that will move the fighters image in the PvP_Mode Menu*/
    val timer = new Timer(1000 / f.speed, Swing.ActionListener { _ =>
      f.images.next
      l.icon = new ImageIcon(f.image)
      Swing.onEDT {
        repaint()
        toolkit.sync()
      }
    })
    timer.start()
    timers = timer +: timers
  }

  fightersView()

  override def paint(g:Graphics2D): Unit ={
    super.paint(g)
  }

}
