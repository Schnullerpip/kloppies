package main.scala.controller

import main.scala.model.GameObject
import main.scala.model.attributes.LivePoints
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.FighterState
import main.scala.model.map.GameMap
import main.scala.model.player.Player
import main.scala.model.states.MidAir

import scala.annotation.tailrec
import main.scala.util.{Observable, Observer}

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
   // gameMap.fighters.foreach{case f:Fighter if f.name == "julian" => println(f.name + "\t" + f.state + "\t" + f.images.image_coordinate.c)
   // case _ =>}
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

  private val GRAVITY_CONSTANT = 1
  /**
    * instead of letting the GameObjects decide what to do in case of gravityEffect now the Controller has the responsibility to
    * manipulate the GameObjects velocities and to check for Ground Contact*/
  private def gravityEffect(gos:Seq[GameObject]) = {
    /*gos foreach{_.gravity_affect()}  OLD WAY*/

    /*NEW WAY*/
    gos foreach{go =>
      go.gravity_affect(GRAVITY_CONSTANT)
      if(go.gravity_affected){
        go.state match {
          case ma:MidAir if go.z_velocity > 0 => go.z += go.z_velocity
          case ma:MidAir =>
            val stages = gameMap.stages.filter(s => s.z <= go.z && s.z >= go.z+go.z_velocity)
            if(stages isEmpty)
              go.z += go.z_velocity
            else{
              val iterator = stages.iterator
              while (iterator.hasNext && go.z_velocity < 0) {
                val s = iterator.next()
                if ((go.x >= s.x && go.x <= s.x + s.width) || (go.x <= s.x && go.x + go.width >= s.x)) {
                  if ((go.y >= s.y && go.y <= s.y + s.width) || (go.y <= s.y && go.y + go.width >= s.y)) {
                    go.z = s.z
                    go.z_velocity = 0
                    go.state.landing //asInstanceOf[FighterState].landing
                  }
                }
              }
            }
          case _ =>
        }
      }
    }
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
