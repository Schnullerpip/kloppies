package main.scala.model

/**
 * Created by julian on 14.02.16.
 */
trait Moveable extends Position{
  var x_velocity, y_velocity, z_velocity = 0
  /**
   * moveable should only affect the Player (if there is one).
   * If false then the player is not allowed to move the Moveable.
   * Is for example turned off during animations
   * */
  var moveable = true

  def moving = x_velocity != 0 || y_velocity != 0

  def move{
    x += x_velocity
    y += y_velocity
    //z += z_velocity
  }
}
