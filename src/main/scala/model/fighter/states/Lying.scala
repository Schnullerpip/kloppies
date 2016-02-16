package main.scala.model.fighter.states

import main.scala.model.fighter.Fighter
import main.scala.model.ImageMatrix._
import main.scala.model.states.AnimateMe

/**
 * Created by julian on 14.02.16.
 */
case class Lying(f:Fighter) extends FighterState(f) with AnimateMe{
  f.images.set(LYING)
  override def hit = {}
  override def moveUp =   {}
  override def moveDown = {}
  override def moveLeft = {}
  override def moveRight ={}
}
