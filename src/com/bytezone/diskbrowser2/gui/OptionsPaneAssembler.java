package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.Preferences;
import com.bytezone.appleformat.assembler.AssemblerPreferences;

import javafx.scene.layout.Pane;

// -----------------------------------------------------------------------------------//
public class OptionsPaneAssembler extends OptionsPane
// -----------------------------------------------------------------------------------//
{
  AssemblerPreferences assemblerPreferences;
  OptionsPane2Assembler optionsPane2Assembler;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneAssembler (AssemblerPreferences assemblerPreferences)
  // ---------------------------------------------------------------------------------//
  {
    this.assemblerPreferences = assemblerPreferences;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  Pane createPane ()
  // ---------------------------------------------------------------------------------//
  {
    optionsPane2Assembler = new OptionsPane2Assembler ();
    return optionsPane2Assembler;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void preferenceChanged (Preferences preferences)
  // ---------------------------------------------------------------------------------//
  {
    if (preferences instanceof AssemblerPreferences assemblerPreferences)
      System.out.println (assemblerPreferences);
  }
}
