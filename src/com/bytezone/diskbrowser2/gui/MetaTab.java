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
  private static final String HEADER_LINE =
      "===================================================================";
  private static final String SPACES = "                                  ";

  AppleTreeNode appleTreeNode;

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

    attachHeader (lines);

    if (appleBlock != null)
    {
      lines.add (appleBlock.toString ());
      lines.add ("");
    }

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
    if (appleTreeNode != null)
    {
      if (appleTreeNode.isAppleFileSystem ())
        lines.add (frameHeader ("Apple File System"));
      else if (appleTreeNode.isAppleFolder ())
        lines.add (frameHeader ("Apple Folder"));
      else if (appleTreeNode.isAppleForkedFile ())
        lines.add (frameHeader ("Forked File"));
      else if (appleTreeNode.isAppleFork ())
        lines.add (frameHeader (
            (appleFile.getForkType () == ForkType.DATA ? "Data" : "Resource") + " Fork"));
      else if (appleTreeNode.isAppleDataFile ())
        lines.add (frameHeader ("Apple File"));
      else if (appleTreeNode.isLocalDirectory ())
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

    text.append (HEADER_LINE);
    text.append ("\n");
    int padding = (HEADER_LINE.length () - headingText.length ()) / 2;
    text.append (SPACES.substring (0, padding));
    text.append (headingText);
    text.append ("\n");
    text.append (HEADER_LINE);

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleTreeNode (AppleTreeNode appleTreeNode)
  // ---------------------------------------------------------------------------------//
  {
    appleBlock = null;
    this.appleTreeNode = appleTreeNode;

    appleFile = appleTreeNode.getAppleFile ();
    appleFileSystem = appleTreeNode.getAppleFileSystem ();

    if (appleFile != null && appleFileSystem == null)
      appleFileSystem = appleFile.getParentFileSystem ();

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleBlock (AppleBlock appleBlock)
  // ---------------------------------------------------------------------------------//
  {
    this.appleBlock = appleBlock;
    appleTreeNode = null;

    appleFile = appleBlock.getFileOwner ();
    appleFileSystem = appleBlock.getFileSystem ();

    refresh ();
  }
}
