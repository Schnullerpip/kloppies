package main.scala.model.map

import main.scala.model.Position

/**
 * Created by julian on 14.02.16.
 * Describes the plane that a stage spans. as long as the fighter is on said stage
 */
case class Stage (length:Int, width:Int, height:Int, override var x:Int, override var y:Int, override var z:Int) extends Position
