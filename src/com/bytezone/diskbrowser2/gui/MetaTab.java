package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class MetaTab extends DBTextTab implements TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  TreeFile treeFile;
  AppleFile appleFile;

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
    List<String> newLines = new ArrayList<> ();

    if (treeFile == null)
      return newLines;

    if (treeFile.isFile ())
    {
      newLines.add ("--> File");
      if (treeFile.isAppleFileSystem ())
      {
        newLines.add ("--> AppleFileSystem");
        for (String line : (((AppleFileSystem) treeFile.getAppleFile ()).toText ()).split ("\n"))
          newLines.add (line);
      }
    }
    else if (treeFile.isDirectory ())
    {
      newLines.add ("--> Directory");
    }
    else if (treeFile.isAppleFileSystem ())
    {
      newLines.add ("--> AppleFileSystem");

      for (String line : (((AppleFileSystem) treeFile.getAppleFile ()).toText ()).split ("\n"))
        newLines.add (line);
    }
    else if (treeFile.isAppleDirectory ())
    {
      newLines.add ("--> AppleDirectory");
    }
    else if (treeFile.isAppleDataFile ())
    {
      newLines.add ("--> AppleDataFile");
    }

    return newLines;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (TreeFile treeFile)
  // ---------------------------------------------------------------------------------//
  {
    this.treeFile = treeFile;
    appleFile = treeFile.getAppleFile ();

    refresh ();
  }
}
