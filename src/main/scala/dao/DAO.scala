package main.scala.dao

import main.scala.model.fighter.Fighter

/**
 * Created by julian on 14.02.16.
 */
trait DAO {
  def store(f:Fighter)
  def query:Seq[Fighter]
  def close
}
