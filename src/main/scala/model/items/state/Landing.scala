package main.scala.model.items.state

import main.scala.model.intention.Harmless
import main.scala.model.items.Item
import main.scala.model.ImageMatrix.ITEM_NORMAL
import main.scala.util.sound.SoundDistributor

/**
  * Created by julian on 18.04.16.
  */
case class Landing(item:Item) extends ItemState(item){
  item.groundContact = true
  item.intention = Harmless
  item.images.set(ITEM_NORMAL)
  SoundDistributor.play("deep_smash")
  item.x_velocity = 0
  item.y_velocity = 0
  item.z_velocity = 0
}
