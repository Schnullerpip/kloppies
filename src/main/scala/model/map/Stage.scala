package main.scala.model.map

import main.scala.model.Position

/**
 * Created by julian on 14.02.16.
 * Describes the plane that a stage spans. as long as the fighter is on said stage
 */
case class Stage (override var x:Int = -20000,
                  override var y:Int = -20000,
                  override var z:Int = 0,
                  width:Int = 1000000,
                  height:Int = 1000000) extends Position
