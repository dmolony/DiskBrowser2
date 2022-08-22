package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleFile;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class OutputTab extends DBTextTab implements    //
    ShowLinesListener,                            //
    //    TableItemSelectionListener,                   //
    //    FilterChangeListener,                         //
    //    OutputWriter,                                 //
    //    CodePageSelectedListener,                     //
    TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_LINES = 2500;
  private static final String TRUNCATE_MESSAGE_1 =
      "*** Output truncated at %,d lines to improve rendering time ***";
  private static final String TRUNCATE_MESSAGE_2 =
      "***      To see the entire file, use File -> Save Output      ***";

  //  private static Pattern includePattern =
  //      Pattern.compile ("^//\\s+JCLLIB\\s+ORDER=\\((" + Utility.validName + ")\\)$");
  //  private static Pattern memberPattern = Pattern
  //      .compile ("^//(" + Utility.validPart + ")?\\s+INCLUDE\\s+MEMBER=(" + Utility.validPart + ")");

  LineDisplayStatus lineDisplayStatus;
  private AppleFile dataFile;                    // the item to display

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
    return dataFile == null ? new ArrayList<> () : getLines (MAX_LINES);
  }

  // ---------------------------------------------------------------------------------//
  private List<String> getLines (int maxLines)
  // ---------------------------------------------------------------------------------//
  {
    List<String> newLines = new ArrayList<> ();

    //    List<String> lines = dataFile.getLines ();     // improve this
    //    int lineNo = 0;
    //    String includeDatasetName = "";
    //
    //    boolean isJCL = lineDisplayStatus.expandInclude && Utility.isJCL (lines);
    //
    //    if (maxLines == 0)
    //      maxLines = Integer.MAX_VALUE;
    //
    //    for (String line : lines)
    //    {
    //      if (++lineNo > maxLines)
    //      {
    //        newLines.add ("");
    //        newLines.add (String.format (TRUNCATE_MESSAGE_1, maxLines));
    //        newLines.add (TRUNCATE_MESSAGE_2);
    //        break;
    //      }
    //
    //      if (lineDisplayStatus.stripLines)
    //        line = Utility.stripLineNumber (line);
    //
    //      if (lineDisplayStatus.truncateLines && line.length () > 0)
    //        newLines.add (line.substring (1));
    //      else
    //        newLines.add (line);
    //
    //      if (isJCL)
    //        includeDatasetName = checkInclude (includeDatasetName, line, newLines);
    //    }

    return newLines;
  }

  // ---------------------------------------------------------------------------------//
  //  private String checkInclude (String includeDatasetName, String line, List<String> newLines)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    if (!includeDatasetName.isEmpty ())
  //    {
  //      Matcher m = memberPattern.matcher (line);
  //      if (m.find ())
  //        append (newLines, includeDatasetName, m.group (2), "//*");
  //    }
  //
  //    Matcher m = includePattern.matcher (line);
  //    if (m.find ())
  //      includeDatasetName = m.group (1);
  //
  //    return includeDatasetName;
  //  }

  // ---------------------------------------------------------------------------------//
  //  private void append (List<String> newLines, String datasetName, String memberName,
  //      String commentIndicator)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    Optional<PdsMember> optMember = findMember (datasetName, memberName);
  //    if (optMember.isEmpty ())
  //      newLines.add (String.format ("==> %s(%s): dataset not seen yet", datasetName, memberName));
  //    else
  //      for (String line : optMember.get ().getLines ())
  //        if (!line.startsWith (commentIndicator))
  //          newLines.add (line);
  //  }

  // ---------------------------------------------------------------------------------//
  //  Optional<PdsMember> findMember (String datasetName, String memberName)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    if (datasets.containsKey (datasetName))
  //      return datasets.get (datasetName).findMember (memberName);
  //
  //    return Optional.empty ();
  //  }

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
  public void treeNodeSelected (TreeFile nodeData)
  // ---------------------------------------------------------------------------------//
  {
    //    if (nodeData.isPartitionedDataset ())
    //    {
    //      Dataset dataset = nodeData.getDataset ();
    //      String datasetName = dataset.getName ();
    //      if (!datasets.containsKey (datasetName))
    //        datasets.put (datasetName, (PdsDataset) dataset);
    //      dataFile = null;
    //    }
    //    else if (nodeData.isPhysicalSequentialDataset ())
    //      dataFile = nodeData.getDataFile ();
    //    else
    //      dataFile = null;

    refresh ();
  }
}
