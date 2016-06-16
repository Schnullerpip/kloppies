package main.scala.model.items.normal

import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.items.Item
import main.scala.model.items.state.ItemState
import main.scala.model.states.{MidAir, State}

/**
  * Created by julian on 16.06.16.
  */
case class DustParticle(override var x:Int,
                        override var y:Int,
                        override var z:Int
                       ) extends Item{
  override var images: ImageMatrix = new ImageMatrix("dustParticle.png", this, 1, 1)
  override var state: State = DustStatus(this)
  override var full_strength: Int = 0
  override var hp: Int = 1
  override var mass: Int = 1
  drawsShadow = false
  height = 1
  width = 10
  length = 5
  vulnerable = false
  tangible = true
  collidable = true
  override def gravity_affect(pace:Int = 0): Unit ={
    if(gravity_affected && state.isInstanceOf[MidAir])
      z_velocity = -1
  }
  override def moveX = {
    if(x_velocity != 0) {
      if (x_velocity > 0)
        x_velocity -= 1
      else
        x_velocity += 1
    }
    super.moveX
  }
}

private case class DustStatus(d:DustParticle) extends ItemState(d){
  override def hurtBy(g:GameObject)(amount:Int = g.strength): Unit ={
    d.z_velocity += amount
    d.x_velocity += amount * g.directionValue
  }
  override def actOnCollision(g:GameObject){
    d.z_velocity += {if(g.moving)20 else 0}
    d.x_velocity += {if(g.moving)10 else 0} * g.directionValue
    d.collidable = false
  }
  override def levitate =
    d.state = new DustStatus(d) with MidAir
  override def landing(g:GameObject): Unit ={
    d.state = DustStatus(d)
    d.z_velocity = 0
    d.x_velocity = 0
    d.y_velocity = 0
  }
  override def inflictDamageTo(g:GameObject, amount:Int = 0) = {}
  override def stop = {}
}
