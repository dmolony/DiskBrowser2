package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

// -----------------------------------------------------------------------------------//
public class PreferencesManager
// -----------------------------------------------------------------------------------//
{
  private final Preferences prefs;
  private final FileFilterPreferences fileFilter;

  // ---------------------------------------------------------------------------------//
  public PreferencesManager (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    this.prefs = prefs;

    fileFilter = new FileFilterPreferences (prefs);
  }

  // ---------------------------------------------------------------------------------//
  FileFilterPreferences getFileFilter ()
  // ---------------------------------------------------------------------------------//
  {
    return fileFilter;
  }

  // ---------------------------------------------------------------------------------//
  void save ()
  // ---------------------------------------------------------------------------------//
  {
    fileFilter.save ();
  }
}
