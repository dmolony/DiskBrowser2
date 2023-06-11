package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.graphics.GraphicsPreferences;

import javafx.scene.layout.StackPane;

//-----------------------------------------------------------------------------------//
public class OptionsPaneGraphics extends StackPane
//-----------------------------------------------------------------------------------//
{
  GraphicsPreferences graphicsPreferences;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneGraphics (GraphicsPreferences graphicsPreferences)
  // ---------------------------------------------------------------------------------//
  {
    this.graphicsPreferences = graphicsPreferences;
  }
}
