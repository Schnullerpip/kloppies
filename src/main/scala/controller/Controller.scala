package main.scala.controller

import main.scala.model.GameObject
import main.scala.model.attributes.LivePoints
import main.scala.model.fighter.Fighter
import main.scala.model.map.{GameMap, Stage}
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
    pi._1.fighter.y = pi._2*100+200
    pi._1.fighter.addObserver(this)
    gameMap(pi._1.fighter)
  }}

  def init ={
    players.foreach { p =>
      animators = QueueAnimator(p.fighter, gameMap.elements) +: animators
    }
  }

  def cycle() = {
    gravityEffect(moveElements)
    notifyObservers()
    garbageCollector()
  }

  def shutDown(): Unit ={
    gameMap.elements = Seq()
  }

  private def garbageCollector():Boolean = {
    gameMap.elements = gameMap.elements.filter {case l: LivePoints => l.hp > 0 case _ => true}
    gameMap.elements.count{case f: Fighter => true case _ => false } > 1
  }

  /**
    * @return returns a collision matrix Seq[(GameObject, Seq[GameObject])]*/
  private def moveElements = {
    val moveables = gameMap.elements
    var go_collision_matrix:Seq[(GameObject, Seq[GameObject])] = Seq()
    //println(moveables.sortBy(m => m.z + m.height).map(m => m.z+m.height))
    moveables.sortBy(m => m.z+m.height).foreach { m => //sorting to assure groundnearest objects are considered first

      /*---------preelimination for x and y axis---------*/
      var preelimination: Seq[GameObject] =
        moveables.filter { e => e != m && e.collidingX(m) { e.collidingY(m)(true) } }
      val collision_sequence = for(e <- preelimination) yield e // just copy the preelimination
      /*-------------------------------------------------*/

      /*--------z movement step by step-------*/
      m.moveZ {
        preelimination.filter { o =>
          (m.z >= o.z && m.z <= o.z + o.height*0.9) || (m.z <= o.z && m.z + m.height*0.9 >= o.z)
        }.foreach { e =>
          e.state.actOnCollision(m)
          preelimination = preelimination.filter{_ != e}
        }
      }
      go_collision_matrix = go_collision_matrix :+ (m, collision_sequence.filter { o =>
          (m.z >= o.z && m.z <= o.z + o.height) || (m.z <= o.z && m.z + m.height >= o.z)
        })
      /*--------------------------------------*/

      /*---x and y movement-----*/
      m.moveX
      m.moveY
      m.blocked.unblock
      /*------------------------*/

    }
    go_collision_matrix
  }

  private val GRAVITY_CONSTANT = 1
  private def gravityEffect(collision_matrix:Seq[(GameObject, Seq[GameObject])]) = {
    collision_matrix foreach { gocol =>
      if (gocol._1.gravity_affected) {
        val go = gocol._1
        if (!go.groundContact && !go.state.isInstanceOf[MidAir])
          go.state.levitate
        go.gravity_affect(GRAVITY_CONSTANT)
        if(!go.isInstanceOf[Stage])go.groundContact = false
      }
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
