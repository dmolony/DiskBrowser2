package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appbase.DataPane;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class OptionsPane2Text extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes;

  // ---------------------------------------------------------------------------------//
  public OptionsPane2Text ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 5, 20);                           // columns, rows, row height

    setColumnConstraints (150, 30);             // column widths

    String[] labels = { "Show offsets", "Show .S as Merlin" };

    createLabelsVertical (labels, 0, 0, HPos.RIGHT);
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 0, labels.length, Pos.CENTER, true));
  }
}