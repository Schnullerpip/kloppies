package main.scala.model

/**
 * Created by julian on 14.02.16.
 */
trait Direction {
  var looksLeft = true
  def turn = looksLeft = !looksLeft
}
