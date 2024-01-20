package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appleformat.ApplePreferences;
import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.filesystem.AppleBlock;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class ExtrasTab extends DBTextTab implements PreferenceChangeListener
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_LINES = 2500;

  private FormattedAppleFile formattedAppleFile;
  //  private AppleTreeItem appleTreeItem;
  private AppleTreeFile appleTreeFile;
  //  private AppleFile appleFile;
  //  private AppleBlock appleBlock;
  //  private AppleFileSystem appleFileSystem;

  // ---------------------------------------------------------------------------------//
  public ExtrasTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  List<String> getLines ()
  // ---------------------------------------------------------------------------------//
  {
    return formattedAppleFile == null ? new ArrayList<> () : getLines (MAX_LINES);
  }

  // ---------------------------------------------------------------------------------//
  private List<String> getLines (int maxLines)
  // ---------------------------------------------------------------------------------//
  {
    List<String> newLines = new ArrayList<> ();

    for (String line : formattedAppleFile.getExtras ().split ("\n"))
      newLines.add (line);

    return newLines;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void preferenceChanged (ApplePreferences preferences)
  // ---------------------------------------------------------------------------------//
  {
    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeItem (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    appleTreeFile = appleTreeItem.getValue ();
    formattedAppleFile = appleTreeFile.getFormattedAppleFile ();
    //    appleBlock = null;

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleBlock (AppleBlock appleBlock)
  // ---------------------------------------------------------------------------------//
  {
    appleTreeFile = null;
    //    this.appleBlock = appleBlock;
    formattedAppleFile = (FormattedAppleFile) appleBlock.getUserData ();

    refresh ();
  }
}
