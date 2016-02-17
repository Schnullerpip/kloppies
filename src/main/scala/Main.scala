package main.scala

import javax.swing.Timer

import main.scala.controller.Controller
import main.scala.model.map.GameMap
import main.scala.model.{Size, ImageMatrix}
import main.scala.model.fighter.Fighter
import main.scala.model.player.{KeySet, Player}
import main.scala.view.gui.Arena

import scala.swing.Swing

/**
 * Created by julian on 15.02.16.
 * Main
 */
object Main {
  val GAME_SPEED = 1000/40

  def main(args:Array[String]): Unit ={
    val fighters = Seq(Fighter("julian", "images/fighter_stickfigure.png", speed = 1))
    val players = Seq(Player(fighters.head))
    val map = new GameMap("images/backround_white.png", Seq())
    val controller = new Controller(players, map)
    lazy val timer = new Timer(GAME_SPEED, Swing.ActionListener{ _ =>
      controller.cycle()
    })

    new Arena(controller, timer)

    timer.start()
  }
}
