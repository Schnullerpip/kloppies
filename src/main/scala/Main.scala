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
  val FPS = 1000/20


  def main(args:Array[String]): Unit ={
    val fighters = Seq(Fighter("julian", "images/fighter_stickfigure.png", speed = 10))
    val players = Seq(Player(fighters.head))
    val map = new GameMap("images/backround_white.png", Seq())
    val controller = new Controller(players, map)
    lazy val timer = new Timer(200, Swing.ActionListener{ _ =>
      controller.cycle()
    })
    val view = new Arena(controller, timer)

    timer.start()
  }
}
