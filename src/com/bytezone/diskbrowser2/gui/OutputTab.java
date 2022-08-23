package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class OutputTab extends DBTextTab implements    //
    ShowLinesListener,                            //
    //    FilterChangeListener,                         //
    //    OutputWriter,                                 //
    TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_LINES = 2500;
  //  private static final String TRUNCATE_MESSAGE_1 =
  //      "*** Output truncated at %,d lines to improve rendering time ***";
  //  private static final String TRUNCATE_MESSAGE_2 =
  //      "***      To see the entire file, use File -> Save Output      ***";

  LineDisplayStatus lineDisplayStatus;
  private TreeFile treeFile;                    // the item to display

  // ---------------------------------------------------------------------------------//
  public OutputTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    //    textFormatter = new TextFormatterJcl ();
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

    if (treeFile.isFile ())
    {
      newLines.add ("--> File");
      if (treeFile.isAppleFileSystem ())
        newLines.add ("also fs");
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
      newLines.add ("--> AppleFile");
      byte[] buffer = treeFile.getAppleFile ().read ();

      for (String line : Utility.getHexDumpLines (buffer, 0, buffer.length))
        newLines.add (line);
    }

    return newLines;
  }

  // ---------------------------------------------------------------------------------//
  //  @Override
  //  public void write (File file)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    if (file == null)
  //      return;
  //
  //    try (BufferedWriter output = new BufferedWriter (new FileWriter (file)))
  //    {
  //      for (String line : getLines (0))
  //        output.write (line + "\n");
  //      AppBase.showAlert (AlertType.INFORMATION, "Success", "File Saved: " + file.getName ());
  //    }
  //    catch (IOException e)
  //    {
  //      AppBase.showAlert (AlertType.ERROR, "Error", "File Error: " + e.getMessage ());
  //    }
  //  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void showLinesSelected (LineDisplayStatus lineDisplayStatus)
  // ---------------------------------------------------------------------------------//
  {
    this.lineDisplayStatus = lineDisplayStatus;
    textFormatter.setShowLineNumbers (lineDisplayStatus.showLines);
    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  //  @Override
  //  public void setFilter (FilterStatus filterStatus)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    textFormatter.setFilter (filterStatus);
  //    refresh ();
  //  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (TreeFile treeFile)
  // ---------------------------------------------------------------------------------//
  {
    this.treeFile = treeFile;

    refresh ();
  }
}
