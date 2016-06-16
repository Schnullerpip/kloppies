package main.scala.model.items.state

import main.scala.model.GameObject
import main.scala.model.attributes.LivePoints
import main.scala.model.intention.{Harmful, Harmless}
import main.scala.model.items.Item
import main.scala.model.states.{MidAir, State}
import main.scala.util.sound.SoundDistributor

/**
 * Created by julian on 22.02.16.
 */
abstract class ItemState(item:Item) extends State(item) {

  override def actOnCollision(go: GameObject): Unit = {
    if(item.intention == Harmful && go.tangible){
      item.state.inflictDamageTo(go, item.strength)
    }
    super.actOnCollision(go)
  }

  override def levitate = item.state = new Normal(item) with MidAir

  override def landing(go:GameObject) = {
    if(item.x+item.width/2 > go.x+go.width){ //Schwerpunkt ist außerhalb der Landefläche, deshalb sollte item wegspringen
      item.x_velocity -= item.z_velocity/4
      item.z_velocity = -1
    }else if(item.x+item.width/2 < go.x){
      item.x_velocity += item.z_velocity/4
      item.z_velocity = -1
    }else if(item.velocity_factor > item.mass){ //item is falling too heavily and therefore flies up once more
      item.z_velocity = -1*item.z_velocity/2
    }else{
      item.x_velocity = 0
      item.y_velocity = 0
      item.z_velocity = 0
      item.state.stop
    }
  }

  override def inflictDamageTo(go:GameObject, amount:Int)={
    go.state.hurtBy(item)(amount)
  }

  override def hurtBy(g:GameObject)(amount:Int = g.strength) = item match {
    case l:LivePoints if item.vulnerable => item.state = new Hurt(item, g)(amount)
    case _ => //Break(item)
  }
  override def stop: Unit = item.state = new Normal(item)

}
