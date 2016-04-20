package main.scala.model

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import main.scala.model.ImageMatrix.ImageCoordinate
import main.scala.util.{Observer, Observable}

import scala.collection.mutable

/**
 * Created by julian on 14.02.16.
 * Class for providing image matrices that split a palette of images to several chunks, which hold an individual image
 * representing a Fighters state for example
 */
class ImageMatrix(val name:String, val go:GameObject, val rows:Int, val cols:Int) extends Observable{
  val image_coordinate = ImageCoordinate(cols)
  val images = ImageMatrix.matrices(name)

  def set(row:Int, col:Int = 0, dir:Boolean = go.looksLeft){
    image_coordinate.r = {if(dir) 1 else 0} + row
    image_coordinate.c = col
    notifyObservers()
  }
  def next = {val ic = image_coordinate.next;notifyObservers();ic}
  def currentImage =
    images(image_coordinate.r)(image_coordinate.c)
}

object ImageMatrix{
  case class ImageCoordinate(cols:Int){
    var r:Int = 0
    var c:Int = 0
    def next:ImageCoordinate = {if(c+1 >= cols) c = 0 else c+=1; this}
    override def toString = s"r:$r, c:$c"
  }

  val matrices = mutable.HashMap[String, IndexedSeq[IndexedSeq[BufferedImage]]]()
  def apply(path:String, rows:Int=FIGHTER_ROWS, cols:Int=FIGHTER_COLS, width:Int=FIGHTER_WIDTH, height:Int=FIGHTER_HEIGHT) = {
    val imageFile = new File(path)
    val imagePalette = ImageIO.read(imageFile)
    val matrix = {
      for (row <- 0 until rows) yield {
        for (col <- 0 until cols) yield {
          val (x, y, w, h) = (col * width, row * height, width, height)
          imagePalette.getSubimage(x, y, w, h)
        }
      }
    }
    matrices.put(imageFile.getName, matrix)
  }


  /*rows even numbers are for the left direction*/
  /*FOR FIGHTERS*/
  val FIGHTER_ROWS = 30
  val FIGHTER_COLS = 7
  val FIGHTER_WIDTH = 40
  val FIGHTER_HEIGHT = 50

  val STANDING = 0
  val STANDING_HIT = 2
  val RUNNING = 4
  val RUNNING_HIT = 6
  val JUMPING = 8
  val LEVITATING = 10
  val LEVITATING_HIT = 12
  val LANDING = 14
  val FALLING = 16
  val HIT_THE_GROUND = 18
  val LYING = 20
  val OUCH = 22
  val THROW_TECHNIQUE = 24
  val USE_TECHNIQUE = 26
  val DEFENDING = 28

  /*FOR ITEMS*/
  val ITEM_ROWS = 6
  val ITEM_COLS = 6
  val ITEM_WIDTH = 50
  val ITEM_HEIGHT = 50

  val ITEM_NORMAL = 0
  val ITEM_MOVE = 2
  val ITEM_BREAK = 4
  val ITEM_OUCH = 6
}
