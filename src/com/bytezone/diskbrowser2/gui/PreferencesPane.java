package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appbase.DataPane;
import com.bytezone.appleformat.Preferences;

// -----------------------------------------------------------------------------------//
public class PreferencesPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  List<PreferenceChangeListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public PreferencesPane (int columns, int rows)
  // ---------------------------------------------------------------------------------//
  {
    super (columns, rows, 20);                // row height
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
