package main.scala.util.images

import java.awt.RenderingHints
import java.awt.image.BufferedImage

/**
  * Created by julian on 02.05.16.
  */
object ScaleImage {
  def apply(image:BufferedImage, newWidth:Int, newHeight:Int):BufferedImage = {
    val newImage = new BufferedImage(newWidth, newHeight, image.getType)
    val graphics = newImage.createGraphics()
    graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    graphics.drawImage(image, 0, 0, newWidth, newHeight,0, 0, image.getWidth, image.getHeight, null)
    graphics.dispose()
    newImage
  }
}
