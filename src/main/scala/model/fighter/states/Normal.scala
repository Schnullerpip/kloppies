package main.scala.model.fighter.states

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.aggressive.StandardAttack
import main.scala.model.states.AnimateMe
import main.scala.model.ImageMatrix.{STANDING, STANDING_HIT}

/**
 * Created by julian on 14.02.16.
 * The normal State of a Fighter. The current Image always iterates through the imageMatrix for generating an Animation -> prevents static feeling cause
 * Fighter is always moving
 */
case class Normal(f:Fighter) extends FighterState(f) with AnimateMe{
  f.images.set(STANDING)
  override def hit = f.state = StandardAttack(f)

  override def moveUp =   { super.moveUp;   f.state = Running(f)}
  override def moveDown = { super.moveDown; f.state = Running(f)}
  override def moveLeft = { super.moveLeft; f.looksLeft = true; f.state = Running(f)}
  override def moveRight ={ super.moveRight;f.looksLeft = false; f.state = Running(f)}

}
