package main.scala.controller

import main.scala.model.GameObject
import main.scala.model.attributes.LivePoints
import main.scala.model.fighter.Fighter
import main.scala.model.items.magical.shock.ElectricSpark
import main.scala.model.map.GameMap
import main.scala.model.player.Player
import main.scala.model.states.OneHitWonder
import scala.annotation.tailrec
import main.scala.util.{Observer, Observable}

/**
 * Created by julian on 14.02.16.
 * Controller class, that works all the major steps like moving hitdetection etc.
 *
 */
case class Controller(players:Seq[Player], gameMap:GameMap) extends Observable with Observer{
  var animators = Seq[QueueAnimator]()
  players.zipWithIndex.foreach{pi => {
    pi._1.fighter.x = pi._2*100
    pi._1.fighter.addObserver(this)
    gameMap(pi._1.fighter)
  }}

  def init ={
    players.foreach { p =>
      animators = QueueAnimator(p.fighter, gameMap.elements) +: animators
    }
  }

  def cycle() = {
    //introduceView()
    gravityEffect(moveElements)
    hitDetection()
    notifyObservers()
    garbageCollector()
  }

  def shutDown(): Unit ={
    gameMap.elements = Seq()
  }

  @deprecated
  private def introduceView() = {
    gameMap.elements.foreach{go => rainDownObservers(go.images)}
  }

  private def garbageCollector():Boolean = {
    gameMap.elements = gameMap.elements.filter {case l: LivePoints => l.hp > 0 case _ => true}
    gameMap.elements.count{case f: Fighter => true case _ => false } > 1
  }

  private def moveElements = {
    val moveables = gameMap.elements
    moveables foreach{_.move}
    moveables
  }
  private def gravityEffect(gos:Seq[GameObject]) = {
   gos foreach{_.gravity_affect()}
   gos
  }

  private def hitDetection():Unit = hitDetection(gameMap.elements.filter(_.collidable))
  @tailrec
  private final def hitDetection(elements:Seq[GameObject]): Unit ={
    if(elements.size > 1) {
      elements.tail.foreach { e =>
        if (e.colliding(elements.head)) {
          e.state.actOnCollision(elements.head)
          elements.head.state.actOnCollision(e)
        }
      }
      hitDetection(elements.tail)
    }
  }

  override def update: Unit = cycle()

  override def update(go: GameObject): Unit = {
    gameMap(go)
    if(go.images != null){
      rainDownObservers(go.images)
      QueueAnimator(go, gameMap.elements)
    }
  }

  override def addObserver(obs:Observer) = {
    super.addObserver(obs)
    gameMap(obs)
  }
}
