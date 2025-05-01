package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appleformat.PreferencesFactory;
import com.bytezone.appleformat.assembler.AssemblerPreferences;

import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class OptionsPaneAssembler extends PreferencesPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes;

  AssemblerPreferences assemblerPreferences = PreferencesFactory.assemblerPreferences;

  //Used by FileOptionsTab
  // ---------------------------------------------------------------------------------//
  public OptionsPaneAssembler ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 5);                             // columns, rows

    setColumnConstraints (150, 30);           // column widths
    //    setPadding (defaultInsets);               // only the root pane has insets

    String[] labels = { "Show targets", "Show strings", "Offset from zero" };

    createLabelsVertical (labels, 0, 0, HPos.RIGHT);
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 0, labels.length, Pos.CENTER, true));

    set ();

    for (CheckBox checkBox : checkBoxes)
      checkBox.selectedProperty ().addListener (this::changeListener1);
  }

  // ---------------------------------------------------------------------------------//
  private void changeListener1 (ObservableValue<? extends Boolean> observableValue,
      Boolean prevState, Boolean currentState)
  // ---------------------------------------------------------------------------------//
  {
    assemblerPreferences.showTargets = checkBoxes[0].isSelected ();
    assemblerPreferences.showStrings = checkBoxes[1].isSelected ();
    assemblerPreferences.offsetFromZero = checkBoxes[2].isSelected ();

    notifyListeners (assemblerPreferences);
  }

  // ---------------------------------------------------------------------------------//
  void set ()
  // ---------------------------------------------------------------------------------//
  {
    checkBoxes[0].setSelected (assemblerPreferences.showTargets);
    checkBoxes[1].setSelected (assemblerPreferences.showStrings);
    checkBoxes[2].setSelected (assemblerPreferences.offsetFromZero);
  }
}
