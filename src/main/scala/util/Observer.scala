package main.scala.util

import main.scala.model.GameObject

/**
 * Created by julian on 14.02.16.
 */
trait Observer {
  def update
  def update(go: GameObject)
}
