package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.appleformat.assembler.AssemblerPreferences;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class OptionsPaneAssembler extends OptionsPane2
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes;

  AssemblerPreferences assemblerPreferences =
      FormattedAppleFileFactory.assemblerPreferences;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneAssembler ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 5, 20);                         // columns, rows, row height

    setColumnConstraints (150, 30);           // column widths
    setPadding (defaultInsets);               // only the root pane has insets

    String[] labels = { "Show targets", "Show strings", "Offset from zero" };

    createLabelsVertical (labels, 0, 0, HPos.RIGHT);
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 0, labels.length, Pos.CENTER, true));
  }
}
