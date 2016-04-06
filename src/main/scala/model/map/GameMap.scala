package main.scala.model.map

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import main.scala.model.{GameObject, Moveable, Mass}
import main.scala.model.fighter.Fighter
import main.scala.util.Observer

/**
 * Created by julian on 14.02.16.
 * representation of a Map, that has a background image and consists of stages
 */
class GameMap(val backGround: BufferedImage, var stages:Seq[Stage], var observers:Seq[Observer]){
  def apply(observer: Observer) = {
    observers = observer +: observers
    elements.foreach{go => go.images.observers = observers}
  }
  def apply(element:GameObject) = elements = element +: elements

  def apply(stage:Stage) = stages = stage +: stages

  /*TODO this sequence might be vulnerable to parallel execution!! probably need to synchronize it*/
  var elements:Seq[GameObject] = Seq()

  private def filter[T] = (f:(AnyRef) => Boolean) => elements.filter{f}.asInstanceOf[Seq[T]]
  def fighters:Seq[Fighter] =  filter{case f:Fighter => true case _ => false}
  def massives:Seq[Mass] = filter{case m:Mass => true case _ => false}
  def moveable:Seq[Moveable] = filter{case m:Moveable => true case _ => false}
}

object GameMap{
  def apply(path:String, stages:Seq[Stage], observers:Seq[Observer] = Seq()) =
    new GameMap(ImageIO.read(new File(path)), stages, observers)
}
