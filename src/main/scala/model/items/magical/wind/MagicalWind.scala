package main.scala.model.items.magical.wind

import main.scala.model.intention.Harmful
import main.scala.model.states.State
import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.fighter.Fighter
import main.scala.model.items.Item
import main.scala.model.items.state.ItemState
import main.scala.util.sound.SoundDistributor

/**
 * Created by julian on 25.02.16.
 */
case class MagicalWind(caster:Fighter) extends Item{
  override var images: ImageMatrix = null
  override var mass: Int = 0
  override var full_strength: Int = 15
  override var hp: Int = 1
  width = 2000
  height = 2000
  length = 2000
  override var x: Int = caster.x-width/2
  override var y: Int = caster.y-length/2
  override var z: Int = caster.z-height/2
  override var state:State = WindState(this)
  tangible = false
  looksLeft = caster.looksLeft
  gravity_affected = false
  SoundDistributor.loop("wind_loop")

  override def goKillYourself = {
    SoundDistributor.stop("wind_loop")
    super.goKillYourself
  }
}

case class WindState(item:Item) extends ItemState(item){
  item.vulnerable = false
  item.moveable = false
  item.intention = Harmful

override def inflictDamageTo(go:GameObject, amount:Int) = {
    if(go.mass > 0)
      go.extrinsicMove(amount/go.mass*item.directionValue)
  }
  override def hurtBy(g:GameObject)(amount:Int=g.strength) = {}
}
