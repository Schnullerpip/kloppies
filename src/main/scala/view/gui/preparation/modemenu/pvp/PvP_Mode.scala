package main.scala.view.gui.preparation.modemenu.pvp

import java.awt.Color
import javax.swing.border.LineBorder
import javax.swing.{Timer, ImageIcon}

import main.scala.controller.Controller
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.Running
import main.scala.model.map.{GameMap, Stage}
import main.scala.model.player.Player
import main.scala.view.gui.Defaults
import main.scala.view.gui.game.Arena

import scala.swing._
import scala.swing.event.MouseClicked

class PvP_Mode(fighters:Seq[Fighter], frame:MainFrame) extends BorderPanel {
  var timers = Seq[Timer]()
  var players = Seq[Player]()
  var nPlayers = 0
  val player_label = new Label(s"Player ${nPlayers +1} CHOOSE FIGHTER!"){ font = Defaults.FONT }

  fighters.foreach{f => f.state = Running(f)}

  layout += new BoxPanel(Orientation.Vertical){
    contents += new Label("Start"){
      peer.setFont(Defaults.FONT)
      border = new LineBorder(Color.BLACK)
      listenTo(mouse.clicks)
      reactions += {
        case e:MouseClicked if players.nonEmpty =>
          close()
          //frame.contents = new Arena() TODO game should not start new frame but frame's content should be replaced with an arena component
          val map = GameMap("images/backround_white.png", Seq(new Stage()))
          val controller = new Controller(players, map)
          def stopTimer():Unit = timer.stop()
          lazy val timer = new Timer(1000/25, Swing.ActionListener{ _ =>
            if(!controller.cycle())
              stopTimer()
          })
          new Arena(controller, timer)
          timer.start()
      }
    }
    contents += player_label
  } -> BorderPanel.Position.North

  layout +=  new GridPanel(0, 6){
    fighters.foreach{f =>
      contents += new Label(){
        icon = new ImageIcon(f.image)
        listenTo(mouse.clicks)
        reactions += {
          case e:MouseClicked =>
            players = Player(f) +: players
        }
      }
      val timer = new Timer(200, Swing.ActionListener { _ =>
        f.images.next
        Swing.onEDT {
          repaint()
        }
      })
      timer.start()
      timers = timer +: timers
    }
  } -> BorderPanel.Position.Center

 private def close() = timers.foreach{_ stop()}


}
