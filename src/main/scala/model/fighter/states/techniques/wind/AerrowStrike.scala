package main.scala.model.fighter.states.techniques.wind

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{FighterState, Normal}
import main.scala.model.fighter.states.midair.Levitate
import main.scala.model.fighter.states.techniques.{Technique, Techniques}
import main.scala.model.intention.{Harmful, Harmless}
import main.scala.model.items.normal.DustyLeave
import main.scala.model.states.MidAir
import main.scala.util.sound.SoundDistributor

/**
  * Created by julian on 15.06.16.
  */
case class AerrowStrike(override val caster:Fighter) extends Technique(caster) with WindTechnique{
  caster.moveable = false
  override val name: String = "AerrowStrike"
  override def act: Unit = {
    caster.state = Fighter_Aerrow_Strike_Attack_State(caster)
    new Thread(new Runnable {
      override def run(): Unit = {
        Thread.sleep(500)
        if(caster.state.isInstanceOf[Fighter_Aerrow_Strike_Attack_State])
          caster.state.stop
      }
    }).start()
  }
  override val manaUse: Int = Techniques.MANA_USE_TECHNIQUE_LEVEL_2
  override val description: String = "shoots Fighter forwards, unleashing powerful attack, when crossing paths with a foe"
  override val image: BufferedImage = ImageIO.read(new File("images/techniques/aerrowStrike.png"))
}

private case class Fighter_Aerrow_Strike_Attack_State(f:Fighter) extends FighterState(f){
  f.z_velocity = 0
  f.gravity_affected = false
  f.images.set(ImageMatrix.RUNNING_HIT, 3)
  f.x_velocity += (f.x_velocity + 40) * f.directionValue
  f.notifyObservers(new DustyLeave(f.x, f.y+1, f.z, f.looksLeft))
  SoundDistributor.play("passing_woosh")
  override def actOnCollision(go:GameObject): Unit ={
    if(go.vulnerable && go.tangible && go.collidable)
      f.state = Fighter_Aerrow_Strike_Hit_State(f, go)
  }
  override def stop: Unit = {
    f.x_velocity = 0
    f.gravity_affected = true
    f.moveable = true
    if(f.state.isInstanceOf[MidAir])f.state = Levitate(f)
    else f.state = Normal(f)
  }
}

private case class Fighter_Aerrow_Strike_Hit_State(f:Fighter, opponent:GameObject) extends FighterState(f){
  val velocityfactor = f.velocity_factor
  f.x_velocity = 0
  new Thread(new Runnable {
    override def run(): Unit = {
      for(i <- 3 until f.images.cols){
        f.images.next
        Thread.sleep(1000/f.speed/3)
       }
      f.intention = Harmful
      SoundDistributor.play("small_punch")
      f.state.inflictDamageTo(opponent, f.strength+velocityfactor)
      f.intention = Harmless
      stop
    }
  }).start()

  override def stop = {
    f.gravity_affected = true
    f.moveable = true
    if(f.state.isInstanceOf[MidAir])f.state = Levitate(f)
    else f.state = Normal(f)
  }
}


