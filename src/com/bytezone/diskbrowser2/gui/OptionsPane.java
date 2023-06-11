package com.bytezone.diskbrowser2.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

// -----------------------------------------------------------------------------------//
public class OptionsPane extends StackPane
// -----------------------------------------------------------------------------------//
{
  Button okButton = getButton ("OK");
  Button cancelButton = getButton ("Cancel");

  // ---------------------------------------------------------------------------------//
  public OptionsPane ()
  // ---------------------------------------------------------------------------------//
  {
    getChildren ().add (okButton);
  }

  // ---------------------------------------------------------------------------------//
  private Button getButton (String text)
  // ---------------------------------------------------------------------------------//
  {
    Button button = new Button (text);
    button.setMinWidth (100);

    return button;
  }
}
