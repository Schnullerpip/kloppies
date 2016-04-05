package main.scala.controller

import main.scala.model.GameObject
import main.scala.model.attributes.Speed
import main.scala.model.items.magical.shock.ElectricSpark
import main.scala.model.states.{OneHitWonder, AnimateMe}

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

  val timer = go.state match {
    case ohw: OneHitWonder =>
      new javax.swing.Timer(sleepTime, Swing.ActionListener { _ =>
        if (pool.contains(go)) {
          if (go.images.next.c >= go.images.cols-1) {
            stopTimer()
            ohw.cleanMeUp
          }
        }else{
            stopTimer()
            ohw.cleanMeUp
          }
      })
    case _ =>
      new javax.swing.Timer(sleepTime, Swing.ActionListener { _ =>
        if (pool.contains(go)) {
          if (go.state.isInstanceOf[AnimateMe]) {
            go.images.next
          }
        } else
          stopTimer()
      })
  }

  def stopTimer():Unit = timer.stop()

  timer.start()
}
