package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabPaneBase;

import javafx.scene.input.KeyCode;

//-----------------------------------------------------------------------------------//
class ExtrasTabPane extends TabPaneBase
//-----------------------------------------------------------------------------------//
{
  final OptionsTab optionsTab = new OptionsTab ("Options", KeyCode.O);
  final LayoutTab layoutTab = new LayoutTab ("Layout", KeyCode.L);

  // ---------------------------------------------------------------------------------//
  ExtrasTabPane (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    add (optionsTab);
    add (layoutTab);

    setDefaultTab (0);
  }
}
