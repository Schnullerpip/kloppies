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

  def ===(p:Position):Boolean = y == p.y
  def <(p:Position):Boolean =   y <  p.y
  def >(p:Position):Boolean =   y >  p.y
  def <=(p:Position):Boolean =  y <= p.y
  def >=(p:Position):Boolean =  y >= p.y
}
