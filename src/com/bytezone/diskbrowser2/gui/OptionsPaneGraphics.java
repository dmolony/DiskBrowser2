package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.appleformat.graphics.GraphicsPreferences;

// -----------------------------------------------------------------------------------//
public class OptionsPaneGraphics extends OptionsPane2
// -----------------------------------------------------------------------------------//
{
  GraphicsPreferences graphicsPreferences = FormattedAppleFileFactory.graphicsPreferences;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneGraphics ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 5, 20);                         // columns, rows, row height

    setColumnConstraints (150, 30);           // column widths
    setPadding (defaultInsets);               // only the root pane has insets

    //    String[] labels = { "Show targets", "Show strings", "Offset from zero" };
    //
    //    createLabelsVertical (labels, 0, 0, HPos.RIGHT);
    //    checkBoxes =
    //        createCheckBoxes (new DataLayout (1, 0, labels.length, Pos.CENTER, true));
  }
}
