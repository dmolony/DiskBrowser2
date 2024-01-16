package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class HexTab extends DBTextTab
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_HEX_BYTES = 0x10_000;

  private FormattedAppleFile formattedAppleFile;
  AppleTreeItem appleTreeItem;
  AppleTreeFile treeFile;
  AppleFile appleFile;
  AppleFileSystem appleFileSystem;

  // ---------------------------------------------------------------------------------//
  public HexTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
    //    textFormatter = new TextFormatterHex ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  List<String> getLines ()
  // ---------------------------------------------------------------------------------//
  {
    List<String> lines = new ArrayList<> ();

    byte[] buffer = null;
    int offset = 0;
    int length = 0;

    if (appleFile != null && appleFile.hasEmbeddedFileSystem ())
    {
      buffer = appleFile.read ();
      offset = 0;
      length = buffer.length;
    }
    else
    {
      if (formattedAppleFile == null)
        return lines;

      buffer = formattedAppleFile.getBuffer ();
      if (buffer == null)
        return lines;

      offset = formattedAppleFile.getOffset ();
      length = formattedAppleFile.getLength ();
    }

    return Utility.getHexDumpLines (buffer, offset, Math.min (MAX_HEX_BYTES, length));
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeItem (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeItem = appleTreeItem;

    treeFile = appleTreeItem.getValue ();
    appleFile = treeFile.getAppleFile ();
    appleFileSystem = treeFile.getAppleFileSystem ();
    formattedAppleFile =
        appleFile == null ? null : (FormattedAppleFile) appleFile.getUserData ();

    refresh ();
  }
}
