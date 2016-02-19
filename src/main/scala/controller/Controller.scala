package main.scala.controller

import main.scala.model.GameObject
import main.scala.model.map.GameMap
import main.scala.model.player.Player
import scala.annotation.tailrec
import main.scala.util.Observable

/**
 * Created by julian on 14.02.16.
 * Controller class, that works all the major steps like moving hitdetection etc.
 *
 */
case class Controller(players:Seq[Player], gameMap:GameMap) extends Observable{
  var animators = Seq[QueueAnimator]()
  players.foreach{p => {
    gameMap(p.fighter)
    animators = QueueAnimator(p.fighter, gameMap.elements) +: animators
  }}

  def cycle() = {
    introduceView()
    gravityEffect(moveElements)
    hitDetection()
    notifyObservers()
  }

  def shutDown(): Unit ={
    gameMap.elements = Seq()
  }

  private def introduceView() = {
    gameMap.elements.foreach{go => rainDownObservers(go.images)}
  }

  private def moveElements = {
    val moveables = gameMap.elements
    moveables.foreach{_.move}
    moveables
  }
  private def gravityEffect(gos:Seq[GameObject]) = {
   gos.foreach{_.gravity_affect()}
   gos
  }

  private def hitDetection():Unit = hitDetection(gameMap.elements)
  @tailrec
  private final def hitDetection(elements:Seq[GameObject]): Unit ={
    if(elements nonEmpty) {
      elements.tail.foreach { e => if (e.colliding(elements.head)) e.state.actOnCollision(elements.head) }
      hitDetection(elements.tail)

}
  }

}
