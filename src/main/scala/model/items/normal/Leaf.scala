package main.scala.model.items.normal

import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.items.state.{ItemState, Normal}
import main.scala.model.states.{AnimateMe, MidAir, State}
import scala.util.Random

/**
  * Created by julian on 17.06.16.
  */
class Leaf(x:Int,y:Int,z:Int) extends DustParticle(x, y, z){
  width = 10
  height = 10
  length = 10
  images = new ImageMatrix("leaf.png", this, 4, 6)
  state = new Leaf_Normal(this)
  vulnerable = false
  tangible = true
  images.set(Leaf.r.nextInt(2))

}

private class Leaf_Normal(l:Leaf) extends ItemState(l){
  override def hurtBy(g:GameObject)(amount:Int = g.strength): Unit ={
    actOnCollision(g)
  }
  override def actOnCollision(g:GameObject): Unit ={
    if(g.moving)
      l.state = Leaf_Moving(l)
  }
  override def levitate =
    l.state = new Leaf_Moving(l) with MidAir
  override def landing(g:GameObject): Unit ={
    l.state = new Leaf_Normal(l)
    l.z_velocity = 0
    l.x_velocity = 0
    l.y_velocity = 0
  }
  override def inflictDamageTo(g:GameObject, amount:Int = 0) = {}
  override def stop = {}
}

private case class Leaf_Moving(l:Leaf) extends Leaf_Normal(l) with AnimateMe{
  l.images.set(ImageMatrix.ITEM_MOVE)
  l.z_velocity += Leaf.r.nextInt(10)+30
  l.x_velocity += (Leaf.r.nextInt(5)+2) * {if(Leaf.r.nextBoolean()) -1 else 1}
  l.collidable = false
}

private object Leaf{
  val r = new Random()
}
