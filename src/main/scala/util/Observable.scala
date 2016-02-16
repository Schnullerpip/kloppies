package main.scala.util

import main.scala.model.GameObject

/**
 * Created by julian on 14.02.16.
 */
trait Observable {
  var observers:Seq[Observer] = Seq()
  def notifyObservers() = observers.foreach{_.update}
  def notifyObservers(go:GameObject) = observers.foreach(_.update(go))
  def addObserver(o:Observer) = observers = o +: observers
  def isObservedBy(o:Observer) = observers.contains(o)
  def rainDownObservers(o:Observable) = {
    o.observers = observers
  }
}
