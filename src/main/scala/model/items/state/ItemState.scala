package main.scala.model.items.state

import main.scala.model.GameObject
import main.scala.model.attributes.LivePoints
import main.scala.model.intention.Harmful
import main.scala.model.items.Item
import main.scala.model.states.{MidAir, State}

/**
 * Created by julian on 22.02.16.
 */
abstract class ItemState(item:Item) extends State(item) {
  override def actOnCollision(go: GameObject): Unit = {
    if(item.intention == Harmful && go.tangible){
      item.state.inflictDamageTo(go, item.strength)
      //item.state = Break(item)
    }
    super.actOnCollision(go)
  }

  override def levitate = item.state = new Normal(item) with MidAir

  override def landing = item.state = Landing(item)

  override def inflictDamageTo(go:GameObject, amount:Int)={
    go.state.hurtBy(item)
  }

  override def hurtBy(go:GameObject) = item match {
    case l:LivePoints if item.vulnerable => item.state = new Hurt(item, go)()
    case _ => //Break(item)
  }
  override def stop: Unit = {}

}
