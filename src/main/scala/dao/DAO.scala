package main.scala.dao

import com.db4o.ObjectSet
import main.scala.model.fighter.Fighter

/**
 * Created by julian on 14.02.16.
 */
trait DAO {
  def store(f:Fighter)
  def query:ObjectSet[Fighter]
}
