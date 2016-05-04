package main.scala.model.fighter.states

import main.scala.model.{GameObject, ImageMatrix}
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.midair.Jumping

/**
  * Created by julian on 04.05.16.
  */
case class Loading(f:Fighter) extends FighterState(f){
  val jump_last = System.currentTimeMillis()


  var contin = true
  val moveTrhead = new Thread(new Runnable {
    override def run(): Unit = {
      f.images.set(ImageMatrix.LOADING)
      ifStillLoading{
        f.images.next
        ifStillLoading {
          f.images.next
          ifStillLoading {
            f.images.next
            //loop the last three images
            var index = 4
            while(f.state.isInstanceOf[Loading]){
              for(i <- 4 until 6){
                ifStillLoading{
                  f.images.set(ImageMatrix.LOADING, i)
                }
              }
            }
          }
        }
      }
    }
  }).start()

  override def hit = {}
  override def moveUp = {}
  override def moveDown = {}
  override def moveLeft = {}
  override def moveRight = {}
  override def jump = {}
  override def stop = {
    val max = 1000.0
    val diff = (System.currentTimeMillis - jump_last).toDouble
    val factor = { if(diff > max) max else diff }/max
    contin = false
    f.state = new Jumping(f, factor)
  }
  override def hurtBy(go:GameObject): Unit ={
    contin = false
    super.hurtBy(go)
  }


  private def ifStillLoading(b: => Unit): Unit = {
    if (f.state.isInstanceOf[Loading] && contin) {
      Thread.sleep(1000 / f.speed / 2)
      b
    }
  }
}
