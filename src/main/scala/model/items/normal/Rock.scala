package main.scala.model.rocks.normal

import main.scala.model.attributes.{LivePoints, Speed}
import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.intention.{Harmful, Harmless}
import main.scala.model.items.normal.{Stone, StoneNormal}
import main.scala.model.items.state.{Break, ItemState}
import main.scala.model.states.{AnimateMe, MidAir}
import main.scala.util.sound.SoundDistributor

/**
  * Created by julian on 19.04.16.
  */
class Rock(x:Int, y:Int, z:Int ) extends Stone(x, y, z) with Speed{
  override var speed: Int = 10
  images = new ImageMatrix("rock.png", this, 8, 6)
  state = new RockNormal(this)
  width = Rock.rock_width
  height = Rock.rock_height
  length = Rock.rock_length
  mass = 15
  hp = 200
  override def strength = velocity_factor+10
  full_strength = strength
}

class RockNormal(val rock:Rock) extends ItemState(rock){
  rock.intention = Harmless
  override def hurtBy(go:GameObject) = {
    if(rock.vulnerable) rock.state = new RockHurt(rock, go)()
  }
  override def levitate = {
    rock.state = new RockNormal(rock) with MidAir{ rock.intention = Harmful }
  }

  override def actOnCollision(go:GameObject) = {
    if(rock.moving && rock.velocity_factor > rock.speed )
      SoundDistributor.play("deep_smash")
    super.actOnCollision(go)
  }
  override def landing(go:GameObject) = {
    //TODO create shards on Ground
    go.state.hurtBy(rock)
    SoundDistributor.play("deep_smash")
    val velocity = rock.z_velocity * {if(rock.z_velocity < 0 ) -1 else 1}
    if(velocity > rock.mass){
      rock.z_velocity = velocity/2
    }else{
      rock.x_velocity = 0
      rock.y_velocity = 0
      rock.z_velocity = 0
      rock.state = new RockNormal(rock)
    }
  }
}

case class RockMoving(r:Rock) extends RockNormal(r) with AnimateMe{
  rock.images.set(ImageMatrix.ITEM_MOVE)
  rock.intention = Harmful
}

case class RockHurt(rock:Rock, opponent:GameObject)(amount:Int = opponent.strength) extends ItemState(rock){
  private var contin = true

  if(rock.hp - amount <= 0){
    rock.state = new RockBreak(rock)
  }else{
    rock.takeDamage(amount)
    rock.images.set(ImageMatrix.ITEM_OUCH)

    val sleepTime = 1000 / {rock match {
      case s:Speed => s.speed
      case _ => 150
    }}
    new Thread(new Runnable {
      override def run(): Unit = {
        var i = 0
        rock.images.next
        Thread.sleep(sleepTime)
        while(i < rock.images.cols-2 && contin){
          rock.images.next
          Thread.sleep(sleepTime)
          i += 1
        }
      }
    }).start()
  }
  override def hurtBy(go:GameObject) = {
    contin = false
    rock.state = new RockHurt(rock, go)()
  }
}

case class RockBreak(rock:Rock) extends Break(rock){
  SoundDistributor.play("crumble")
}

object Rock{
  //ImageMatrix("images/items/rock.png", 8, 6, 50, 50)
  private val rock_width = 50
  private val rock_height = 50
  ImageMatrix("images/items/rock.png", 8, 6, rock_width, rock_height)
  private val rock_length = 10
  def apply(x:Int, y:Int, z:Int) = new Rock(x, y, z)
}



