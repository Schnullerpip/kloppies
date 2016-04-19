package main.scala.model.items.normal

import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.intention.{Harmful, Harmless}
import main.scala.model.items.Item
import main.scala.model.items.state.ItemState
import main.scala.model.states.{MidAir, State}
import main.scala.util.sound.SoundDistributor

/**
  * Created by julian on 07.04.16.
  */
class Stone(override var x:Int, override var y:Int, override var z:Int) extends Item {
  override var images: ImageMatrix = new ImageMatrix("stone.png", this, 8, 6)
  override var state: State = new StoneNormal(this)
  override var mass: Int = 5
  override var hp: Int = mass
  override var strength: Int = 1
  width = 10
  height = 10
  length = 5
  steppable = true
}

class StoneNormal(stone:Stone) extends ItemState(stone) {
  import main.scala.model.ImageMatrix.ITEM_NORMAL
  stone.intention = Harmless
  stone.images.set(ITEM_NORMAL)
  stone.tangible = true
}
case class StoneFalling(s:Stone) extends StoneNormal(s) with MidAir{
  s.intention = Harmful
  //override def landing = {
  //  SoundDistributor.play("deep_smash")
  //  s.x_velocity = 0
  //  s.y_velocity = 0
  //  s.state = new StoneNormal(s)
  //}
  override def actOnCollision(go:GameObject)={
    if(go.tangible)
      SoundDistributor.play("small_punch")
    super.actOnCollision(go)
  }
}
