package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabPaneBase;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class OutputTabPane extends TabPaneBase
// -----------------------------------------------------------------------------------//
{
  final HexTab hexTab = new HexTab ("Hex", KeyCode.H);
  final OutputTab outputTab = new OutputTab ("Data", KeyCode.D);
  final MetaTab metaTab = new MetaTab ("Meta", KeyCode.M);
  final GraphicsTab graphicsTab = new GraphicsTab ("Graphics", KeyCode.G);

  // ---------------------------------------------------------------------------------//
  OutputTabPane (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    add (metaTab);
    add (hexTab);
    add (outputTab);
    add (graphicsTab);

    setDefaultTab (2);
  }
}
