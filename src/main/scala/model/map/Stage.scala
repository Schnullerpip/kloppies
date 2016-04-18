package main.scala.model.map

import java.awt.Color
import java.awt.image.BufferedImage

import main.scala.model.{GameObject, ImageMatrix, Position}
import main.scala.model.items.Item
import main.scala.model.items.state.MapObject
import main.scala.model.states.State
import main.scala.view.gui.Defaults

/**
 * Created by julian on 14.02.16.
 * Describes the plane that a stage spans. as long as the fighter is on said stage
 */
case class Stage (override var x:Int = -2000,
                  override var y:Int = -2000,
                  override var z:Int = 0,
                  override val width:Int = 10000,
                  override val length:Int = 10000,
                  override val height:Int = 0,
                  /**style is used if no image is found to draw a surface for the stage according to a style*/
                  style:Option[StageStyle] = Some(Rocky)) extends Item {
  override var images: ImageMatrix = null
  override var state: State = MapObject(this)
  override var strength: Int = 0
  override var mass: Int = width*length
  override var hp: Int = mass
}

sealed trait StageStyle{
  val color:Color
  val items:Seq[Item] = Seq()
}
case object Rocky extends StageStyle{
  override val color = Defaults.BROWNISH
  /*TODO insert some small rock items as soon as they exist*/
}

case object Meadow extends StageStyle{
  override val color = Defaults.GREENY
  /*TODO insert some bush items as soon as they exist*/
}

case object Street extends StageStyle {
  override val color = Defaults.STREET_GREY
  /*TODO insert some bins or lantern items as soon as they exist*/
}
