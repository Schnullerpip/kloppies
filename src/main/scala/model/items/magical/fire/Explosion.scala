package main.scala.model.items.magical.fire

import main.scala.model.attributes.Speed
import main.scala.model.intention.Harmful
import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.items.Item
import main.scala.model.items.state.ItemState
import main.scala.model.states.{OneHitWonder, State}
import main.scala.util.images.ScaleImage
import main.scala.util.sound.SoundDistributor

/**
  * Created by julian on 02.05.16.
  */
class Explosion(val cause:GameObject ) extends Item with Speed{

  override var images: ImageMatrix = new ImageMatrix("explosion.png", this, 1, 15)
  override var state: State = Exploding(this)
  override var mass: Int = 0
  override var hp: Int = 1
  override var x: Int = cause.x
  override var y: Int = cause.y
  override var z: Int = cause.z
  override var full_strength:Int = 5 + cause.strength
  override var speed = 18

  intention = Harmful
  tangible = false
  steppable = false
  moveable = false
  gravity_affected = false

  override def image = ScaleImage(images.currentImage, 25+strength, 25+strength)
  var noisy = false
}

case class Exploding(explosion: Explosion) extends ItemState(explosion) with OneHitWonder{
  override def actOnCollision(go:GameObject): Unit ={
    if(go != explosion.cause && go.tangible){
      if(!explosion.noisy){SoundDistributor.play("small_explosion"); explosion.noisy = true}
      super.actOnCollision(go)
    }
  }

  override def cleanMeUp: Unit = {
    explosion.goKillYourself
  }
}

object Explosion{
}