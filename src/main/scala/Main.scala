package main.scala

import javax.swing.Timer

import main.scala.controller.Controller
import main.scala.model.fighter.states.techniques.fire.ThrowFireball
import main.scala.model.map.{Stage, GameMap}
import main.scala.model.fighter.Fighter
import main.scala.model.player.{KeySet, Player}
import main.scala.controller.GAME_SPEED
import main.scala.view.gui.game.Arena

import scala.swing.Swing

/**
 * Created by julian on 15.02.16.
 * Main
 */
object Main {

  def main(args:Array[String]): Unit ={
    val fighters = Seq(
      Fighter("julian", "images/fighters/fighter_stickfigure.png", speed = 10, strength = 20),
      Fighter("kiki", "images/fighters/fighter_stickfigure.png", speed = 30, strength = 1)
    )
    val players = Seq(
      Player(fighters.head),
      Player(fighters(1), keySet = KeySet('k', 'j', 'h', 'l', 'u', 'o', 'p'))
    )
    val map = GameMap("images/backround_white.png", Seq(new Stage()))
    val controller = new Controller(players, map)
    /*initialize techniques for fighters*/
    controller.gameMap.fighters.foreach{_.techniques.put("defenddirectionattack", ThrowFireball(controller))}



    lazy val timer = new Timer(GAME_SPEED, Swing.ActionListener{ _ =>
      if(controller.cycle())
        stopTimer()
    })

    def stopTimer() = timer.stop()

    new Arena(controller, timer)

    timer.start()
  }
}
