package com.bytezone.diskbrowser2.gui;

import java.util.Arrays;
import java.util.List;

import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.filesystem.AppleBlock;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class HexTab extends DBTextTab
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_HEX_BYTES = 0x10_000;
  private static final List<String> emptyList = Arrays.asList ("This file has no data");

  private AppleFile appleFile;
  private AppleBlock appleBlock;
  private FormattedAppleFile formattedAppleFile;

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
    byte[] buffer = null;
    int offset = 0;
    int length = 0;

    if (appleBlock != null)
    {
      buffer = appleBlock.read ();
      offset = 0;
      length = buffer.length;
    }
    else if (appleFile != null && appleFile.hasEmbeddedFileSystem ())
    {
      AppleFileSystem afs = appleFile.getEmbeddedFileSystem ();
      buffer = afs.getDiskBuffer ();
      offset = afs.getDiskOffset ();
      length = afs.getDiskLength ();
    }
    else
    {
      if (formattedAppleFile == null)
        return emptyList;

      buffer = formattedAppleFile.getBuffer ();
      if (buffer == null || buffer.length == 0)
        return emptyList;

      offset = formattedAppleFile.getOffset ();
      length = formattedAppleFile.getLength ();
    }

    return Utility.getHexDumpLines (buffer, offset, Math.min (MAX_HEX_BYTES, length));
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeNode (AppleTreeNode treeNode,
      FormattedAppleFile formattedAppleFile)
  // ---------------------------------------------------------------------------------//
  {
    appleFile = treeNode.getAppleFile ();
    appleBlock = null;
    this.formattedAppleFile = formattedAppleFile;

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
}
