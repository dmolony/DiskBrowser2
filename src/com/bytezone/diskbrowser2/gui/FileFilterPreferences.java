package com.bytezone.diskbrowser2.gui;

import java.util.prefs.Preferences;

import com.bytezone.appleformat.ApplePreferences;

// -----------------------------------------------------------------------------------//
public class FileFilterPreferences extends ApplePreferences
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_FILE_TYPE = "FileType-";

  String[] suffixes = { "po", "dsk", "do", "hdv", "2mg", "d13", "sdk", "shk", "bxy",
      "bny", "bqy", "woz", "img", "dimg", "zip", "gz" };

  private boolean[] showFileTypes = new boolean[suffixes.length];
  int totalSet;

  // ---------------------------------------------------------------------------------//
  public FileFilterPreferences (Preferences preferences)
  // ---------------------------------------------------------------------------------//
  {
    super ("File Filter Preferences", preferences);

    for (int i = 0; i < suffixes.length; i++)
    {
      showFileTypes[i] = preferences.getBoolean (PREFS_FILE_TYPE + suffixes[i], true);
      if (showFileTypes[i])
        totalSet++;
    }
  }

  // ---------------------------------------------------------------------------------//
  boolean getShowFileTypes (int index)
  // ---------------------------------------------------------------------------------//
  {
    return showFileTypes[index];
  }

  // ---------------------------------------------------------------------------------//
  void setShowFileTypes (int index, boolean value)
  // ---------------------------------------------------------------------------------//
  {
    showFileTypes[index] = value;

    totalSet = 0;
    for (int i = 0; i < showFileTypes.length; i++)
      if (showFileTypes[i])
        totalSet++;
  }

  // ---------------------------------------------------------------------------------//
  public boolean filtersActive ()
  // ---------------------------------------------------------------------------------//
  {
    return totalSet != showFileTypes.length;
  }

  // ---------------------------------------------------------------------------------//
  //  public boolean isMatch (AppleTreeFile filePath)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    return true;
  //  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save ()
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < suffixes.length; i++)
      preferences.putBoolean (PREFS_FILE_TYPE + suffixes[i], showFileTypes[i]);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder (super.toString ());

    for (int i = 0; i < suffixes.length; i++)
      text.append (
          String.format ("%-4s ................. %s%n", suffixes[i], showFileTypes[i]));

    return Utility.rtrim (text);
  }
}
