package main.scala.model.items.magical.fire

import main.scala.model.attributes.Speed
import main.scala.model.intention.Harmful
import main.scala.model.items.state.{Break, Move, Normal}
import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.items.Item
import main.scala.model.states.{AnimateMe, State}

/**
 * Created by julian on 22.02.16.
 */
case class FireBall(caster:GameObject) extends Item with Speed{
  override var strength: Int = 10
  override var speed: Int = 15
  override var mass: Int = 1
  override var hp: Int = 1
  override var x: Int = caster.x + (caster.width * {if (caster.looksLeft) -1 else 1})
  override var y: Int = caster.y
  override var z: Int = caster.z
  override val width = 50
  override val height = 50
  looksLeft = caster.looksLeft
  override var images: ImageMatrix = new ImageMatrix("fireball.png", this, 6, 6)
  override var state: State = FireBallStateMove(this)
  val MAX_RANGE = 4000
  var range = 0

  override def move = {
    super.move
    range += x_velocity*{if(x_velocity < 0)-1 else 1}
    if(range >= MAX_RANGE)
      goKillYourself
  }
}

case class FireBallStateNormal(item:FireBall) extends Normal(item) with AnimateMe{
  item.intention = Harmful
  override def actOnCollision(go:GameObject) = {
    if (go != item.caster && go.tangible){
      super.actOnCollision(go)
      item.state = Break(item)
    }
  }
}

case class FireBallStateMove(item:FireBall) extends Move(item) with AnimateMe{
  item.intention = Harmful
  item.x_velocity += item.speed * {if(item.caster.looksLeft) -1 else 1}
  override def actOnCollision(go:GameObject) = {
    if (go != item.caster && go.tangible){
      super.actOnCollision(go)
      item.state = Break(item)
    }
  }
}
