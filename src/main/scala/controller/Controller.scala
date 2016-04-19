package main.scala.controller

import main.scala.model.GameObject
import main.scala.model.attributes.LivePoints
import main.scala.model.fighter.Fighter
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
    moveables foreach { m =>
      /*---y and y movement-----*/
      m.moveX
      m.moveY
      /*------------------------*/


      /*---------preelimination for x and y axis---------*/
      var preelimination: Seq[GameObject] =
        moveables.filter { e => e != m && e.collidingX(m) { e.collidingY(m)(true) } }
      val collision_sequence = for(e <- preelimination) yield e // just copy the preelimination
      /*-------------------------------------------------*/


      /*--------z movement step by step-------*/
      m.moveZ {
        val colliding = preelimination.filter { o =>
          (m.z >= o.z && m.z <= o.z + o.height) || (m.z <= o.z && m.z + m.height >= o.z)
        }
        colliding.foreach { e =>
          e.state.actOnCollision(m)
          preelimination = preelimination.filter{_ != e}
        }
      }
      go_collision_matrix = go_collision_matrix :+ (m, collision_sequence.filter { o =>
          (m.z >= o.z && m.z <= o.z + o.height) || (m.z <= o.z && m.z + m.height >= o.z)
        })
      /*--------------------------------------*/
    }
    go_collision_matrix
  }

  private val GRAVITY_CONSTANT = 1
  private def gravityEffect(collision_matrix:Seq[(GameObject, Seq[GameObject])]) = {
    collision_matrix foreach { gocol =>
      if (gocol._1.gravity_affected) {
        val go = gocol._1
        //if(!gocol._2.exists(e => e.steppable && e.tangible && e.collidable)) go.state.levitate
        if (!go.groundContact && !go.state.isInstanceOf[MidAir]) go.state.levitate
        go.gravity_affect(GRAVITY_CONSTANT)
        go.groundContact = false
      }
    }
  }

  /**
    * @param elems a sequence of GameObjects that will be checked for collisions
    * @return a sequence of tuples, containing ._1 = a gameobject and ._2 = all the colliding gameobjects, where there are no doubles!
    *         if you want to make sure each gameObject acts its dedicated behaviour when iterating over the returned matrix, then you should
    *         call both actOnCollision Methods for each found pair
    * */
  private def collisionMatrix(elems:Seq[GameObject] = gameMap.elements):Seq[(GameObject, Seq[GameObject])] =
    collisionMatrixR(Seq(), elems)

  @tailrec
  private def collisionMatrixR(matrix:Seq[(GameObject, Seq[GameObject])], elems:Seq[GameObject]):Seq[(GameObject, Seq[GameObject])] =
    if (elems.size > 1) {
      var collisions = Seq[GameObject]()
      elems.tail.foreach { e =>
        if (e.colliding(elems.head)) {
          collisions = collisions :+ e
        }
      }
      collisionMatrixR(matrix :+ (elems.head, collisions), elems.tail)
    } else Seq()


  private def collisionSequence(gameObject: GameObject, others:Seq[GameObject]):(GameObject, Seq[GameObject]) =
    (gameObject, others.filter(e => e != gameObject && e.colliding(gameObject)))

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
