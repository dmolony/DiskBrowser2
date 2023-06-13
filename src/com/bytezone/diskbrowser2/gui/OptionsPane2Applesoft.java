package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appbase.DataPane;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class OptionsPane2Applesoft extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes;

  // ---------------------------------------------------------------------------------//
  public OptionsPane2Applesoft ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 20, 20);                          // columns, rows, row height

    setColumnConstraints (150, 30);             // column widths

    String[] labels = { "User format", "Apple line wrap", "Split REM", "Split DIM",
        "Align assign", "Show caret", "Show THEN", "Blank after RETURN", "Format REM",
        "Delete extra DATA space", "Show all Xref", "Show GOTO/GOSUB", "Show CALL",
        "Show symbols", "Show constants", "Show functions", "Show duplicate symbols", };

    createLabelsVertical (labels, 0, 0, HPos.RIGHT);
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 0, labels.length, Pos.CENTER, true));
  }
}
