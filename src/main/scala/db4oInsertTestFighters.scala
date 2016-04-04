package main.scala

import main.scala.dao.db4o.DB4O
import main.scala.model.ImageMatrix
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.fire.ThrowFireball
import main.scala.model.fighter.states.techniques.shock.SpeedPlus
import main.scala.model.fighter.states.techniques.wind.SummonWind

/**
 * Created by julian on 23.02.16.
 */
object db4oInsertTestFighters extends App {
  /*Load all the Images*/
  ImageMatrix("images/fighters/fighter_stickfigure.png")
  ImageMatrix("images/items/magical/fireball.png", 6, 6, 50, 50)

  val dao = new DB4O
  val fighters = Seq(
    Fighter("julian", "fighter_stickfigure.png", full_speed = 10, full_strength = 10, full_mana = 10, full_hp = 10, full_mass = 10),
    Fighter("kiki", "fighter_stickfigure.png", full_speed = 15, full_strength = 1)
  )

  fighters.head.newTechnique(SummonWind(fighters.head), "defenddirectionjump")
  fighters(1).newTechnique(SummonWind(fighters(1)), "defenddirectionjump")
  fighters(1).newTechnique(ThrowFireball(fighters(1)), "defenddirectionattack")
  fighters(1).newTechnique(SpeedPlus(fighters(1)), "defenddownjump")

  fighters.foreach{dao store}
  dao close
}
