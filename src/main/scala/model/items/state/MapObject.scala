package main.scala.model.items.state

import main.scala.model.intention.Harmless
import main.scala.model.items.Item

/**
  * Created by julian on 19.04.16.
  */
case class MapObject(item:Item) extends ItemState(item){
  item.intention = Harmless
  item.moveable = false
  item.vulnerable = false
  item.collidable = true
  item.tangible = false
  item.gravity_affected = false
}
