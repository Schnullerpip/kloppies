package main.scala.model.attributes

import main.scala.model.Size

/**
 * Created by julian on 14.02.16.
 * Livepoints is mixed in each object, that can be destroyed.
 * Mixes Size in, because everything, that can be destroyed needs to be present on the map
 * and therefore it needs to be able to be visualized what requires a specific size to it
 */
trait LivePoints extends Size{
  var hp:Int
  var vulnerable = true
  def takeDamage(damage:Int) = hp -= damage
}
