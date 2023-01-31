package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appleformat.FormattedAppleFile;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class MetaTab extends DBTextTab
// -----------------------------------------------------------------------------------//
{
  //  TreeFile treeFile;
  //  AppleFile appleFile;
  private FormattedAppleFile formattedAppleFile;

  // ---------------------------------------------------------------------------------//
  public MetaTab (String title, KeyCode keyCode)
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

    for (String line : formattedAppleFile.getMeta ().split ("\n"))
      lines.add (line);
    return lines;

    //    if (treeFile == null)
    //    {
    //      newLines.add ("No tree");
    //      return newLines;
    //    }
    //
    //    if (treeFile.isLocalFile ())
    //    {
    //      newLines.add ("--> PC File");
    //      if (treeFile.isAppleFileSystem ())
    //      {
    //        newLines.add ("--> AppleFileSystem");
    //        for (String line : (((AppleFileSystem) treeFile.getAppleFile ()).toText ()).split ("\n"))
    //          newLines.add (line);
    //      }
    //    }
    //    else if (treeFile.isLocalDirectory ())
    //    {
    //      newLines.add ("--> PC Directory");
    //    }
    //    else if (treeFile.isAppleFileSystem ())
    //    {
    //      newLines.add ("--> AppleFileSystem");
    //
    //      for (String line : (((AppleFileSystem) treeFile.getAppleFile ()).toText ()).split ("\n"))
    //        newLines.add (line);
    //    }
    //    else if (treeFile.isAppleFolder ())
    //    {
    //      newLines.add ("--> AppleFolder");
    //    }
    //    else if (treeFile.isAppleDataFile ())
    //    {
    //      newLines.add ("--> AppleDataFile");
    //      AppleFile appleFile = treeFile.getAppleFile ();
    //      newLines.add (appleFile.toString ());
    //    }

    //    return newLines;
  }

  // ---------------------------------------------------------------------------------//
  public void setFormatter (FormattedAppleFile formattedAppleFile)
  // ---------------------------------------------------------------------------------//
  {
    //    this.treeFile = appleTreeItem.getValue ();
    //    appleFile = treeFile.getAppleFile ();
    this.formattedAppleFile = formattedAppleFile;

    refresh ();
  }
}
