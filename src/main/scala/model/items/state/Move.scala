package main.scala.model.items.state

import main.scala.model.items.Item
import main.scala.model.ImageMatrix.ITEM_MOVE
import main.scala.model.states.AnimateMe

/**
 * Created by julian on 22.02.16.
 */
class Move(item:Item) extends ItemState(item) with AnimateMe{
  item.images.set(ITEM_MOVE)
}
