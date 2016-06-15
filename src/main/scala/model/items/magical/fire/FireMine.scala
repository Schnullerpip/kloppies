package main.scala.model.items.magical.fire

import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.fighter.Fighter
import main.scala.model.intention.Harmful
import main.scala.model.items.Item
import main.scala.model.items.state.{Break, Normal}
import main.scala.model.states.{AnimateMe, MidAir, State}
import main.scala.util.sound.SoundDistributor

/**
  * Created by julian on 02.05.16.
  */
class FireMine(val caster:Fighter) extends Item{
  override var images: ImageMatrix = new ImageMatrix("fireMine.png", this,8, 6)
  override var state: State = new FireMineNormal(this)
  override var mass: Int = 5
  override var full_strength: Int = mass
  override var hp: Int = 1
  override var x: Int = caster.x + caster.width/2 * (1 + caster.directionValue)
  override var y: Int = caster.y
  override var z: Int = caster.z

  width  = FireMine.mine_width
  height = FireMine.mine_height
  length = FireMine.mine_length
  intention = Harmful
  looksLeft = caster.looksLeft
  SoundDistributor.play("beep")
}

class FireMineNormal(fireMine:FireMine) extends Normal(fireMine) with AnimateMe{
  fireMine.intention = Harmful
  override def inflictDamageTo(gameObject: GameObject, amount:Int): Unit ={
    fireMine.caster.notifyObservers(new Explosion(fireMine))
    fireMine.state = new Break(fireMine)
  }

  override def actOnCollision(go:GameObject): Unit ={
    if(go != fireMine.caster && fireMine.intention == Harmful){
      super.actOnCollision(go)
    }
  }

  override def levitate = fireMine.state = new FireMineLevitate(fireMine)
  override def stop = fireMine.state = new FireMineNormal(fireMine)
}

class FireMineLevitate(fireMine: FireMine) extends FireMineNormal(fireMine) with MidAir with AnimateMe{
  fireMine.images.set(ImageMatrix.ITEM_MOVE)
}

object FireMine{
  lazy val (mine_width, mine_height, mine_length) = (20, 10, 10)
}
