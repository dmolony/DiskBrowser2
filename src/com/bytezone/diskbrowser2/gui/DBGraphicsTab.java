package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabBase;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class DBGraphicsTab extends TabBase
// -----------------------------------------------------------------------------------//
{
  private final ScrollPane scrollPane = new ScrollPane ();

  // ---------------------------------------------------------------------------------//
  public DBGraphicsTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
    scrollPane.setPadding (new Insets (5, 5, 5, 5));
    scrollPane.setStyle ("-fx-background: white;-fx-border-color: lightgray;");

    setContent (scrollPane);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);

    scrollPane.setVvalue (0);
    scrollPane.setHvalue (0);
  }
}
