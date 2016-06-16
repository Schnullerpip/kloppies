package main.scala.model.items.normal

import main.scala.model.ImageMatrix
import main.scala.model.attributes.Speed
import main.scala.model.intention.Harmless
import main.scala.model.items.Item
import main.scala.model.items.state.Normal
import main.scala.model.states.{OneHitWonder, State}

/**
  * Created by julian on 15.06.16.
  */
class DustyLeave( override var x:Int,
                  override var y:Int,
                  override var z:Int,
                  direction:Boolean = true
                     ) extends Item with Speed{
  override var speed = 20
  intention = Harmless
  collidable = false
  tangible = false
  looksLeft = direction
  override var images: ImageMatrix =
    new ImageMatrix("dustyLeave.png", this, 1, 5)
  override var state: State = new Normal(this) with OneHitWonder{
    override def cleanMeUp = goKillYourself
  }
  override var hp: Int = 1
  override var full_strength: Int = 0
  override var mass: Int = 1
}
