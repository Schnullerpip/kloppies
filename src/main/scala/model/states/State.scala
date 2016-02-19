package main.scala.model.states

import main.scala.model.GameObject

/**
 * Created by julian on 14.02.16.
 * Superclass for all States
 */
abstract class State(val go:GameObject) {
  def actOnCollision(go:GameObject)
  def stop
}
