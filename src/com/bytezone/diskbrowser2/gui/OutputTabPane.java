package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabPaneBase;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class OutputTabPane extends TabPaneBase
// -----------------------------------------------------------------------------------//
{
  final HexTab hexTab = new HexTab ("Hex", KeyCode.X);
  final OutputTab outputTab = new OutputTab ("Output", KeyCode.O);
  final MetaTab metaTab = new MetaTab ("Meta", KeyCode.M);

  // ---------------------------------------------------------------------------------//
  OutputTabPane (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    add (metaTab);
    add (hexTab);
    add (outputTab);

    setDefaultTab (2);
  }
}
