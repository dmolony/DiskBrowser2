package com.bytezone.diskbrowser2.gui;

import static com.bytezone.utility.Utility.suffixes;

import java.util.prefs.Preferences;

import com.bytezone.appleformat.ApplePreferences;

// -----------------------------------------------------------------------------------//
public class FileFilterPreferences extends ApplePreferences
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_FILE_TYPE = "FileType-";

  //  String[] suffixes = { "po", "dsk", "do", "hdv", "2mg", "d13", "sdk", "shk", "bxy",
  //      "bny", "bqy", "lbr", "woz", "img", "dimg", "zip", "gz" };

  private boolean[] showFileTypes =
      new boolean[com.bytezone.utility.Utility.suffixes.size ()];
  int totalSet;

  // ---------------------------------------------------------------------------------//
  public FileFilterPreferences (Preferences preferences)
  // ---------------------------------------------------------------------------------//
  {
    super ("File Filter Preferences", preferences);

    for (int i = 0; i < suffixes.size (); i++)
    {
      showFileTypes[i] =
          preferences.getBoolean (PREFS_FILE_TYPE + suffixes.get (i), true);
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
    for (int i = 0; i < suffixes.size (); i++)
      preferences.putBoolean (PREFS_FILE_TYPE + suffixes.get (i), showFileTypes[i]);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder (super.toString ());

    for (int i = 0; i < suffixes.size (); i++)
      text.append (String.format ("%-4s ................. %s%n", suffixes.get (i),
          showFileTypes[i]));

    return Utility.rtrim (text);
  }
}
