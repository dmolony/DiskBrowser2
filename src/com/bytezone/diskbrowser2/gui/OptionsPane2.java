package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appbase.DataPane;
import com.bytezone.appleformat.Preferences;

// -----------------------------------------------------------------------------------//
public class OptionsPane2 extends DataPane
// -----------------------------------------------------------------------------------//
{
  List<PreferenceChangeListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public OptionsPane2 (int columns, int rows, int rowHeight)
  // ---------------------------------------------------------------------------------//
  {
    super (columns, rows, rowHeight);
  }

  // ---------------------------------------------------------------------------------//
  void addListener (PreferenceChangeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  void notifyListeners (Preferences preferences)
  // ---------------------------------------------------------------------------------//
  {
    for (PreferenceChangeListener listener : listeners)
      listener.preferenceChanged (preferences);
  }
}
