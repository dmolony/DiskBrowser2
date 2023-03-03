package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appleformat.FormattedAppleFile;
import com.bytezone.filesystem.AppleFile;

import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class MetaTab extends DBTextTab
// -----------------------------------------------------------------------------------//
{
  private static final String HEADER =
      "===================================================================";
  private static final String SPACES = "                                  ";
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
    List<String> lines = new ArrayList<> ();

    if (appleTreeItem == null)
      return lines;

    TreeItem<TreeFile> item = appleTreeItem;
    while (item != null)
    {
      show (item.getValue (), lines);
      item = item.getParent ();
    }

    return lines;
  }

  // ---------------------------------------------------------------------------------//
  private void show (TreeFile treeFile, List<String> lines)
  // ---------------------------------------------------------------------------------//
  {
    AppleFile appleFile = treeFile.getAppleFile ();

    if (treeFile.isLocalFile ())                    // PC files and folders
    {
      if (treeFile.isAppleFileSystem ())
        lines.add (frameHeader ("AppleFileSystem", appleFile));
    }
    else                                            // Apple files
    {
      if (treeFile.isAppleFileSystem ())
        lines.add (frameHeader ("Embedded AppleFileSystem", appleFile));
      else if (treeFile.isAppleFolder ())
        lines.add (frameHeader ("AppleFolder", appleFile));
      else if (treeFile.isAppleForkedFile ())
        lines.add (frameHeader ("AppleForkedFile", appleFile));
      else if (treeFile.isAppleDataFile ())
        lines.add (frameHeader ("AppleDataFile", appleFile));
    }
  }

  // ---------------------------------------------------------------------------------//
  private String frameHeader (String header, AppleFile appleFile)
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    text.append (HEADER);
    text.append ("\n");
    int padding = (HEADER.length () - header.length ()) / 2;
    text.append (SPACES.substring (0, padding));
    text.append (header);
    text.append ("\n");
    text.append (HEADER);
    text.append ("\n");

    text.append (appleFile.toString ());

    return text.toString ();
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
