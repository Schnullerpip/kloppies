package main.scala.dao.db4o

import com.db4o.Db4oEmbedded
import main.scala.dao.{StorableFighter, DAO, DEFAULT_DATABASE_NAME}
import main.scala.model.fighter.Fighter

/**
 * Created by julian on 14.02.16.
 */
class DB4O extends DAO{
  val connection = Db4oEmbedded.openFile(DEFAULT_DATABASE_NAME)
  override def store(fighter: Fighter) = storeStorableFighter(fighter)
  override def query:Seq[Fighter] = {
    val objset = connection.query(classOf[StorableFighter]).iterator()
    var seq = Seq[Fighter]()
    while(objset hasNext){
      val obj = objset.next().toFighter
      seq = obj +: seq
    }
    seq
  }
  override def close = connection.close()

  private def storeStorableFighter(sf:StorableFighter) = connection.store(sf)


  private implicit def toSF(f:Fighter):StorableFighter={
    StorableFighter(f.name, f.imagesName, f.xp, f.full_hp, f.full_strength, f.full_speed,
                    f.full_mana, f.full_mass, f.techniques)
  }
}

