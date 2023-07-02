package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.DataLayout;

import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class OptionsPaneFileFilter extends PreferencesPane
// -----------------------------------------------------------------------------------//
{
  CheckBox[] checkBoxes;
  FileFilterPreferences fileFilterPreferences =
      new FileFilterPreferences (Preferences.userNodeForPackage (this.getClass ()));
  String[] labels = { "po", "dsk", "do", "hdv", "2mg", "d13", "sdk", "shk", "bxy", "bny",
      "bqy", "woz", "img", "dimg", "zip", "gz" };

  // ---------------------------------------------------------------------------------//
  public OptionsPaneFileFilter ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 12);                            // columns, rows

    setColumnConstraints (160, 30);           // column widths
    setPadding (defaultInsets);               // only the root pane has insets
    //    setGridLinesVisible (true);

    createLabelsVertical (labels, 0, 5, HPos.RIGHT);
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 5, labels.length, Pos.CENTER, true));

    set ();       // must happen before the listener is added because java is brain-dead

    for (CheckBox checkBox : checkBoxes)
      checkBox.selectedProperty ().addListener (this::changeListener);
  }

  // ---------------------------------------------------------------------------------//
  private void changeListener (ObservableValue<? extends Boolean> observableValue,
      Boolean prevState, Boolean currentState)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < labels.length; i++)
      fileFilterPreferences.showFileTypes[0] = checkBoxes[0].isSelected ();

    notifyListeners (fileFilterPreferences);
  }

  // ---------------------------------------------------------------------------------//
  void set ()
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < labels.length; i++)
      checkBoxes[i].setSelected (fileFilterPreferences.showFileTypes[i]);
  }
}
