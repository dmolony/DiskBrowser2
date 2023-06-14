package com.bytezone.diskbrowser2.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

// -----------------------------------------------------------------------------------//
public abstract class OptionsPane extends BorderPane
// -----------------------------------------------------------------------------------//
{
  Button okButton = getButton ("OK");
  Button cancelButton = getButton ("Cancel");

  // ---------------------------------------------------------------------------------//
  public OptionsPane ()
  // ---------------------------------------------------------------------------------//
  {
    okButton.setDefaultButton (true);
    cancelButton.setCancelButton (true);

    setBottom (okButton);
    setMargin (okButton, new Insets (10, 10, 10, 10));

    setCenter (createPane ());

    //    scrollPane.setPadding (new Insets (5, 5, 5, 5));
    setStyle ("-fx-background: white;-fx-border-color: lightgray;");
  }

  // ---------------------------------------------------------------------------------//
  private Button getButton (String text)
  // ---------------------------------------------------------------------------------//
  {
    Button button = new Button (text);
    button.setMinWidth (100);

    return button;
  }

  // ---------------------------------------------------------------------------------//
  abstract Pane createPane ();
  // ---------------------------------------------------------------------------------//
}
