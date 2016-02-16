package main.scala.model.map

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import main.scala.model.{GameObject, Moveable, Mass}
import main.scala.model.fighter.Fighter

/**
 * Created by julian on 14.02.16.
 * representation of a Map, that has a background image and consists of stages
 */
class GameMap(val backGround: BufferedImage, stages:Seq[Stage]){
  def this(path:String, stages:Seq[Stage]) = this(ImageIO.read(new File(path)), stages)
  def apply(element:GameObject) = elements = element +: elements

  /*this sequence might be vulnerable to parallel execution!! probably need to synchronize it*/
  var elements:Seq[GameObject] = Seq()

  def filter[T] = (f:(AnyRef) => Boolean) => elements.filter{f}.asInstanceOf[Seq[T]]
  def fighters:Seq[Fighter] =  filter{case f:Fighter => true case _ => false}
  def massives:Seq[Mass] = filter{case m:Mass => true case _ => false}
  def moveable:Seq[Moveable] = filter{case m:Moveable => true case _ => false}
}

object GameMap{
  def apply(path:String, stages:Seq[Stage]) = new GameMap(ImageIO.read(new File(path)), stages)
}
