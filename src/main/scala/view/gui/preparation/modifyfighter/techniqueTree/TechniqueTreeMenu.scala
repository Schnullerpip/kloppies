package main.scala.view.gui.preparation.modifyfighter.techniqueTree

import javax.swing.JFrame

import main.scala.model.fighter.Fighter
import main.scala.model.fighter.states.techniques.{TechniqueLists, Techniques}
import main.scala.view.gui.preparation.modemenu.ModeMenu
import main.scala.view.gui.preparation.modifyfighter.ModifyFighter
import main.scala.view.gui.preparation.modifyfighter.techniqueTree.fireTree.FireTree

import scala.swing.{Frame, TabbedPane}
import scala.swing.TabbedPane.Page

/**
 * Created by julian on 02.03.16.
 */
class TechniqueTreeMenu(f:Fighter, toUpdate:ModeMenu) extends Frame{
  title = "Techniques"
  centerOnScreen()
  maximize()
  //peer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  //val pags = Seq(
  //  new Page("Fire", new FireTree(f, this))
  //)
  val pags = Seq(
    new Page("Fire", new TechniqueTree(for(t <- TechniqueLists.fireTechniques) yield Techniques.newTechnique(t, f), this)),
    new Page("Water", new TechniqueTree(for(t <- TechniqueLists.waterTechniques) yield Techniques.newTechnique(t, f), this)),
    new Page("Shock", new TechniqueTree(for(t <- TechniqueLists.shockTechniques) yield Techniques.newTechnique(t, f), this)),
    new Page("Earth", new TechniqueTree(for(t <- TechniqueLists.earthTechniques) yield Techniques.newTechnique(t, f), this)),
    new Page("Wind", new TechniqueTree(for(t <- TechniqueLists.windTechniques) yield Techniques.newTechnique(t, f), this))
  )

  contents = new TabbedPane {
    pags.foreach{pages += _}
  }
  visible = true

  def update = {
    toUpdate.defaultActionProcedureModifyFighter(new ModifyFighter(f, toUpdate))
  }
}

