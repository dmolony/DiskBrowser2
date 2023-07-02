package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appleformat.ApplePreferences;

// -----------------------------------------------------------------------------------//
public class FileFilterPreferences extends ApplePreferences
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_FILE_TYPE = "FileType-";

  boolean[] showFileTypes = new boolean[16];
  String[] labels = { "po", "dsk", "do", "hdv", "2mg", "d13", "sdk", "shk", "bxy", "bny",
      "bqy", "woz", "img", "dimg", "zip", "gz" };

  // ---------------------------------------------------------------------------------//
  public FileFilterPreferences (Preferences preferences)
  // ---------------------------------------------------------------------------------//
  {
    super ("File Filter Preferences", preferences);

    for (int i = 0; i < labels.length; i++)
      showFileTypes[i] = preferences.getBoolean (PREFS_FILE_TYPE + labels[i], true);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save ()
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < labels.length; i++)
      preferences.putBoolean (PREFS_FILE_TYPE + labels[i], showFileTypes[i]);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder (super.toString ());

    for (int i = 0; i < labels.length; i++)
      text.append (String.format ("%-3s ............ %s%n", labels[i], showFileTypes[i]));

    return Utility.rtrim (text);
  }
}
