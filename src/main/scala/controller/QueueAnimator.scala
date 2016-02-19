package main.scala.controller

import main.scala.model.GameObject
import main.scala.model.attributes.Speed
import main.scala.model.states.AnimateMe

import scala.swing.Swing

/**
 * Created by julian on 14.02.16.
 * An animator
 */
case class QueueAnimator (go:GameObject, pool:Seq[GameObject], sleep:Int = 250) {
  def sleepTime = go match {
    case s: Speed =>
      val animationFPS = 1000 / s.speed
     // if(animationFPS > GAME_SPEED) GAME_SPEED
     // else animationFPS
      animationFPS
    case _ => sleep
  }

  def stopTimer():Unit = timer.stop()

  val timer = new javax.swing.Timer(sleepTime, Swing.ActionListener{ _ =>
    if(pool.contains(go)){
      if(go.state.isInstanceOf[AnimateMe]){
        go.images.next
        //println(s"$go's images running at ${1000/sleepTime} FPS -> sleeptime $sleepTime")
      }
    }else stopTimer()
  })

  timer.start()
}
