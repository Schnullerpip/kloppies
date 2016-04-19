package main.scala.model

/**
 * Created by julian on 14.02.16.
 * Representation of a size. anything that is shown on the map must have a specific size
 */
trait Size {
  var width = 40
  var height = 50
  var length = width/2
}
