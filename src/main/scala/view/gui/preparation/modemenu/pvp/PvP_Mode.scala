package main.scala.view.gui.preparation.modemenu.pvp

import java.awt.Color
import javax.swing.border.LineBorder
import javax.swing.Timer

import main.scala.controller.Controller
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.Running
import main.scala.model.map.{GameMap, Stage}
import main.scala.model.player.{KeySet, Player}
import main.scala.view.gui.Defaults
import main.scala.view.gui.game.Arena

import scala.swing._
import scala.swing.event.MouseClicked

class PvP_Mode(fighters:Seq[Fighter], frame:MainFrame) extends BorderPanel {
  var timers = Seq[Timer]()
  var players = Seq[Player]()
  var nPlayer = 0
  var keySet = KeySet()
  var fightersGrid = new InformedFightersGrid(fighters)

  /*------------The Textfields which hold the keyset-------------------------*/
  val up      = new TextField(keySet.up.toString) {columns = 2}
  val down    = new TextField(keySet.down.toString) {columns = 2}
  val left    = new TextField(keySet.left.toString) {columns = 2}
  val right   = new TextField(keySet.right.toString) {columns = 2}
  val attack  = new TextField(keySet.attack.toString) {columns = 2}
  val defense = new TextField(keySet.defense.toString) {columns = 2}
  val jump    = new TextField(keySet.jump.toString) {columns = 2}
  /*-------------------------------------------------------------------------*/

  var selectionPanel = new BorderPanel() {

    /*The Keyset modification panel with labels and textfields representing the keyset*/
    layout += new GridPanel(2, 1) {
      border = new LineBorder(Color.BLACK)
      contents += new Label("KeySet")
      contents += new GridPanel(0, 2) {
        contents += new BorderPanel() { layout += new Label("Up") -> BorderPanel.Position.East }
        contents += new BorderPanel() { layout += up -> BorderPanel.Position.West }
        contents += new BorderPanel() { layout += new Label("Down") -> BorderPanel.Position.East }
        contents += new BorderPanel() { layout += down -> BorderPanel.Position.West }
        contents += new BorderPanel() { layout += new Label("Left") -> BorderPanel.Position.East }
        contents += new BorderPanel() { layout += left -> BorderPanel.Position.West }
        contents += new BorderPanel() { layout += new Label("Right") -> BorderPanel.Position.East }
        contents += new BorderPanel() { layout += right -> BorderPanel.Position.West }
        contents += new BorderPanel() { layout += new Label("Attack") -> BorderPanel.Position.East }
        contents += new BorderPanel() { layout += attack -> BorderPanel.Position.West }
        contents += new BorderPanel() { layout += new Label("Jump") -> BorderPanel.Position.East }
        contents += new BorderPanel() { layout += jump -> BorderPanel.Position.West }
        contents += new BorderPanel() { layout += new Label("Defense") -> BorderPanel.Position.East }
        contents += new BorderPanel() { layout += defense -> BorderPanel.Position.West }
      }
    } -> BorderPanel.Position.North


    /*The Panel showing all the Fighters*/
    layout += new GridPanel(2, 1) {
      border = new LineBorder(Color.BLACK)
      contents += new Label("Fighters")
      contents += fightersGrid
    } -> BorderPanel.Position.Center
  }

  val player_label = new Label(s"Player ${nPlayer +1}"){
    font = Defaults.FONT
    listenTo(mouse.clicks)
    reactions += {
      case e:MouseClicked =>
        if(fightersGrid.fighter isDefined) {
          nextPlayer_and_start_routine
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

  fighters.foreach{f => f.state = Running(f)}

  layout += new GridPanel(2,1){
      contents += new Label("Start") {
        peer.setFont(Defaults.FONT)
        border = new LineBorder(Color.BLACK)
        listenTo(mouse.clicks)
        reactions += {
          case e: MouseClicked if players.nonEmpty =>
            close()
            nextPlayer_and_start_routine
            //frame.contents = new Arena() TODO game should not start new frame but frame's content should be replaced with an arena component
            val map = GameMap("images/backround_white.png", Seq(new Stage()))
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
    contents += player_label
  } -> BorderPanel.Position.North

  layout += selectionPanel -> BorderPanel.Position.Center

  private def close() = timers.foreach{_ stop()}

  private def nextPlayer_and_start_routine = {
    /*------ generating keyset ----------*/
      keySet = KeySet(up, down, left, right, attack, defense, jump)
    /*---------------------------------*/

    /*------adding new player to players list---------*/
    players = Player(fightersGrid.fighter.get, keySet) +: players
    /*------------------------------------------------*/
  }

  private implicit def textfieldToChar(t:TextField):Char = t.text.charAt(0)
}
