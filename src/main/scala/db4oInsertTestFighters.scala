package main.scala

import main.scala.dao.db4o.DB4O
import main.scala.model.ImageMatrix
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.fire.ThrowFireball

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
  fighters.foreach{f => f.newTechnique(ThrowFireball(f), "defenddirectionattack")}
  fighters.foreach{dao store}
  dao close
}
