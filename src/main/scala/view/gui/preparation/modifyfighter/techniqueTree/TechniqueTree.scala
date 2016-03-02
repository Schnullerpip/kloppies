package main.scala.view.gui.preparation.modifyfighter.techniqueTree

import main.scala.model.fighter.Fighter
import main.scala.view.gui.preparation.modemenu.ModeMenu
import main.scala.view.gui.preparation.modifyfighter.ModifyFighter
import main.scala.view.gui.preparation.modifyfighter.techniqueTree.fireTree.FireTree

import scala.swing.{Frame, TabbedPane}
import scala.swing.TabbedPane.Page

/**
 * Created by julian on 02.03.16.
 */
class TechniqueTree(f:Fighter, toUpdate:ModeMenu) extends Frame{
  title = "TODO"
  centerOnScreen()
  val pags = Seq(
    new Page("Fire", new FireTree(f, this))
  )

  contents = new TabbedPane {
    pags.foreach{pages += _}
  }
  visible = true

  def update = {
    toUpdate.defaultActionProcedureModifyFighter(new ModifyFighter(f, toUpdate))
  }
}
