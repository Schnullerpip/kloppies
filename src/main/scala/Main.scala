package main.scala

import javax.swing.Timer

import main.scala.controller.Controller
import main.scala.dao.db4o.DB4O
import main.scala.model.ImageMatrix
import main.scala.model.fighter.states.techniques.fire.ThrowFireball
import main.scala.model.map.{Stage, GameMap}
import main.scala.model.fighter.Fighter
import main.scala.model.player.{KeySet, Player}
import main.scala.controller.GAME_SPEED
import main.scala.view.gui.game.Arena
import main.scala.view.gui.preparation.modemenu.ModeMenu

import scala.swing.{MainFrame, Swing}

/**
 * Created by julian on 15.02.16.
 * Main
 */
object Main {

  def main(args:Array[String]): Unit ={
    /*initialize DAO*/
    val dao = new DB4O

    /*Load all the Images*/
    ImageMatrix("images/fighters/fighter_stickfigure.png")

    /*Get the Fighters*/
    val fighters = dao.query

    /*declare GameMode*/
    val frame:MainFrame = new MainFrame{
      title = "Kloppies"
      centerOnScreen()
      maximize()
    }
    frame.contents = new ModeMenu(fighters, frame)
    frame.visible = true

    /*shutdown application*/
    dao.close
    return
    val players = Seq(
      Player(fighters.head),
      Player(fighters(1), keySet = KeySet('k', 'j', 'h', 'l', 'u', 'o', 'p'))
    )

    /**/
    val map = GameMap("images/backround_white.png", Seq(new Stage()))
    val controller = new Controller(players, map)



    lazy val timer = new Timer(GAME_SPEED, Swing.ActionListener{ _ =>
      if(!controller.cycle())
        stopTimer()
    })

    def stopTimer():Unit = timer.stop()

    new Arena(controller, timer)

    timer.start()
  }
}
