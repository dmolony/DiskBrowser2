package com.bytezone.diskbrowser2.gui;

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
      DiskBrowserApp.preferencesManager.fileFilter;
  //      new FileFilterPreferences (Preferences.userNodeForPackage (this.getClass ()));
  TextField[] suffixTotals;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneFileFilter ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 17);                            // columns, rows

    setColumnConstraints (80, 30, 60);        // column widths
    setPadding (defaultInsets);               // only the root pane has insets
    //    setGridLinesVisible (true);

    createLabelsVertical (fileFilterPreferences.suffixes, 0, 1, HPos.RIGHT);
    checkBoxes = createCheckBoxes (
        new DataLayout (1, 1, fileFilterPreferences.suffixes.length, Pos.CENTER, true));
    suffixTotals = createTextFields (new DataLayout (2, 1,
        fileFilterPreferences.suffixes.length, Pos.CENTER_RIGHT, false));

    set ();       // must happen before the listener is added because java is brain-dead

    for (CheckBox checkBox : checkBoxes)
      checkBox.selectedProperty ().addListener (this::changeListener);
  }

  // ---------------------------------------------------------------------------------//
  private void changeListener (ObservableValue<? extends Boolean> observableValue,
      Boolean prevState, Boolean currentState)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < fileFilterPreferences.suffixes.length; i++)
      fileFilterPreferences.setShowFileTypes (i, checkBoxes[i].isSelected ());

    notifyListeners (fileFilterPreferences);
  }

  // ---------------------------------------------------------------------------------//
  void set ()
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < fileFilterPreferences.suffixes.length; i++)
      checkBoxes[i].setSelected (fileFilterPreferences.getShowFileTypes (i));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void totalsChanged (int[] totals)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < fileFilterPreferences.suffixes.length; i++)
      suffixTotals[i].setText (String.format ("%,7d", totals[i]));
  }
}
