package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.Preferences;
import com.bytezone.appleformat.graphics.GraphicsPreferences;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

//-----------------------------------------------------------------------------------//
public class OptionsPaneGraphics extends OptionsPane
//-----------------------------------------------------------------------------------//
{
  GraphicsPreferences graphicsPreferences;
  CheckBox[] checkBoxes;

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
    GridPane gridPane = new GridPane ();
    return gridPane;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void preferenceChanged (Preferences preferences)
  // ---------------------------------------------------------------------------------//
  {
    if (preferences instanceof GraphicsPreferences graphicsPreferences)
      System.out.println (graphicsPreferences);
  }
}
