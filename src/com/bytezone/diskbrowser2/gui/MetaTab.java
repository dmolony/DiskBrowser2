package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.filesystem.AppleBlock;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;
import com.bytezone.filesystem.FileProdos.ForkType;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class MetaTab extends DBTextTab
// -----------------------------------------------------------------------------------//
{
  private static final String HEADER =
      "===================================================================";
  private static final String SPACES = "                                  ";

  AppleTreeItem appleTreeItem;
  AppleTreeFile appleTreeFile;

  AppleFile appleFile;
  AppleBlock appleBlock;
  AppleFileSystem appleFileSystem;

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

    //    if (appleTreeItem == null)
    //      return lines;

    attachHeader (lines);

    if (appleFile != null)
      lines.add (appleFile.toString ());

    if (appleFileSystem != null)
    {
      if (appleFile != null)
        lines.add ("");
      lines.add (appleFileSystem.toString ());
    }

    if (appleFile != null && appleFile.hasEmbeddedFileSystem ()
        && appleFile.getEmbeddedFileSystem () != appleFileSystem)
      System.out.println ("mismatch");

    return lines;
  }

  // ---------------------------------------------------------------------------------//
  private void attachHeader (List<String> lines)
  // ---------------------------------------------------------------------------------//
  {
    if (appleTreeFile != null)
    {
      if (appleTreeFile.isAppleFileSystem ())
        lines.add (frameHeader ("Apple File System"));
      else if (appleTreeFile.isAppleFolder ())
        lines.add (frameHeader ("Apple Folder"));
      else if (appleTreeFile.isAppleForkedFile ())
        lines.add (frameHeader ("Forked File"));
      else if (appleTreeFile.isAppleFork ())
        lines.add (frameHeader (
            (appleFile.getForkType () == ForkType.DATA ? "Data" : "Resource") + " Fork"));
      else if (appleTreeFile.isAppleDataFile ())
        lines.add (frameHeader ("Apple File"));
      else if (appleTreeFile.isLocalDirectory ())
        lines.add (frameHeader ("Local Folder"));
    }
    else if (appleBlock != null)
      lines.add (frameHeader ("Apple Block"));
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

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeItem (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.appleTreeItem = appleTreeItem;
    appleBlock = null;

    appleTreeFile = appleTreeItem.getValue ();
    appleFile = appleTreeFile.getAppleFile ();
    appleFileSystem = appleTreeFile.getAppleFileSystem ();

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleBlock (AppleBlock appleBlock)
  // ---------------------------------------------------------------------------------//
  {
    this.appleBlock = appleBlock;
    appleTreeItem = null;

    appleTreeFile = null;
    appleFile = appleBlock.getFileOwner ();
    appleFileSystem = null;

    refresh ();
  }
}
