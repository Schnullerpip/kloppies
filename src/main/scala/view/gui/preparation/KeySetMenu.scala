package main.scala.view.gui.preparation

import java.awt.Color
import javax.swing.border.LineBorder

import main.scala.model.player.KeySet

import scala.swing.event.MouseClicked
import scala.swing.{BorderPanel, Button, FlowPanel, GridPanel, Label, TextField}

/**
  * Created by julian on 06.04.16.
  */
class KeySetMenu extends GridPanel(3, 1){
  var keySet:KeySet = DefaultKeySet_1

  /*------------The Textfields which hold the keyset-------------------------*/
  val up      = new TextField(keySet.up.toString) {columns = 2}
  val down    = new TextField(keySet.down.toString) {columns = 2}
  val left    = new TextField(keySet.left.toString) {columns = 2}
  val right   = new TextField(keySet.right.toString) {columns = 2}
  val attack  = new TextField(keySet.attack.toString) {columns = 2}
  val defense = new TextField(keySet.defense.toString) {columns = 2}
  val jump    = new TextField(keySet.jump.toString) {columns = 2}
  /*-------------------------------------------------------------------------*/

  /*inserting a black border around the component*/
  border = new LineBorder(Color.BLACK)
  /*Simple Label communicating the panels purpose*/
  contents += new Label("KeySet")



  /*---------------------Panel offering default KeySets------------*/
  contents += new FlowPanel(){
    contents += new Button("WASD"){
      listenTo(mouse.clicks)
      reactions += {
        case mc:MouseClicked if keySet != DefaultKeySet_1 =>
          keySet = DefaultKeySet_1
          changeKeySetRoutine
      }
    }
    contents += new Button("HJKL"){
      listenTo(mouse.clicks)
      reactions += {
        case mc:MouseClicked if keySet != DefaultKeySet_2 =>
          keySet = DefaultKeySet_2
          changeKeySetRoutine
      }
    }
  }
  /*---------------------------------------------------------------*/



  /*adding the labels and the textfields, representing the keyset*/
  contents += new GridPanel(0, 2) {
    contents += new BorderPanel() { layout += new Label("Up") -> BorderPanel.Position.East }
    contents += new BorderPanel() { layout += up -> BorderPanel.Position.West }
    contents += new BorderPanel() { layout += new Label("Down") -> BorderPanel.Position.East }
    contents += new BorderPanel() { layout += down -> BorderPanel.Position.West }
    contents += new BorderPanel() { layout += new Label("Left") -> BorderPanel.Position.East }
    contents += new BorderPanel() { layout += left -> BorderPanel.Position.West }
    contents += new BorderPanel() { layout += new Label("Right") -> BorderPanel.Position.East }
    contents += new BorderPanel() { layout += right -> BorderPanel.Position.West }
    contents += new BorderPanel() { layout += new Label("Attack") -> BorderPanel.Position.East }
    contents += new BorderPanel() { layout += attack -> BorderPanel.Position.West }
    contents += new BorderPanel() { layout += new Label("Jump") -> BorderPanel.Position.East }
    contents += new BorderPanel() { layout += jump -> BorderPanel.Position.West }
    contents += new BorderPanel() { layout += new Label("Defense") -> BorderPanel.Position.East }
    contents += new BorderPanel() { layout += defense -> BorderPanel.Position.West }
  }

  def get = KeySet(up, down, left, right, attack, defense, jump)
  def next = {
    keySet match {
      case DefaultKeySet_1 => keySet = DefaultKeySet_2
      case DefaultKeySet_2 => keySet = DefaultKeySet_3
      case _ =>
    }
    changeKeySetRoutine
  }

  private def changeKeySetRoutine:Unit = {
    up.text = keySet.up
    down.text = keySet.down
    left.text = keySet.left
    right.text = keySet.right
    attack.text = keySet.attack
    defense.text = keySet.defense
    jump.text = keySet.jump
  }

  private implicit def charToString(c:Char):String = c.toString
  private implicit def textfieldToChar(t:TextField):Char = t.text.charAt(0)
}

object DefaultKeySet_1 extends KeySet(){ }

object DefaultKeySet_2 extends KeySet('k', 'j', 'h','l', 'u', 'p', 'o'){}

private object DefaultKeySet_3 extends KeySet('c', 'v', 'b','n', 'm', 'y', 'x'){}
