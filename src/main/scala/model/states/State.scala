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
      var blocking_it = false
       /*make sure opposing object will be blocked for movement*/
      if(go.z+go.height > g.z+g.height/3){
        if(go.x < g.x) {g.blocked.left = true; blocking_it = true}
        else if(go.x > g.x) {g.blocked.right = true; blocking_it = true}
        if(go.y < g.y) {g.blocked.deep = true; blocking_it = true}
        else if(go.y > g.y) {g.blocked.forth = true; blocking_it = true}
        if(go.z > g.z) {g.blocked.up = true; blocking_it = true}
      }
     if(go.groundContact && !blocking_it) {
       g.groundContact = true
       if (g.state.isInstanceOf[MidAir] && g.z_velocity < 0 && go.groundContact)
         g.state.landing(go)
     }
    }
  }
  def inflictDamageTo(g:GameObject, amount:Int)
  def hurtBy(g:GameObject)(amount:Int = g.strength)
  def stop
  def landing(g:GameObject)
  def levitate
  /**
    * method that should be called with a state to do the actual stuff - else closure might happen
    * and state changes inside a states constructor have no effect
    * */
  def init = {}

  override def toString = this.getClass.getSimpleName
}
