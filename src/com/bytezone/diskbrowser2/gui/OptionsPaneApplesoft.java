package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appleformat.PreferencesFactory;
import com.bytezone.appleformat.basic.ApplesoftBasicPreferences;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

// Used by FileOptionsTab
// -----------------------------------------------------------------------------------//
public class OptionsPaneApplesoft extends PreferencesPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes1;
  private CheckBox[] checkBoxes2;
  private RadioButton[] radioButtons;

  ApplesoftBasicPreferences applesoftBasicPreferences =
      PreferencesFactory.basicPreferences;

  // ---------------------------------------------------------------------------------//
  public OptionsPaneApplesoft ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 20);                            // columns, rows

    setColumnConstraints (160, 30);           // column widths
    //    setGridLinesVisible (true);

    assert applesoftBasicPreferences != null;

    String[] labels0 =
        { "No format", "User format", "40 column Apple format", "Hex format" };
    DataLayout dataLayout = new DataLayout (0, 1, labels0);
    radioButtons = createRadioButtons (dataLayout);
    ToggleGroup toggleGroup = radioButtons[0].getToggleGroup ();
    toggleGroup.selectedToggleProperty ().addListener (new ChangeListener<Toggle> ()
    {
      @Override
      public void changed (ObservableValue<? extends Toggle> ov, Toggle old_toggle,
          Toggle new_toggle)
      {
        if (toggleGroup.getSelectedToggle () != null)
        {
          for (int i = 0; i < labels0.length; i++)
            if (radioButtons[i].isSelected ())
            {
              applesoftBasicPreferences.displayFormat = i;
              break;
            }
          notifyListeners (applesoftBasicPreferences);
        }
      }
    });

    int labels1Row = 6;
    int labels2Row = 18;

    String[] labels1 = { "Split REM", "Split DIM", "Align assign", "Show caret",
        "Show THEN after IF", "Blank after RETURN", "Format REM",
        "Delete extra DATA space", "Hide LET", "Wrap PRINT" };

    String[] labels2 = { "Show symbols", "Show duplicate symbols", "Show functions",
        "Show constants", "Show GOTO/GOSUB", "Show CALL" };

    createLabelsVertical (labels1, 0, labels1Row, HPos.RIGHT);
    checkBoxes1 = createCheckBoxes (
        new DataLayout (1, labels1Row, labels1.length, Pos.CENTER, true));

    createLabelsVertical (labels2, 0, labels2Row, HPos.RIGHT);
    checkBoxes2 = createCheckBoxes (
        new DataLayout (1, labels2Row, labels2.length, Pos.CENTER, true));

    set ();       // must happen before the listener is added because java is brain-dead

    for (CheckBox checkBox : checkBoxes1)
      checkBox.selectedProperty ().addListener (this::changeListener1);

    for (CheckBox checkBox : checkBoxes2)
      checkBox.selectedProperty ().addListener (this::changeListener2);
  }

  // ---------------------------------------------------------------------------------//
  private void changeListener1 (ObservableValue<? extends Boolean> observableValue,
      Boolean prevState, Boolean currentState)
  // ---------------------------------------------------------------------------------//
  {
    applesoftBasicPreferences.splitRem = checkBoxes1[0].isSelected ();
    applesoftBasicPreferences.splitDim = checkBoxes1[1].isSelected ();
    applesoftBasicPreferences.alignAssign = checkBoxes1[2].isSelected ();
    applesoftBasicPreferences.showCaret = checkBoxes1[3].isSelected ();
    applesoftBasicPreferences.showThen = checkBoxes1[4].isSelected ();
    applesoftBasicPreferences.blankAfterReturn = checkBoxes1[5].isSelected ();
    applesoftBasicPreferences.formatRem = checkBoxes1[6].isSelected ();
    applesoftBasicPreferences.deleteExtraDataSpace = checkBoxes1[7].isSelected ();
    applesoftBasicPreferences.hideLet = checkBoxes1[8].isSelected ();
    applesoftBasicPreferences.wrapPrint = checkBoxes1[9].isSelected ();

    notifyListeners (applesoftBasicPreferences);
  }

  // ---------------------------------------------------------------------------------//
  private void changeListener2 (ObservableValue<? extends Boolean> observableValue,
      Boolean prevState, Boolean currentState)
  // ---------------------------------------------------------------------------------//
  {
    applesoftBasicPreferences.showSymbols = checkBoxes2[0].isSelected ();
    applesoftBasicPreferences.showDuplicateSymbols = checkBoxes2[1].isSelected ();
    applesoftBasicPreferences.showFunctions = checkBoxes2[2].isSelected ();
    applesoftBasicPreferences.showConstants = checkBoxes2[3].isSelected ();
    applesoftBasicPreferences.showGosubGoto = checkBoxes2[4].isSelected ();
    applesoftBasicPreferences.showCalls = checkBoxes2[5].isSelected ();

    notifyListeners (applesoftBasicPreferences);
  }

  // ---------------------------------------------------------------------------------//
  void set ()
  // ---------------------------------------------------------------------------------//
  {
    radioButtons[applesoftBasicPreferences.displayFormat].setSelected (true);

    checkBoxes1[0].setSelected (applesoftBasicPreferences.splitRem);
    checkBoxes1[1].setSelected (applesoftBasicPreferences.splitDim);
    checkBoxes1[2].setSelected (applesoftBasicPreferences.alignAssign);
    checkBoxes1[3].setSelected (applesoftBasicPreferences.showCaret);
    checkBoxes1[4].setSelected (applesoftBasicPreferences.showThen);
    checkBoxes1[5].setSelected (applesoftBasicPreferences.blankAfterReturn);
    checkBoxes1[6].setSelected (applesoftBasicPreferences.formatRem);
    checkBoxes1[7].setSelected (applesoftBasicPreferences.deleteExtraDataSpace);
    checkBoxes1[8].setSelected (applesoftBasicPreferences.hideLet);
    checkBoxes1[9].setSelected (applesoftBasicPreferences.wrapPrint);

    checkBoxes2[0].setSelected (applesoftBasicPreferences.showSymbols);
    checkBoxes2[1].setSelected (applesoftBasicPreferences.showDuplicateSymbols);
    checkBoxes2[2].setSelected (applesoftBasicPreferences.showFunctions);
    checkBoxes2[3].setSelected (applesoftBasicPreferences.showConstants);
    checkBoxes2[4].setSelected (applesoftBasicPreferences.showGosubGoto);
    checkBoxes2[5].setSelected (applesoftBasicPreferences.showCalls);
  }
}
