package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appleformat.FormattedAppleFile;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class HexTab extends DBTextTab
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_HEX_BYTES = 0x20_000;

  //  TreeFile treeFile;
  //  AppleFile appleFile;
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
    List<String> lines = new ArrayList<> ();

    if (formattedAppleFile == null)
      return lines;

    //    byte[] buffer = appleFile.read ();
    //    return Utility.getHexDumpLines (buffer, 0, Math.min (MAX_HEX_BYTES, buffer.length));
    for (String line : formattedAppleFile.getHex ().split ("\n"))
      lines.add (line);
    return lines;
  }

  // ---------------------------------------------------------------------------------//
  public void setFormatter (FormattedAppleFile formattedAppleFile)
  // ---------------------------------------------------------------------------------//
  {
    //    this.treeFile = appleTreeItem.getValue ();
    //    appleFile = treeFile.isAppleDataFile () ? treeFile.getAppleFile () : null;
    this.formattedAppleFile = formattedAppleFile;

    refresh ();
  }
}
