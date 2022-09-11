package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabBase;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class DBOptionsTab extends TabBase
// -----------------------------------------------------------------------------------//
{

  // ---------------------------------------------------------------------------------//
  public DBOptionsTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);
  }
}
