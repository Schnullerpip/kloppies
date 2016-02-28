package main.scala.view.gui.preparation.modemenu.pvp

import javax.swing.{Timer, ImageIcon}

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.Running
import main.scala.model.player.Player
import main.scala.view.gui.Defaults

import scala.swing._
import scala.swing.event.MouseClicked

/**
 * Created by julian on 25.02.16.
 */
class PvP_Mode(fighters:Seq[Fighter], frame:MainFrame) extends BorderPanel {
  val player_one_label = new Label("Player 1 CHOOSE FIGHTER!"){ font = Defaults.FONT }
  val player_two_label = new Label("Player 2 CHOOSE FIGHTER!"){ font = Defaults.FONT }
  var timers = Seq[Timer]()
  var players = Seq[Player]()
  fighters.foreach{f => f.state = Running(f)}

  layout += player_one_label -> BorderPanel.Position.North

  layout +=  new GridPanel(0, 6){
    fighters.foreach{f =>
      contents += new InformedButton(f.image _){
        reactions += {
          case e:MouseClicked =>
            players = Player(f) +: players
        }
      }
      val timer = new Timer(1000/f.speed, Swing.ActionListener{_ =>
        f.images.next
        revalidate()
        repaint()
      })
      timer.start()
      timers = timer +: timers
    }
  } -> BorderPanel.Position.Center


 private def close = timers.foreach{_ stop()}


}
