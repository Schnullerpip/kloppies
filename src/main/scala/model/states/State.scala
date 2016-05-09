package main.scala.model.states

import main.scala.model.GameObject

/**
 * Created by julian on 14.02.16.
 * Superclass for all States
 */
abstract class State(val go:GameObject) {

  /**
    * The most basic actOnCollision definition is to act as an obstacle (in case the object is steppable)*/
  def actOnCollision(g:GameObject) = {

    if(go.steppable && go.collidable){
       /*make sure opposing object will be blocked for movement*/
      if(go.z+go.height > g.z+g.height/3){
        if(go.x < g.x) g.blocked.left = true
        else if(go.x > g.x) g.blocked.right = true
        if(go.y < g.y) g.blocked.deep = true
        else if(go.y > g.y) g.blocked.forth = true
        if(go.z > g.z) g.blocked.up = true
      }
     if(go.groundContact) {
       g.groundContact = true
       if (g.state.isInstanceOf[MidAir] && g.z_velocity < 0 && go.groundContact)
         g.state.landing(go)
     }
    }
  }
  def inflictDamageTo(go:GameObject, amount:Int)
  def hurtBy(go:GameObject)
  def stop
  def landing(go:GameObject)
  def levitate

  override def toString = this.getClass.getSimpleName
}
