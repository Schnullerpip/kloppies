package main.scala.model.fighter.animations

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.{FighterState, Hurt}
import main.scala.model.intention.Harmful

/**
 * Created by julian on 15.02.16.
 * A sequence of Punching pictures taen from the ImageMatrix
 */
class Punch(f:Fighter) extends Animator(f){
  override def run(){
    val before = f.state
    var done = false
    f.moveable = false
    before.asInstanceOf[FighterState].hit
    Thread.sleep(1000/f.speed)
    while(!done && f.state.isInstanceOf[Hurt]){
      if(f.images.next.c != f.cols){
        Thread.sleep(1000/f.speed)
      }else{done = true; f.intention = Harmful}
    }
    f.moveable = true
    f.state = before
  }
}

object Punch{
  def apply(f:Fighter) = new Thread(new Punch(f)).start()
}
