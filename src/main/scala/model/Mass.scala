package main.scala.model

/**
 * Created by julian on 14.02.16.
 * The Massive trait will be inherited by anything, that for example wants to be gravity affected, or anything else that
 * needs to have a weight
 */
trait Mass extends Moveable{
  var mass:Int
  var gravity_affected = true
  def gravity_affect(pace:Int = 1) = if(gravity_affected)y_velocity += pace
}
