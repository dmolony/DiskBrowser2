package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.appleformat.text.TextPreferences;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class OptionsPaneText extends OptionsPane2
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes;

  TextPreferences textPreferences = FormattedAppleFileFactory.textPreferences;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneText ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 5, 20);                         // columns, rows, row height

    setColumnConstraints (150, 30);           // column widths
    setPadding (defaultInsets);               // only the root pane has insets

    String[] labels = { "Show offsets", "Show .S as Merlin" };

    createLabelsVertical (labels, 0, 0, HPos.RIGHT);
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 0, labels.length, Pos.CENTER, true));
  }
}
