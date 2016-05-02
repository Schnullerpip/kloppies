package main.scala.model.items.magical.fire

import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.fighter.Fighter
import main.scala.model.intention.Harmful
import main.scala.model.items.Item
import main.scala.model.items.state.Normal
import main.scala.model.states.{AnimateMe, MidAir, State}

/**
  * Created by julian on 02.05.16.
  */
class FireMine(val caster:Fighter) extends Item{
  override var images: ImageMatrix = new ImageMatrix("fireMine.png", this,8, 6)
  override var state: State = new FireMineNormal(this)
  override var mass: Int = 5
  override var full_strength: Int = 50
  override var hp: Int = 1
  override var x: Int = caster.x + caster.width/2 * (1 + caster.directionValue)
  override var y: Int = caster.y
  override var z: Int = caster.z

  width  = FireMine.mine_width
  height = FireMine.mine_height
  length = FireMine.mine_length
  intention = Harmful
}

class FireMineNormal(fireMine:FireMine) extends Normal(fireMine){
  override def inflictDamageTo(gameObject: GameObject, amount:Int): Unit ={
    fireMine.goKillYourself
    fireMine.caster.notifyObservers(new Explosion(fireMine))
  }

  override def actOnCollision(go:GameObject): Unit ={
    if(go != fireMine.caster){
      super.actOnCollision(go)
    }
  }

  override def levitate = fireMine.state = new FireMineLevitate(fireMine)
}

class FireMineLevitate(fireMine: FireMine) extends FireMineNormal(fireMine) with MidAir with AnimateMe{
  fireMine.images.set(ImageMatrix.ITEM_MOVE)
}

object FireMine{
  val (mine_width, mine_height, mine_length) = (20, 10, 5)
}
