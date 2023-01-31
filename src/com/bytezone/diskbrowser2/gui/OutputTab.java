package com.bytezone.diskbrowser2.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bytezone.appbase.AppBase;
import com.bytezone.appleformat.FormattedAppleFile;
import com.bytezone.appleformat.assembler.AssemblerPreferences;
import com.bytezone.appleformat.assembler.AssemblerProgram;
import com.bytezone.appleformat.basic.BasicPreferences;
import com.bytezone.appleformat.basic.BasicProgram;
import com.bytezone.appleformat.graphics.GraphicsPreferences;
import com.bytezone.appleformat.graphics.HiResImage;
import com.bytezone.appleformat.text.Text;
import com.bytezone.appleformat.text.TextPreferences;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class OutputTab extends DBTextTab implements FilterChangeListener, OutputWriter
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_LINES = 2500;
  //  private static final String TRUNCATE_MESSAGE_1 =
  //      "*** Output truncated at %,d lines to improve rendering time ***";
  //  private static final String TRUNCATE_MESSAGE_2 =
  //      "***      To see the entire file, use File -> Save Output      ***";

  //  private TreeFile treeFile;                    // the item to display
  //  private AppleFile appleFile;

  private BasicPreferences basicPreferences = new BasicPreferences ();
  private TextPreferences textPreferences = new TextPreferences ();
  private GraphicsPreferences graphicsPreferences = new GraphicsPreferences ();
  private AssemblerPreferences assemblerPreferences = new AssemblerPreferences ();

  private FormattedAppleFile formattedAppleFile;

  // ---------------------------------------------------------------------------------//
  public OutputTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    BasicProgram.setBasicPreferences (basicPreferences);
    basicPreferences.showAllXref = true;
    basicPreferences.showGosubGoto = true;
    basicPreferences.showCalls = true;
    basicPreferences.showSymbols = true;
    basicPreferences.showFunctions = true;
    basicPreferences.showConstants = true;
    basicPreferences.showDuplicateSymbols = true;

    Text.setTextPreferences (textPreferences);
    textPreferences.showHeader = true;

    HiResImage.setGraphicsPreferences (graphicsPreferences);

    AssemblerProgram.setAssemblerPreferences (assemblerPreferences);
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
    //
    //    if (treeFile.isAppleFileSystem ())
    //    {
    //      String catalog = treeFile.getAppleFile ().catalog ();
    //      return Arrays.asList (catalog.split ("\n"));
    //    }
    //    else if (treeFile.isAppleFolder ())
    //    {
    //      String catalog = treeFile.getAppleFile ().catalog ();
    //      return Arrays.asList (catalog.split ("\n"));
    //    }
    //    else if (treeFile.isAppleDataFile ())
    //    {
    //      appleFile = treeFile.getAppleFile ();
    //      AppleFileSystem fileSystem = appleFile.getFileSystem ();
    //
    //      if (fileSystem instanceof FsDos)
    //      {
    //        AppleFileFormatter formatter = factory.getDosFormatter ((FileDos) appleFile);
    for (String line : formattedAppleFile.getText ().split ("\n"))
      newLines.add (line);
    return newLines;
    //      }
    //      else if (fileSystem instanceof FsProdos)
    //      {
    //      }
    //
    //      byte[] buffer = treeFile.getAppleFile ().read ();
    //      return Utility.getHexDumpLines (buffer, 0, Math.min (20000, buffer.length));
    //    }
    //
    //    return newLines;
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
      AppBase.showAlert (AlertType.INFORMATION, "Success", "File Saved: " + file.getName ());
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
  public void setFormatter (FormattedAppleFile formattedAppleFile)
  // ---------------------------------------------------------------------------------//
  {
    //    treeFile = appleTreeItem.getValue ();
    //    appleFile = treeFile.isAppleDataFile () ? treeFile.getAppleFile () : null;
    this.formattedAppleFile = formattedAppleFile;

    refresh ();
  }
}
