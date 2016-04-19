package main.scala.model

/**
 * Created by julian on 14.02.16.
 * Representation of a size. anything that is shown on the map must have a specific size
 */
trait Size {
  val width = 40
  val height = 50
  val length = width/2
}
