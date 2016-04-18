package main.scala.view.gui.preparation.modemenu.pvp

import java.awt.Color
import java.io.File
import javax.imageio.ImageIO
import javax.swing.border.LineBorder
import javax.swing.Timer

import main.scala.controller.Controller
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{Normal, Running}
import main.scala.model.items.normal.{Stone, StoneFalling}
import main.scala.model.map.{GameMap, Stage}
import main.scala.model.player.{KeySet, Player}
import main.scala.view.gui.Defaults
import main.scala.view.gui.game.Arena
import main.scala.view.gui.preparation.KeySetMenu

import scala.swing._
import scala.swing.event.MouseClicked

class PvP_Mode(fighters:Seq[Fighter], frame:MainFrame) extends BorderPanel {
  var players = Seq[Player]()
  var nPlayer = 0
  var fightersGrid = new InformedFightersGrid(fighters)
  val keySetMenu = new KeySetMenu()


  var selectionPanel = new BorderPanel() {

    /*The Keyset modification panel with labels and textfields representing the keyset*/
    layout += keySetMenu -> BorderPanel.Position.North


    /*The Panel showing all the Fighters*/
    layout += new GridPanel(2, 1) {
      border = new LineBorder(Color.BLACK)
      contents += new Label("Fighters")
      contents += fightersGrid
    } -> BorderPanel.Position.Center
  }

  /*Buttonlike Label serving as 'next Player'-function saving a players configurations and switching to the next one*/
  val player_label = new Label(s"Player ${nPlayer +1}"){
    font = Defaults.FONT
    listenTo(mouse.clicks)
    reactions += {
      case e:MouseClicked =>
        if(fightersGrid.fighter.isDefined) {
          nextPlayer_and_start_routine
          keySetMenu.next //triggers new default keyset
          if(players.size < fighters.size) {
            nPlayer += 1
            fightersGrid = new InformedFightersGrid(fightersGrid.drop)
            selectionPanel.layout += new GridPanel(2, 1) {
              border = new LineBorder(Color.BLACK)
              contents += new Label("Fighters")
              contents += fightersGrid
            } -> BorderPanel.Position.Center
            text = s"Player ${nPlayer + 1}"
            revalidate()
            repaint()
          }
        }
    }
  }

  fighters.foreach{f => f.state = Running(f)}

  layout += new GridPanel(2,1){
      contents += new Label("Start") {
        peer.setFont(Defaults.FONT)
        border = new LineBorder(Color.BLACK)
        listenTo(mouse.clicks)
        reactions += {
          case e: MouseClicked if players.nonEmpty =>
            if(players.nonEmpty) {
              close()
              fighters foreach { f => f.state = Normal(f) }
              nextPlayer_and_start_routine
              //frame.contents = new Arena() TODO game should not start new frame but frame's content should be replaced with an arena component
              val map = GameMap(ImageIO.read(new File("images/backround_white.png")))
              val controller = new Controller(players, map)
              def stopTimer(): Unit = timer.stop()
              lazy val timer = new Timer(1000 / 25, Swing.ActionListener { _ =>
                if (!controller.cycle())
                  stopTimer()
              })
              new Arena(controller, timer)
              timer.start()
            }
        }
      }
    contents += player_label
  } -> BorderPanel.Position.North

  layout += selectionPanel -> BorderPanel.Position.Center

  private def close() = fightersGrid.drop

  private def nextPlayer_and_start_routine = {
    /*------adding new player to players list---------*/
    players = Player(fightersGrid.fighter.get, keySetMenu.get) +: players
    /*------------------------------------------------*/
  }

}
