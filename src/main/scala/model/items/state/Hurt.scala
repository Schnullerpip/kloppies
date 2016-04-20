package main.scala.model.items.state

import main.scala.model.GameObject
import main.scala.model.items.Item
import main.scala.model.ImageMatrix.ITEM_OUCH
import main.scala.model.attributes.Speed

/**
  * Created by julian on 07.04.16.
  */
class Hurt(item:Item, go:GameObject)(amount:Int = go.strength) extends ItemState(item){
  private var contin = true

  if(item.hp - amount <= 0){
    item.state = new Break(item)
  }else{
    item.takeDamage(amount)
    item.images.set(ITEM_OUCH)

    val sleepTime = 1000 / {item match {
      case s:Speed => s.speed
      case _ => 150
    }}

    new Thread(new Runnable {
      override def run(): Unit = {
        var i = 0
        item.images.next
        Thread.sleep(sleepTime)
        while(i < item.images.cols-2 && contin){
          item.images.next
          Thread.sleep(sleepTime)
          i += 1
        }
      }
    }).start()
  }

  override def hurtBy(go:GameObject) = {
    contin = false
    item.state = new Hurt(item, go)()
  }
}
