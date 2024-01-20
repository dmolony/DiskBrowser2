package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabBase;
import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class DBOptionsTab extends TabBase
// -----------------------------------------------------------------------------------//
{
  protected FormattedAppleFile formattedAppleFile;
  protected AppleTreeItem appleTreeItem;
  protected AppleTreeFile appleTreeFile;
  protected AppleFile appleFile;
  protected AppleFileSystem appleFileSystem;

  // ---------------------------------------------------------------------------------//
  public DBOptionsTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);
  }
}
