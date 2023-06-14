package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.Preferences;
import com.bytezone.appleformat.text.TextPreferences;

import javafx.scene.layout.Pane;

//-----------------------------------------------------------------------------------//
public class OptionsPaneText extends OptionsPane
//-----------------------------------------------------------------------------------//
{
  TextPreferences textPreferences;
  OptionsPane2Text optionsPane2Text;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneText (TextPreferences textPreferences)
  // ---------------------------------------------------------------------------------//
  {
    this.textPreferences = textPreferences;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  Pane createPane ()
  // ---------------------------------------------------------------------------------//
  {
    optionsPane2Text = new OptionsPane2Text ();
    return optionsPane2Text;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void preferenceChanged (Preferences preferences)
  // ---------------------------------------------------------------------------------//
  {
    if (preferences instanceof TextPreferences textPreferences)
      System.out.println (textPreferences);
  }
}
