package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appleformat.FormattedAppleFile;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class MetaTab extends DBTextTab
// -----------------------------------------------------------------------------------//
{
  AppleTreeItem appleTreeItem;
  TreeFile treeFile;
  AppleFile appleFile;
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
    List<String> newLines = new ArrayList<> ();

    if (appleTreeItem == null)
      return newLines;

    TreeItem<TreeFile> item = appleTreeItem;
    while (true)
    {
      TreeFile tf = item.getValue ();
      show (tf, newLines);
      item = item.getParent ();
      if (item == null)
        break;
    }

    return newLines;
  }

  // ---------------------------------------------------------------------------------//
  private void show (TreeFile treeFile, List<String> newLines)
  // ---------------------------------------------------------------------------------//
  {
    if (treeFile == null)
    {
      newLines.add ("No tree");
      return;
    }

    AppleFile appleFile = treeFile.getAppleFile ();

    if (treeFile.isLocalFile ())
    {
      if (treeFile.isAppleFileSystem ())
      {
        newLines.add ("\n--> AppleFileSystem");
        for (String line : (((AppleFileSystem) treeFile.getAppleFile ()).toText ()).split ("\n"))
          newLines.add (line);
      }
    }
    else
    {
      if (treeFile.isAppleFileSystem ())
      {
        newLines.add ("\n--> AppleFileSystem : " + appleFile.getFileSystemType ());

        for (String line : (((AppleFileSystem) appleFile).toText ()).split ("\n"))
          newLines.add (line);
      }
      else if (treeFile.isAppleFolder ())
      {
        newLines.add ("\n--> AppleFolder");
        newLines.add (appleFile.toString ());
      }
      else if (treeFile.isAppleDataFile ())
      {
        newLines.add ("\n--> AppleDataFile");
        newLines.add (appleFile.toString ());
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeItem (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeItem = appleTreeItem;
    treeFile = appleTreeItem.getValue ();
    appleFile = treeFile.getAppleFile ();
    formattedAppleFile = treeFile.getFormattedAppleFile ();

    refresh ();
  }
}
