package com.bytezone.diskbrowser2.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bytezone.appbase.AppBase;
import com.bytezone.appleformat.ApplesoftBasicProgram;
import com.bytezone.appleformat.BasicPreferences;
import com.bytezone.appleformat.BasicProgram;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;
import com.bytezone.filesystem.FileDos;
import com.bytezone.filesystem.FileProdos;
import com.bytezone.filesystem.FsDos;
import com.bytezone.filesystem.FsProdos;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class OutputTab extends DBTextTab implements      //
    //    ShowLinesListener,                            //
    FilterChangeListener,                         //
    OutputWriter,                                 //
    TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_LINES = 2500;
  //  private static final String TRUNCATE_MESSAGE_1 =
  //      "*** Output truncated at %,d lines to improve rendering time ***";
  //  private static final String TRUNCATE_MESSAGE_2 =
  //      "***      To see the entire file, use File -> Save Output      ***";

  //  LineDisplayStatus lineDisplayStatus;
  private TreeFile treeFile;                    // the item to display
  private AppleFile appleFile;
  private BasicPreferences basicPreferences = new BasicPreferences ();

  // ---------------------------------------------------------------------------------//
  public OutputTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    BasicProgram.setBasicPreferences (basicPreferences);
    basicPreferences.showAllXref = false;
    basicPreferences.showGosubGoto = true;
    basicPreferences.showCalls = true;
    basicPreferences.showSymbols = true;
    basicPreferences.showFunctions = true;
    basicPreferences.showConstants = true;
    basicPreferences.showDuplicateSymbols = true;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  List<String> getLines ()
  // ---------------------------------------------------------------------------------//
  {
    return treeFile == null ? new ArrayList<> () : getLines (MAX_LINES);
  }

  // ---------------------------------------------------------------------------------//
  private List<String> getLines (int maxLines)
  // ---------------------------------------------------------------------------------//
  {
    List<String> newLines = new ArrayList<> ();

    if (treeFile.isAppleFileSystem ())
    {
      String catalog = ((AppleFileSystem) treeFile.getAppleFile ()).catalog ();
      return Arrays.asList (catalog.split ("\n"));
    }
    else if (treeFile.isAppleDataFile ())
    {
      appleFile = treeFile.getAppleFile ();
      AppleFileSystem fileSystem = appleFile.getFileSystem ();

      if (fileSystem instanceof FsDos)
      {
        switch (((FileDos) appleFile).getFileType ())
        {
          case 2:
            return getDosApplesoftLines ();
        }
      }
      else if (fileSystem instanceof FsProdos)
      {
        switch (((FileProdos) appleFile).getFileType ())
        {
          case 0xFC:
            return getProdosApplesoftLines ();
        }
      }

      byte[] buffer = treeFile.getAppleFile ().read ();
      return Utility.getHexDumpLines (buffer, 0, Math.min (20000, buffer.length));
    }

    return newLines;
  }

  // ---------------------------------------------------------------------------------//
  private List<String> getDosApplesoftLines ()
  // ---------------------------------------------------------------------------------//
  {
    byte[] buffer = appleFile.read ();
    int length = Utility.unsignedShort (buffer, 0);
    ApplesoftBasicProgram basic =
        new ApplesoftBasicProgram (appleFile.getName (), buffer, 2, length);

    return List.of (basic.getText ().split ("\n"));
  }

  // ---------------------------------------------------------------------------------//
  private List<String> getProdosApplesoftLines ()
  // ---------------------------------------------------------------------------------//
  {
    byte[] buffer = appleFile.read ();
    int length = ((FileProdos) appleFile).getLength ();
    ApplesoftBasicProgram basic =
        new ApplesoftBasicProgram (appleFile.getName (), buffer, 0, length);

    return List.of (basic.getText ().split ("\n"));
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
  //  @Override
  //  public void showLinesSelected (LineDisplayStatus lineDisplayStatus)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    this.lineDisplayStatus = lineDisplayStatus;
  //    textFormatter.setShowLineNumbers (lineDisplayStatus.showLines);
  //
  //    refresh ();
  //  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void setFilter (FilterStatus filterStatus)
  // ---------------------------------------------------------------------------------//
  {
    textFormatter.setFilter (filterStatus);

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (TreeFile treeFile)
  // ---------------------------------------------------------------------------------//
  {
    this.treeFile = treeFile;
    appleFile = treeFile.isAppleDataFile () ? treeFile.getAppleFile () : null;

    refresh ();
  }
}
