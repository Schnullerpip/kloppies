package main.scala.model

/**
 * Created by julian on 14.02.16.
 * Every entity, that is an actual Object on the Map needs to be represented by three coordinates x, y, z to
 * be able to be positioned on the Map
 */
trait Position {
  var x, y, z:Int
  def moveTo(x_new:Int, y_new:Int, z_new:Int): Unit ={
    x = x_new
    y = y_new
    z = z_new
  }

  private def getDepth = this match {
    case s:Size => y+s.width
    case _ => y
  }

  def ===(p:Position):Boolean = getDepth == p.getDepth
  def <(p:Position):Boolean =   getDepth <  p.getDepth
  def >(p:Position):Boolean =   getDepth >  p.getDepth
  def <=(p:Position):Boolean =  getDepth <= p.getDepth
  def >=(p:Position):Boolean =  getDepth >= p.getDepth
}
