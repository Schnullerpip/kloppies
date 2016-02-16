package main.scala.model.fighter.animations

import main.scala.model.GameObject
import main.scala.model.fighter.Fighter
import main.scala.model.ImageMatrix.OUCH

/**
 * Created by julian on 15.02.16.
 * the animation sequence when a fighter got hit and is struggling shortly
 */
class Struggle(f:Fighter, agressor:GameObject) extends Animator(f) {
  override def run(){
    var done = false
    f.moveable = false
    f.images.set(OUCH)
    Thread.sleep(250)
    while (!done){
     if(f.images.next.c != f.cols){
       Thread.sleep(250)
     }else done = true
    }
    f.moveable = true
  }
}

object Struggle{
  def apply(f:Fighter, agressor:GameObject) = new Thread(new Struggle(f, agressor)).start()
}
