package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.text.TextPreferences;

import javafx.scene.layout.StackPane;

//-----------------------------------------------------------------------------------//
public class OptionsPaneText extends StackPane
//-----------------------------------------------------------------------------------//
{
  TextPreferences textPreferences;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneText (TextPreferences textPreferences)
  // ---------------------------------------------------------------------------------//
  {
    this.textPreferences = textPreferences;
  }
}
