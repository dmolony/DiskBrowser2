package com.bytezone.diskbrowser2.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bytezone.appbase.AppBase;
import com.bytezone.appleformat.FormattedAppleFile;
import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.appleformat.assembler.AssemblerPreferences;
import com.bytezone.appleformat.basic.ApplesoftBasicPreferences;
import com.bytezone.appleformat.graphics.GraphicsPreferences;
import com.bytezone.appleformat.text.TextPreferences;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class OutputTab extends DBTextTab implements FilterChangeListener, OutputWriter
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_LINES = 2500;

  private ApplesoftBasicPreferences basicPreferences =
      FormattedAppleFileFactory.basicPreferences;
  private TextPreferences textPreferences = FormattedAppleFileFactory.textPreferences;
  private GraphicsPreferences graphicsPreferences =
      FormattedAppleFileFactory.graphicsPreferences;
  private AssemblerPreferences assemblerPreferences =
      FormattedAppleFileFactory.assemblerPreferences;

  private FormattedAppleFile formattedAppleFile;
  AppleTreeItem appleTreeItem;
  AppleTreeFile treeFile;
  AppleFile appleFile;
  AppleFileSystem appleFileSystem;

  // ---------------------------------------------------------------------------------//
  public OutputTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    basicPreferences.alignAssign = true;
    basicPreferences.showAllXref = true;
    basicPreferences.showGosubGoto = true;
    basicPreferences.showCalls = true;
    basicPreferences.showSymbols = true;
    basicPreferences.showFunctions = true;
    basicPreferences.showConstants = true;
    basicPreferences.showDuplicateSymbols = true;

    assemblerPreferences.showStrings = false;
    assemblerPreferences.showTargets = false;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  List<String> getLines ()
  // ---------------------------------------------------------------------------------//
  {
    return formattedAppleFile == null ? new ArrayList<> () : getLines (MAX_LINES);
  }

  // ---------------------------------------------------------------------------------//
  private List<String> getLines (int maxLines)
  // ---------------------------------------------------------------------------------//
  {
    List<String> newLines = new ArrayList<> ();

    int lineNo = 0;
    String[] lines = formattedAppleFile.getText ().split ("\n");
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
      for (String line : getLines (0))
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

    refresh ();
  }
}
