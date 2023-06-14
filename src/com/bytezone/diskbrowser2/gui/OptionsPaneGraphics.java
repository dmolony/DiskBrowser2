package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.graphics.GraphicsPreferences;

import javafx.scene.layout.Pane;

//-----------------------------------------------------------------------------------//
public class OptionsPaneGraphics extends OptionsPane
//-----------------------------------------------------------------------------------//
{
  GraphicsPreferences graphicsPreferences;
  OptionsPane2Graphics optionsPane2Graphics;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneGraphics (GraphicsPreferences graphicsPreferences)
  // ---------------------------------------------------------------------------------//
  {
    this.graphicsPreferences = graphicsPreferences;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  Pane createPane ()
  // ---------------------------------------------------------------------------------//
  {
    optionsPane2Graphics = new OptionsPane2Graphics ();

    return optionsPane2Graphics;
  }
}
