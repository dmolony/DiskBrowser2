package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.DataLayout;
import com.bytezone.diskbrowser2.gui.TreePane.SuffixTotalsListener;

import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

// Used by IncludeFilesTab
// -----------------------------------------------------------------------------------//
public class OptionsPaneFileFilter extends PreferencesPane implements SuffixTotalsListener
// -----------------------------------------------------------------------------------//
{
  CheckBox[] checkBoxes;
  FileFilterPreferences fileFilterPreferences =
      new FileFilterPreferences (Preferences.userNodeForPackage (this.getClass ()));
  String[] suffixes = { "po", "dsk", "do", "hdv", "2mg", "d13", "sdk", "shk", "bxy",
      "bny", "bqy", "woz", "img", "dimg", "zip", "gz" };
  TextField[] suffixTotals;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneFileFilter ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 17);                            // columns, rows

    setColumnConstraints (80, 30, 60);        // column widths
    setPadding (defaultInsets);               // only the root pane has insets
    //    setGridLinesVisible (true);

    createLabelsVertical (suffixes, 0, 1, HPos.RIGHT);
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 1, suffixes.length, Pos.CENTER, true));
    suffixTotals = createTextFields (
        new DataLayout (2, 1, suffixes.length, Pos.CENTER_RIGHT, false));

    set ();       // must happen before the listener is added because java is brain-dead

    for (CheckBox checkBox : checkBoxes)
      checkBox.selectedProperty ().addListener (this::changeListener);
  }

  // ---------------------------------------------------------------------------------//
  private void changeListener (ObservableValue<? extends Boolean> observableValue,
      Boolean prevState, Boolean currentState)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < suffixes.length; i++)
      fileFilterPreferences.showFileTypes[i] = checkBoxes[i].isSelected ();

    notifyListeners (fileFilterPreferences);
  }

  // ---------------------------------------------------------------------------------//
  void set ()
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < suffixes.length; i++)
      checkBoxes[i].setSelected (fileFilterPreferences.showFileTypes[i]);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void totalsChanged (int[] totals)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < suffixes.length; i++)
      suffixTotals[i].setText (String.format ("%,7d", totals[i]));
  }
}
