package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

// -----------------------------------------------------------------------------------//
public class PreferencesManager
{
  Preferences prefs;
  FileFilterPreferences fileFilter;

  // ---------------------------------------------------------------------------------//
  public PreferencesManager (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    this.prefs = prefs;

    fileFilter = new FileFilterPreferences (prefs);
  }

  // ---------------------------------------------------------------------------------//
  public void save ()
  // ---------------------------------------------------------------------------------//
  {
    fileFilter.save ();
  }
}
