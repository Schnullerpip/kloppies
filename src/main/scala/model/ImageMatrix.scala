package main.scala.model

import java.io.File
import javax.imageio.ImageIO
import main.scala.model.ImageMatrix.ImageCoordinate
import main.scala.util.Observable

/**
 * Created by julian on 14.02.16.
 * Class for providing image matrices that split a palette of images to several chunks, which hold an individual image
 * representing a Fighters state for example
 */
class ImageMatrix(val path:String, val go:GameObject, val rows:Int, val cols:Int) extends Observable{
 val image_coordinate = ImageCoordinate(cols)

  def set(row:Int, col:Int = 0){
    image_coordinate.r = {if(go.looksLeft) 1 else 0} + row
    image_coordinate.c = col
    notifyObservers()
  }
  def next = {val ic = image_coordinate.next;notifyObservers();ic}
  def currentImage = images(image_coordinate.r)(image_coordinate.c)


  val images = {
    val imagePalette = ImageIO.read(new File(path))
    for(row <- 0 to rows-1) yield {
      for(col <- 0 to cols-1)yield {
        val (x, y, w, h) = (col*go.width, row*go.height, go.width, go.height)
        imagePalette.getSubimage(x, y, w, h)
      }
    }
  }
}

object ImageMatrix{
  case class ImageCoordinate(cols:Int){
    var r:Int = 0
    var c:Int = 0
    def next:ImageCoordinate = {if(c+1 >= cols) c = 0 else c+=1; this}
  }
  /*rows even numbers are for the left direction*/
  val STANDING = 0
  val STANDING_HIT = 2
  val RUNNING = 4
  val RUNNING_HIT = 6
  val JUMPING = 8
  val LEVITATING = 10
  val LEVITATING_HIT = 12
  val LANDING = 14
  val FALLING = 16
  val LYING = 18
  val OUCH = 20
}
