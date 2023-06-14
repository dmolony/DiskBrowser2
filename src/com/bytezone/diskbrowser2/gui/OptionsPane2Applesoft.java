package com.bytezone.diskbrowser2.gui;

import java.util.List;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appbase.DataPane;
import com.bytezone.appleformat.basic.ApplesoftBasicPreferences;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

// -----------------------------------------------------------------------------------//
public class OptionsPane2Applesoft extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes1;
  private CheckBox[] checkBoxes2;
  private RadioButton[] radioButtons;

  ApplesoftBasicPreferences applesoftBasicPreferences;
  List<PreferenceChangeListener> listeners;

  // ---------------------------------------------------------------------------------//
  public OptionsPane2Applesoft ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 20, 20);                        // columns, rows, row height

    setColumnConstraints (150, 30);           // column widths
    setPadding (defaultInsets);               // only the root pane has insets
    //    setGridLinesVisible (true);

    String[] labels0 = { "Apple format", "No format", "User format" };
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
          System.out.println (toggleGroup.getSelectedToggle ());
          notifyListeners ();
        }
      }
    });

    String[] labels1 = { "Split REM", "Split DIM", "Align assign", "Show caret",
        "Blank after RETURN", "Format REM", "Delete extra DATA space" };

    String[] labels2 = { "Show GOTO/GOSUB", "Show CALL", "Show symbols", "Show constants",
        "Show functions", "Show duplicate symbols" };

    createLabelsVertical (labels1, 0, 5, HPos.RIGHT);
    checkBoxes1 =
        createCheckBoxes (new DataLayout (1, 5, labels1.length, Pos.CENTER, true));

    createLabelsVertical (labels2, 0, 15, HPos.RIGHT);
    checkBoxes2 =
        createCheckBoxes (new DataLayout (1, 15, labels2.length, Pos.CENTER, true));
  }

  // ---------------------------------------------------------------------------------//
  void set (ApplesoftBasicPreferences applesoftBasicPreferences)
  // ---------------------------------------------------------------------------------//
  {
    this.applesoftBasicPreferences = applesoftBasicPreferences;

    if (applesoftBasicPreferences.appleLineWrap)
      radioButtons[0].setSelected (true);
    else if (applesoftBasicPreferences.userFormat)
      radioButtons[2].setSelected (true);
    else
      radioButtons[1].setSelected (true);

    //    checkBoxes1[0].setSelected (applesoftBasicPreferences.userFormat);
    //    checkBoxes1[1].setSelected (applesoftBasicPreferences.appleLineWrap);
    checkBoxes1[0].setSelected (applesoftBasicPreferences.splitRem);
    checkBoxes1[1].setSelected (applesoftBasicPreferences.splitDim);
    checkBoxes1[2].setSelected (applesoftBasicPreferences.alignAssign);
    checkBoxes1[3].setSelected (applesoftBasicPreferences.showCaret);
    //    checkBoxes1[4].setSelected (applesoftBasicPreferences.showThen);
    checkBoxes1[4].setSelected (applesoftBasicPreferences.blankAfterReturn);
    checkBoxes1[5].setSelected (applesoftBasicPreferences.formatRem);
    checkBoxes1[6].setSelected (applesoftBasicPreferences.deleteExtraDataSpace);

    checkBoxes2[0].setSelected (applesoftBasicPreferences.showGosubGoto);
    checkBoxes2[1].setSelected (applesoftBasicPreferences.showCalls);
    checkBoxes2[2].setSelected (applesoftBasicPreferences.showSymbols);
    checkBoxes2[3].setSelected (applesoftBasicPreferences.showConstants);
    checkBoxes2[4].setSelected (applesoftBasicPreferences.showFunctions);
    checkBoxes2[5].setSelected (applesoftBasicPreferences.showDuplicateSymbols);
  }

  // ---------------------------------------------------------------------------------//
  void addListener (PreferenceChangeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  void notifyListeners ()
  // ---------------------------------------------------------------------------------//
  {
    for (PreferenceChangeListener listener : listeners)
      listener.preferenceChanged (applesoftBasicPreferences);
  }
}
