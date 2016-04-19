package main.scala.model.states

import main.scala.model.GameObject

/**
 * Created by julian on 14.02.16.
 * Superclass for all States
 */
abstract class State(val go:GameObject) {
  def actOnCollision(g:GameObject) = if(go.steppable && go.collidable){
    g.groundContact = true
    if(g.state.isInstanceOf[MidAir] && g.z_velocity < 0)
      g.state.landing
  }
  def inflictDamageTo(go:GameObject, amount:Int)
  def hurtBy(go:GameObject)
  def stop
  def landing
  def levitate
}
