package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.basic.ApplesoftBasicPreferences;

import javafx.scene.layout.Pane;

// -----------------------------------------------------------------------------------//
public class OptionsPaneApplesoft extends OptionsPane
// -----------------------------------------------------------------------------------//
{
  ApplesoftBasicPreferences applesoftBasicPreferences;
  OptionsPane2Applesoft optionsPane2Applesoft;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneApplesoft (ApplesoftBasicPreferences applesoftBasicPreferences)
  // ---------------------------------------------------------------------------------//
  {
    this.applesoftBasicPreferences = applesoftBasicPreferences;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  Pane createPane ()
  // ---------------------------------------------------------------------------------//
  {
    optionsPane2Applesoft = new OptionsPane2Applesoft ();
    return optionsPane2Applesoft;
  }
}
