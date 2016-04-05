package main.scala.dao.db4o

import java.util

import com.db4o.Db4oEmbedded
import main.scala.dao.{StorableFighter, DAO, DEFAULT_DATABASE_NAME}
import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.Technique

/**
 * Created by julian on 14.02.16.
 */
class DB4O extends DAO{
  var fighters = Seq[StorableFighter]()
  val connection = Db4oEmbedded.openFile(DEFAULT_DATABASE_NAME)
  override def store(fighter: Fighter) = storeStorableFighter(fighter)
  override def query:Seq[Fighter] = {
    val objset = connection.query(classOf[StorableFighter]).iterator()
    var seq = Seq[StorableFighter]()
    while(objset hasNext){
      val obj = objset.next()

      seq = obj +: seq
    }
    fighters = seq
    seq.map(_.toFighter)
  }
  override def close = {
    connection.close()
  }

  private def storeStorableFighter(sf:StorableFighter) = connection.store(sf)


  private implicit def toSF(f:Fighter):StorableFighter={
    import scala.collection.JavaConversions._
    val storable = fighters.find(_.name == f.name)
    if(storable isDefined){
      storable.get.imagesName = f.imagesName
      storable.get.xp = f.xp
      storable.get.hp = f.full_hp
      storable.get.strength = f.full_strength
      storable.get.speed = f.full_speed
      storable.get.mana = f.full_mana
      storable.get.mass = f.full_mass
      storable.get.technique_names = new util.ArrayList[String](seqAsJavaList(f.techniques.values.map(_.name).toSeq))
      storable.get.technique_combinations = new util.ArrayList[String](seqAsJavaList(f.techniques.keySet.toSeq))
      storable.get
    }else{
      val technique_names = new util.ArrayList[String](seqAsJavaList(f.techniques.values.map(_.name).toSeq))
      val technique_combinations = new util.ArrayList[String](seqAsJavaList(f.techniques.keySet.toSeq))
      StorableFighter(f.name, f.imagesName, f.xp, f.full_hp, f.full_strength, f.full_speed,
                    f.full_mana, f.full_mass, technique_names, technique_combinations)
    }
  }
}

