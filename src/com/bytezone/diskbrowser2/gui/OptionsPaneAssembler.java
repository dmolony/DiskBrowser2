package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.assembler.AssemblerPreferences;

import javafx.scene.layout.StackPane;

// -----------------------------------------------------------------------------------//
public class OptionsPaneAssembler extends StackPane
// -----------------------------------------------------------------------------------//
{
  AssemblerPreferences assemblerPreferences;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneAssembler (AssemblerPreferences assemblerPreferences)
  // ---------------------------------------------------------------------------------//
  {
    this.assemblerPreferences = assemblerPreferences;
  }
}
