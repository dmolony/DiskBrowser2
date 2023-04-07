package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.appleformat.FormattedAppleFile;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class MetaTab extends DBTextTab
// -----------------------------------------------------------------------------------//
{
  private static final String HEADER =
      "===================================================================";
  private static final String SPACES = "                                  ";
  AppleTreeItem appleTreeItem;
  AppleTreeFile treeFile;
  AppleFile appleFile;
  AppleFileSystem appleFileSystem;
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

    attachHeader (lines);

    if (appleFile != null)
      lines.add (appleFile.toString ());

    if (appleFileSystem != null)
      lines.add (appleFileSystem.toString ());

    return lines;
  }

  // ---------------------------------------------------------------------------------//
  private void attachHeader (List<String> lines)
  // ---------------------------------------------------------------------------------//
  {
    //    if (treeFile.isLocalFile ())                    // PC files and folders
    //    {
    //    }
    if (treeFile.isAppleFileSystem ())
      lines.add (frameHeader ("AppleFileSystem"));
    else if (treeFile.isAppleFolder ())
      lines.add (frameHeader ("AppleFolder"));
    else if (treeFile.isAppleForkedFile ())
      lines.add (frameHeader ("ForkedFile"));
    else if (treeFile.isAppleDataFile ())
      lines.add (frameHeader ("DataFile"));
    else
      lines.add (frameHeader ("Unknown"));
  }

  // ---------------------------------------------------------------------------------//
  private String frameHeader (String headingText)
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    text.append (HEADER);
    text.append ("\n");
    int padding = (HEADER.length () - headingText.length ()) / 2;
    text.append (SPACES.substring (0, padding));
    text.append (headingText);
    text.append ("\n");
    text.append (HEADER);
    text.append ("\n");

    return text.toString ();
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
