package main.scala.util.sound

import java.io.File
import javax.sound.sampled.{AudioSystem, Clip}

/**
  * Created by julian on 06.04.16.
  */
object SoundDistributer {

  /**
    * the map that holds all the sounds, sounds need to be introduced here as well for consistency reasons
    * all objects need to talk to the SoundDistributor object as well for consistency reasons
    * sounds are initially loaded and ready to be played - whether this is a good idea will be clear later in testing since for example
    * three fireballs all play the same sound right now and therefore interrupting the sound of their previous instances - nnot nice but efficient*/
  private val sounds = Map(
    "small_punch" -> createClip("small_punch"),
    "throw_fireball" -> createClip("throw_fireball"),
    "wind" -> createClip("wind"),
    "wind_loop" -> createClip("wind_loop"),
    "zap" -> createClip("zap")
  )

  def play(clip:String){
    val c = sounds(clip)
    c.setFramePosition(0)
    c.start()
  }
  def loop(clip:String){sounds(clip).loop(Clip.LOOP_CONTINUOUSLY)}
  def stop(clip:String){sounds(clip).stop()}


  private def createClip(file:String):Clip = {
    val clip = AudioSystem.getClip()
    clip.open(AudioSystem.getAudioInputStream(new File(s"sounds/$file.wav")))
    clip
  }

}
