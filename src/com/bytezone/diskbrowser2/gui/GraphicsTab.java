package com.bytezone.diskbrowser2.gui;

import com.bytezone.appleformat.ApplePreferences;
import com.bytezone.filesystem.AppleBlock;
import com.bytezone.filesystem.AppleFileSystem.FileSystemType;
import com.bytezone.filesystem.ProdosConstants;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class GraphicsTab extends DBGraphicsTab implements PreferenceChangeListener
// -----------------------------------------------------------------------------------//
{
  private static final double SCALE = 2;
  private static final double FONT_SCALE = 1;
  private static final double ICON_SCALE = 5;

  //  private FormattedAppleFile formattedAppleFile;
  //  AppleTreeItem appleTreeItem;
  AppleTreeFile appleTreeFile;
  //  AppleFile appleFile;
  AppleBlock appleBlock;
  //  AppleFileSystem appleFileSystem;

  // ---------------------------------------------------------------------------------//
  public GraphicsTab (String title, KeyCode keyCode)
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

    if (formattedAppleFile != null)
    {
      if (formattedAppleFile.getImage () != null)
      {
        double scale = SCALE;

        if (appleFile != null && appleFile.getFileSystemType () == FileSystemType.PRODOS)
        {
          if (appleFile.getFileType () == ProdosConstants.FILE_TYPE_FONT)
            scale = FONT_SCALE;
          if (appleFile.getFileType () == ProdosConstants.FILE_TYPE_ICN)
            scale = ICON_SCALE;
        }

        drawImage (formattedAppleFile.getImage (), scale);
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeItem (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeItem = appleTreeItem;

    appleTreeFile = appleTreeItem.getValue ();
    appleFile = appleTreeFile.getAppleFile ();
    appleFileSystem = appleTreeFile.getAppleFileSystem ();
    formattedAppleFile = appleTreeFile.getFormattedAppleFile ();

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleBlock (AppleBlock appleBlock)
  // ---------------------------------------------------------------------------------//
  {
    appleFile = null;
    this.appleBlock = appleBlock;

    formattedAppleFile = null;

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void preferenceChanged (ApplePreferences preferences)
  // ---------------------------------------------------------------------------------//
  {
    refresh ();
  }
}
