package main.scala

import javax.swing.Timer

import main.scala.controller.Controller
import main.scala.model.map.{Stage, GameMap}
import main.scala.model.fighter.Fighter
import main.scala.model.player.Player
import main.scala.view.gui.Arena
import main.scala.controller.GAME_SPEED

import scala.swing.Swing

/**
 * Created by julian on 15.02.16.
 * Main
 */
object Main {

  def main(args:Array[String]): Unit ={
    val fighters = Seq(Fighter("julian", "images/fighter_stickfigure.png", speed = 6))
    val players = Seq(Player(fighters.head))
    val map = GameMap("images/backround_white.png", Seq(new Stage()))
    val controller = new Controller(players, map)
    lazy val timer = new Timer(GAME_SPEED, Swing.ActionListener{ _ =>
      controller.cycle()
    })

    new Arena(controller, timer)

    timer.start()
  }
}
