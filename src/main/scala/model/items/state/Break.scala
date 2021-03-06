package main.scala.model.items.state

import main.scala.model.attributes.Speed
import main.scala.model.intention.Harmless
import main.scala.model.items.Item
import main.scala.model.ImageMatrix.ITEM_BREAK

/**
 * Created by julian on 22.02.16.
 */
class Break(val item:Item) extends ItemState(item){
  item.intention = Harmless
  item.vulnerable = false
  item.moveable = false
  item.collidable = false
  item.x_velocity = 0
  item.y_velocity = 0
  item.z_velocity = 0
  item.tangible = false
  if(item.images != null)
    item.images.set(ITEM_BREAK)

  private val sleepTime = 1000 / 50

  new Thread(new Runnable {
    override def run(): Unit = {
      for(i <- 0 to (item.images.cols-2)){
        Thread.sleep(sleepTime)
        item.images.next
      }
      Thread.sleep(sleepTime)
      item goKillYourself
    }
  }).start()
}
