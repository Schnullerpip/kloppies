package main.scala.model.items.magical.wind

import main.scala.model.intention.Harmful
import main.scala.model.states.State
import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.fighter.Fighter
import main.scala.model.items.Item
import main.scala.model.items.state.ItemState

/**
 * Created by julian on 25.02.16.
 */
case class Wind(caster:Fighter) extends Item{
  override var images: ImageMatrix = null
  override var mass: Int = 0
  override var strength: Int = 10
  override var hp: Int = 1
  override val width = 500
  override val height = 500
  override var x: Int = caster.x+width/2
  override var y: Int = caster.y+width/2
  override var z: Int = caster.z+width/2
  override var state:State = WindState(this)
}

case class WindState(item:Item) extends ItemState(item){
  item.vulnerable = false
  item.moveable = false
  item.intention = Harmful

  override def inflictDamageTo(go:GameObject, amount:Int) = {
    go.x += amount/go.mass * {if(item.looksLeft) -1 else 1}
  }
}
