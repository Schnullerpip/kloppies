package main.scala.dao

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.Techniques


/**
 * Created by julian on 23.02.16.
 */
case class StorableFighter(var name:String, var imagesName:String, var xp:Int, var hp:Int, var strength:Int,
                           var speed:Int, var mana:Int, var mass:Int,
                           var technique_names:java.util.ArrayList[String],
                           var technique_combinations:java.util.ArrayList[String]) {
  def toFighter:Fighter = {
    import scala.collection.JavaConverters._
    val t_names = technique_names.asScala.toIndexedSeq
    val t_combinations = technique_combinations.asScala.toIndexedSeq
    val ret = Fighter(name, imagesName, xp, hp, strength, speed, mana, mass)
    for(i <- t_names.indices){
      ret.techniques.put(t_combinations(i), Techniques.newTechnique(t_names(i), ret))
    }
    ret
  }

}
