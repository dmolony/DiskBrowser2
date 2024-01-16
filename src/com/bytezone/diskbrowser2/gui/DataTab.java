package com.bytezone.diskbrowser2.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bytezone.appbase.AppBase;
import com.bytezone.appleformat.ApplePreferences;
import com.bytezone.appleformat.block.FormattedAppleBlock;
import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.filesystem.AppleBlock;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class DataTab extends DBTextTab
    implements FilterChangeListener, OutputWriter, PreferenceChangeListener
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_LINES = 2500;

  private AppleTreeItem appleTreeItem;
  private AppleTreeFile treeFile;
  private AppleFile appleFile;
  private AppleFileSystem appleFileSystem;
  private AppleBlock appleBlock;

  private FormattedAppleFile formattedAppleFile;
  private FormattedAppleBlock formattedAppleBlock;

  // ---------------------------------------------------------------------------------//
  public DataTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  List<String> getLines ()
  // ---------------------------------------------------------------------------------//
  {
    if (formattedAppleFile != null)
      return getLines (MAX_LINES, formattedAppleFile.getText ().split ("\n"));

    if (formattedAppleBlock != null)
      return getLines (MAX_LINES, formattedAppleBlock.getText ().split ("\n"));

    return new ArrayList<> ();
  }

  // ---------------------------------------------------------------------------------//
  private List<String> getLines (int maxLines, String[] lines)
  // ---------------------------------------------------------------------------------//
  {
    List<String> newLines = new ArrayList<> ();

    int lineNo = 0;
    //    String[] lines = formattedAppleFile.getText ().split ("\n");
    for (String line : lines)
    {
      if (lineNo++ > MAX_LINES)
      {
        newLines.add ("");
        newLines.add ("**** File too large ****");
        newLines.add (
            String.format ("**** %,d lines omitted ****", lines.length - MAX_LINES));
        break;
      }
      newLines.add (line);
    }

    return newLines;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void write (File file)
  // ---------------------------------------------------------------------------------//
  {
    if (file == null)
      return;

    try (BufferedWriter output = new BufferedWriter (new FileWriter (file)))
    {
      for (String line : getLines ())
        output.write (line + "\n");
      AppBase.showAlert (AlertType.INFORMATION, "Success",
          "File Saved: " + file.getName ());
    }
    catch (IOException e)
    {
      AppBase.showAlert (AlertType.ERROR, "Error", "File Error: " + e.getMessage ());
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void setFilter (FilterStatus filterStatus)
  // ---------------------------------------------------------------------------------//
  {
    textFormatter.setFilter (filterStatus);

    refresh ();
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
    appleBlock = null;

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  public void setAppleBlock (AppleBlock appleBlock,
      FormattedAppleBlock formattedAppleBlock)
  // ---------------------------------------------------------------------------------//
  {
    treeFile = null;
    appleFile = null;
    appleFileSystem = null;
    formattedAppleFile = null;
    this.appleBlock = appleBlock;
    this.formattedAppleBlock = formattedAppleBlock;

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void preferenceChanged (ApplePreferences preferences)
  // ---------------------------------------------------------------------------------//
  {
    refresh ();
  }
}
