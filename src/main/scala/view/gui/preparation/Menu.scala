package main.scala.view.gui.preparation

import scala.swing.{BorderPanel, MainFrame, Swing}

/**
 * Created by julian on 23.02.16.
 */
class Menu {

  Swing.onEDT{
    lazy val frame = new MainFrame{
      title = "Menu"
      centerOnScreen()
      contents = new BorderPanel{

      }
      maximize()
      visible = true

    }
  }

  lazy val

}
