package main.scala

import javax.swing.Timer

import main.scala.controller.Controller
import main.scala.model.ImageMatrix
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

    ImageMatrix("images/fighters/fighter_stickfigure.png")
    ImageMatrix("images/items/magical/fireball.png", 6, 6, 50, 50)

    val fighters = Seq(
      Fighter("julian", "fighter_stickfigure.png", full_speed = 10, full_strength = 20),
      Fighter("kiki", "fighter_stickfigure.png", full_speed = 30, full_strength = 1)
    )
    fighters.foreach{f => f.newTechnique(ThrowFireball(f), "defenddirectionattack")}
    val players = Seq(
      Player(fighters.head),
      Player(fighters(1), keySet = KeySet('k', 'j', 'h', 'l', 'u', 'o', 'p'))
    )
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
