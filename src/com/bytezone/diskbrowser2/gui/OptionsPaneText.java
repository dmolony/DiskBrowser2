package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appleformat.PreferencesFactory;
import com.bytezone.appleformat.text.TextPreferences;

import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

//Used by FileOptionsTab
// -----------------------------------------------------------------------------------//
public class OptionsPaneText extends PreferencesPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes;

  TextPreferences textPreferences = PreferencesFactory.textPreferences;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneText ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 5);                             // columns, rows

    setColumnConstraints (150, 30);           // column widths

    String[] labels = { "Show offsets", "Show .S as Merlin" };

    createLabelsVertical (labels, 0, 0, HPos.RIGHT);
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 0, labels.length, Pos.CENTER, true));

    set ();

    for (CheckBox checkBox : checkBoxes)
      checkBox.selectedProperty ().addListener (this::changeListener1);
  }

  // ---------------------------------------------------------------------------------//
  public void set ()
  // ---------------------------------------------------------------------------------//
  {
    checkBoxes[0].setSelected (textPreferences.showTextOffsets);
    checkBoxes[1].setSelected (textPreferences.merlinFormat);
  }

  // ---------------------------------------------------------------------------------//
  private void changeListener1 (ObservableValue<? extends Boolean> observableValue,
      Boolean prevState, Boolean currentState)
  // ---------------------------------------------------------------------------------//
  {
    textPreferences.showTextOffsets = checkBoxes[0].isSelected ();
    textPreferences.merlinFormat = checkBoxes[1].isSelected ();

    notifyListeners (textPreferences);
  }
}
