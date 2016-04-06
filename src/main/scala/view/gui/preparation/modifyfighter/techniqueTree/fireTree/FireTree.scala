package main.scala.view.gui.preparation.modifyfighter.techniqueTree.fireTree

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.Techniques
import main.scala.view.gui.preparation.modemenu.ModeMenu
import main.scala.view.gui.preparation.modifyfighter.techniqueTree.TechniqueTreeMenu
import main.scala.view.gui.preparation.modifyfighter.{ModifyFighter, TechniqueComponent}

import scala.swing.event.MouseClicked
import scala.swing.{Dimension, Button, Component, GridPanel}

/**
 * Created by julian on 02.03.16.
 */
class FireTree(val f:Fighter, parent:TechniqueTreeMenu) extends GridPanel(0,2){
  FireTree.techniques.foreach{ t =>
    val technique = Techniques.newTechnique(t, f)
    contents += new TechniqueComponent(technique)
    contents += {
      if (f.techniques.values.toSeq.exists(_.name == t))
        new Component {}
      else
        new Button("+") {
          preferredSize = new Dimension(10, 10)
          listenTo(mouse.clicks)
          reactions += {
            case e: MouseClicked =>
              f.newTechnique(technique, "TODO")
              parent.update
              parent.dispose()
          }
        }
    }
  }
}

object FireTree{
  val techniques = Seq("ThrowFireball")
}
