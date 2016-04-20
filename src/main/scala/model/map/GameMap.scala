package main.scala.model.map

import java.awt.image.BufferedImage

import main.scala.model.{GameObject, Mass, Moveable}
import main.scala.model.fighter.Fighter
import main.scala.model.rocks.normal.Rock
import main.scala.util.Observer

/**
 * Created by julian on 14.02.16.
 * representation of a Map, that has a background image and consists of stages
 */
case class GameMap(backGround: BufferedImage){

  /*TODO this sequence might be vulnerable to parallel execution!! probably need to synchronize it*/
  var elements:Seq[GameObject] = Seq(Stage(), Rock(200, 300, 0))
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
