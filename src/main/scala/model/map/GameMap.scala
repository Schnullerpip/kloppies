package main.scala.model.map

import java.awt.image.BufferedImage

import main.scala.model.{GameObject, Mass, Moveable}
import main.scala.model.fighter.Fighter
import main.scala.model.items.normal.{DustParticle, Leaf, Rock}
import main.scala.util.Observer

import scala.util.Random

/**
 * Created by julian on 14.02.16.
 * representation of a Map, that has a background image and consists of stages
 */
case class GameMap(backGround: BufferedImage){

  /*TODO this sequence might be vulnerable to parallel execution!! probably need to synchronize it*/
  var elements:Seq[GameObject] = Seq(Stage(), Rock(190, 300, 1000))
  private val r = new Random()
  for(i <- 0 until 16){
    elements = DustParticle(r.nextInt(1000), r.nextInt(800), 0) +: elements
  }
  for(i <- 0 until 1){
    val nl =  new Leaf(r.nextInt(1000), r.nextInt(800), 0)
    elements = nl +: elements
  }

  var observers:Seq[Observer] = Seq()

  def apply(observer: Observer) = {
    observers = observer +: observers
    elements.foreach{go => if(go.images != null) go.images.observers = observers}
  }
  def apply(element:GameObject) = elements = element +: elements

  private def filter[T] = (f:(AnyRef) => Boolean) => elements.filter{f}.asInstanceOf[Seq[T]]
  def fighters:Seq[Fighter] =  filter{case f:Fighter => true case _ => false}
  def massives:Seq[Mass] = filter{case m:Mass => true case _ => false}
  def moveable:Seq[Moveable] = filter{case m:Moveable => true case _ => false}
}
