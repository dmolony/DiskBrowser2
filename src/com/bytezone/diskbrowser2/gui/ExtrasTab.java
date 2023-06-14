package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appleformat.FormattedAppleFile;
import com.bytezone.appleformat.Preferences;
import com.bytezone.appleformat.assembler.AssemblerPreferences;
import com.bytezone.appleformat.basic.ApplesoftBasicPreferences;
import com.bytezone.appleformat.graphics.GraphicsPreferences;
import com.bytezone.appleformat.text.TextPreferences;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class ExtrasTab extends DBTextTab implements PreferenceChangeListener
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_LINES = 2500;

  private FormattedAppleFile formattedAppleFile;
  AppleTreeItem appleTreeItem;
  AppleTreeFile treeFile;
  AppleFile appleFile;
  AppleFileSystem appleFileSystem;

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
  public void preferenceChanged (Preferences preferences)
  // ---------------------------------------------------------------------------------//
  {
    if (preferences instanceof GraphicsPreferences graphicsPreferences)
      System.out.println (graphicsPreferences);
    if (preferences instanceof ApplesoftBasicPreferences basicPreferences)
      System.out.println (basicPreferences);
    if (preferences instanceof AssemblerPreferences assemblerPreferences)
      System.out.println (assemblerPreferences);
    if (preferences instanceof TextPreferences textPreferences)
      System.out.println (textPreferences);

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeItem (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeItem = appleTreeItem;

    treeFile = appleTreeItem.getValue ();
    appleFile = treeFile.getAppleFile ();
    appleFileSystem = treeFile.getAppleFileSystem ();
    formattedAppleFile = treeFile.getFormattedAppleFile ();

    refresh ();
  }
}
