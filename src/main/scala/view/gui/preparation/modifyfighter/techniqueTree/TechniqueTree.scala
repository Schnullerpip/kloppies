package main.scala.view.gui.preparation.modifyfighter.techniqueTree

import javax.swing.ImageIcon

import main.scala.model.fighter.states.techniques.Technique
import main.scala.view.gui.preparation.modifyfighter.TechniqueComponent

import scala.swing.event.MouseClicked
import scala.swing.{BorderPanel, BoxPanel, Button, Component, FlowPanel, Frame, GridPanel, Label, Orientation, TextField}

/**
  * Created by julian on 06.04.16.
  */
class TechniqueTree(techniques:Seq[Technique], f:Frame) extends BorderPanel(){

  /**
    * will hold the clicked or chosen Technique to see details and stuff
    * initially pointing to the lists Head*/
  var watchedTechnique:Option[Technique] = techniques.headOption
  if(watchedTechnique isDefined)
    layout += new TechniqueComponentAddable(watchedTechnique.get, f) -> BorderPanel.Position.South

  /*A Grid showing all the techniques, choosing a specific one by clicking it*/
  layout += new GridPanel(techniques.size, 2){
    techniques foreach{ t => contents += new FlowPanel(){
      contents += new Label(){icon = new ImageIcon(t.image)}
      contents += new Label(t.name)
      listenTo(mouse.clicks)
      reactions += {
        case mc:MouseClicked =>
          watchedTechnique = Some(t)
          addToLayout(new TechniqueComponentAddable(watchedTechnique.get, f) -> BorderPanel.Position.South)
          revalidate
          repaint
      }
    }}
  } -> BorderPanel.Position.Center
  revalidate()

  private def addToLayout(a:(Component, Constraints)) = layout += a
}

class TechniqueComponentAddable(t:Technique, f:Frame) extends TechniqueComponent(t){
  val keyCombination = new TextField(){editable = false; columns = 50}
  val techniqueOption = t.caster.techniques.find(kv => kv._2 == t)
  if(techniqueOption isDefined)
    keyCombination.text = techniqueOption.get._1

  contents += new BoxPanel(Orientation.Vertical){
    contents += new GridPanel(3,2){
      contents += new Button("up"){listenTo(mouse.clicks);reactions += {case mc:MouseClicked =>       addToKeyCombination("up")}}
      contents += new Button("down"){listenTo(mouse.clicks);reactions += {case mc:MouseClicked =>     addToKeyCombination("down")}}
      contents += new Button("left"){listenTo(mouse.clicks);reactions += {case mc:MouseClicked =>     addToKeyCombination("left")}}
      contents += new Button("right"){listenTo(mouse.clicks);reactions += {case mc:MouseClicked =>    addToKeyCombination("right")}}
      contents += new Button("direction"){listenTo(mouse.clicks);reactions += {case mc:MouseClicked =>addToKeyCombination("direction")}}
      contents += new Button("attack"){listenTo(mouse.clicks);reactions += {case mc:MouseClicked =>   addToKeyCombination("attack")}}
      contents += new Button("jump"){listenTo(mouse.clicks);reactions += {case mc:MouseClicked =>     addToKeyCombination("jump")}}
      contents += new Button("defend"){listenTo(mouse.clicks);reactions += {case mc:MouseClicked =>   addToKeyCombination("defend")}}
    }
    contents += new FlowPanel(){
      contents += new Label("Key Combination")
      contents += keyCombination
      contents += new Button("Ok"){listenTo(mouse.clicks);reactions += {
        case mc:MouseClicked =>
          if(techniqueOption isDefined) {
            t.caster.techniques -= techniqueOption.get._1
            t.caster.techniques += keyCombination.text -> t
          }
          else t.caster.newTechnique(t, keyCombination.text)}
        //f.dispose()
        //f.visible = false
        //f.close()
        //revalidate
        //repaint
      }
    }
  }
  private var renewed = false
  private def addToKeyCombination(key:String) = {
    if (techniqueOption.isDefined && !renewed) {
      keyCombination.text = ""
      renewed = true
    }
    keyCombination.text += key
  }
}
