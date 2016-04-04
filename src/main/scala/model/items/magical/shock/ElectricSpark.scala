package main.scala.model.items.magical.shock

import main.scala.model.ImageMatrix
import main.scala.model.attributes.Speed
import main.scala.model.items.Item
import main.scala.model.items.state.Normal
import main.scala.model.states.{OneHitWonder, AnimateMe, State}

/**
 * Created by julian on 02.03.16.
 */
case class ElectricSpark(override var x:Int, override var y:Int, override var z:Int) extends Item with Speed{
  override var images: ImageMatrix = new ImageMatrix("speedplus.png", this, 2, 6)
  override var strength: Int = 0
  override var hp: Int = 1
  override var mass: Int = 0
  override var speed: Int = 15
  override var state: State = new Normal(this) with AnimateMe with OneHitWonder{
    override def cleanMeUp = goKillYourself
  }
  collidable = false
  vulnerable = false
  moveable = false
}
