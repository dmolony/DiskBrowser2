package com.bytezone.diskbrowser2.gui;

import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class OptionsPane extends BorderPane
// -----------------------------------------------------------------------------------//
{
  PreferencesPane optionsPane;

  // ---------------------------------------------------------------------------------//
  public OptionsPane (PreferencesPane optionsPane)
  // ---------------------------------------------------------------------------------//
  {
    this.optionsPane = optionsPane;

    setCenter (optionsPane);

    //    scrollPane.setPadding (new Insets (5, 5, 5, 5));
    setStyle ("-fx-background: white;-fx-border-color: lightgray;");
  }
}
