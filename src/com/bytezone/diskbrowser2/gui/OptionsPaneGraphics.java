package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.PreferencesFactory;
import com.bytezone.appleformat.graphics.GraphicsPreferences;

// -----------------------------------------------------------------------------------//
public class OptionsPaneGraphics extends PreferencesPane
// -----------------------------------------------------------------------------------//
{
  GraphicsPreferences graphicsPreferences = PreferencesFactory.graphicsPreferences;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneGraphics ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 5);                             // columns, rows

    setColumnConstraints (150, 30);           // column widths
    setPadding (defaultInsets);               // only the root pane has insets
  }
}
