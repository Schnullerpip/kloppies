package main.scala.dao.db4o

import com.db4o.{ObjectSet, Db4oEmbedded}
import main.scala.dao.DAO
import main.scala.model.fighter.Fighter
import main.scala.dao.DEFAULT_DATABASE_NAME

/**
 * Created by julian on 14.02.16.
 */
object DB4O extends DAO{
  val connection = Db4oEmbedded.openFile(DEFAULT_DATABASE_NAME)
  override def store(fighter: Fighter) = connection.store(fighter)
  override def query:ObjectSet[Fighter] = connection.query(classOf[Fighter])
}
