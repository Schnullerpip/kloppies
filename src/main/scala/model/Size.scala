package main.scala.model

/**
 * Created by julian on 14.02.16.
 * Representation of a size. anything that is shown on the map must have a specific size
 */
trait Size {
  var width   = 40 // x--------->
  var height  = 50 // z--------->
  var length  = 10 // y--------->
}
