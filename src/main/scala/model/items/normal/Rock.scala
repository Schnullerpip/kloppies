package main.scala.model.items.normal

import main.scala.model.attributes.Speed
import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.intention.{Harmful, Harmless}
import main.scala.model.items.state.ItemState
import main.scala.model.states.AnimateMe
import main.scala.util.sound.SoundDistributor

/**
  * Created by julian on 19.04.16.
  */
class Rock(x:Int, y:Int, z:Int ) extends Stone(x, y, z) with Speed{
  override var speed: Int = 10
  images = new ImageMatrix("rock.png", this, 8, 6)
  state = new StoneNormal(this)
  width = Rock.rock_width
  height = Rock.rock_height
  length = Rock.rock_length
  mass = width * height
  hp = mass
  override def strength = velocity_factor+10
  full_strength = strength
}

case class RockNormal(rock:Rock) extends ItemState(rock){
  rock.intention = Harmless
}

case class RockMoving(rock:Rock) extends ItemState(rock) with AnimateMe{
  rock.images.set(ImageMatrix.ITEM_MOVE)
  rock.intention = Harmful
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

object Rock{
  ImageMatrix("images/items/rock.png", 8, 6, 50, 50)
  private val rock_width = 50
  private val rock_height = 50
  private val rock_length = 10
  //ImageMatrix("images/items/rock.png", 6, 6, rock_width, rock_height)
  def apply(x:Int, y:Int, z:Int) = new Rock(x, y, z)
}



