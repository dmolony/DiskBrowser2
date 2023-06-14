package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appbase.DataPane;
import com.bytezone.appleformat.text.TextPreferences;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class OptionsPane2Text extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes;

  TextPreferences textPreferences;
  List<PreferenceChangeListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public OptionsPane2Text ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 5, 20);                         // columns, rows, row height

    setColumnConstraints (150, 30);           // column widths
    setPadding (defaultInsets);               // only the root pane has insets

    String[] labels = { "Show offsets", "Show .S as Merlin" };

    createLabelsVertical (labels, 0, 0, HPos.RIGHT);
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 0, labels.length, Pos.CENTER, true));
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
      listener.preferenceChanged (textPreferences);
  }
}
