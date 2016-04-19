package main.scala.model

/**
 * Created by julian on 14.02.16.
 * The Massive trait will be inherited by anything, that for example wants to be gravity affected, or anything else that
 * needs to have a weight
 */
trait Mass extends Moveable{
  var mass:Int
  var gravity_affected = true
  var groundContact = true
  var steppable = false

  def gravity_affect(pace:Int = 1) = {
    if(gravity_affected) {
      z_velocity -= pace
    }
  }

  def moveZ(f: => Unit) = {
    if (z_velocity < 0){
      val reach = z + z_velocity
      val velocity = if(z_velocity > height) -1*height else z_velocity
      while (z > reach && !groundContact)
      {
        z += velocity
        f
      }
    } else {
      z += z_velocity
      f
    }
  }
}
