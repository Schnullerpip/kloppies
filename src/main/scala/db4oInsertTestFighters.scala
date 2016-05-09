package main.scala

import java.io.File

import main.scala.dao.db4o.DB4O
import main.scala.model.ImageMatrix
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.fire.{DropFireMine, ThrowFireball}
import main.scala.model.fighter.states.techniques.shock.SpeedPlus
import main.scala.model.fighter.states.techniques.wind.SummonWind
import main.scala.dao.DEFAULT_DATABASE_NAME
import main.scala.model.fighter.states.techniques.earth.{RockThrow, StoneRain, StoneThrow}

/**
 * Created by julian on 23.02.16.
  * Testclass solely for inserting some default Fighter in to the game
 */
object db4oInsertTestFighters extends App {
  new File(DEFAULT_DATABASE_NAME).delete()

  /*Load all the Images*/
  ImageMatrix("images/fighters/fighter_stickfigure.png")
  ImageMatrix("images/items/magical/fireball.png", 6, 6, 50, 50)

  val dao = new DB4O
  val fighters = Seq(
    Fighter("julian", "fighter_stickfigure.png", fighter_speed = 10, fighter_strength = 10, fighter_mana = 100, fighter_mass = 11),
    Fighter("kiki", "fighter_stickfigure.png", fighter_speed = 10, fighter_strength = 10, fighter_mana = 100, fighter_mass = 11)
  )

  fighters.head.newTechnique(SummonWind(fighters.head), "defenddirectionjump")
  fighters.head.newTechnique(StoneRain(fighters.head), "defenddefenddefend")
  fighters.head.newTechnique(SummonWind(fighters.head), "defenddirectionjump")
  fighters.head.newTechnique(ThrowFireball(fighters.head), "defenddirectionattack")
  fighters.head.newTechnique(RockThrow(fighters.head), "defenddefenddown")
  fighters.head.newTechnique(DropFireMine(fighters.head), "defenddefendjump")

  fighters(1).newTechnique(SummonWind(fighters(1)), "defenddirectionjump")
  fighters(1).newTechnique(ThrowFireball(fighters(1)), "defenddirectionattack")
  fighters(1).newTechnique(SpeedPlus(fighters(1)), "defenddownjump")
  fighters(1).newTechnique(StoneThrow(fighters(1)), "defenddefenddefend")
  fighters(1).newTechnique(RockThrow(fighters(1)), "defenddefenddown")
  fighters(1).newTechnique(DropFireMine(fighters(1)), "defenddefendjump")

  fighters foreach{dao store}
  dao close
}
