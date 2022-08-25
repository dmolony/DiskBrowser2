package com.bytezone.diskbrowser2.gui;

import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

// -----------------------------------------------------------------------------------//
class OutputHeaderBar extends HeaderBar implements TreeNodeListener, ShowLinesListener
// -----------------------------------------------------------------------------------//
{
  private LineDisplayStatus lineDisplayStatus;
  private TreeFile treeFile;

  // ---------------------------------------------------------------------------------//
  void updateNameLabel (boolean truncateLines)
  // ---------------------------------------------------------------------------------//
  {
    if (treeFile == null)
    {
      leftLabel.setText ("");
      return;
    }

    String indicator = truncateLines ? "<-" : "";

    //    if (nodeData.isPartitionedDataset ())
    //    {
    //      String memberName = indicator + catalogEntry.getMemberName ();
    //      if (catalogEntry.isAlias ())
    //        leftLabel.setText (memberName + " -> " + catalogEntry.getAliasName ());
    //      else
    //        leftLabel.setText (memberName);
    //    }
    //    else
    leftLabel.setText (indicator + treeFile.getCatalogLine ());
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void showLinesSelected (LineDisplayStatus lineDisplayStatus)
  // ---------------------------------------------------------------------------------//
  {
    this.lineDisplayStatus = lineDisplayStatus;
    //    updateNameLabel (lineDisplayStatus.truncateLines);
    updateNameLabel (false);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (TreeFile nodeData)
  // ---------------------------------------------------------------------------------//
  {
    this.treeFile = nodeData;

    //    rightLabel.setText (nodeData.isDataset () ? nodeData.getDisposition ().toString () : "");
    //    updateNameLabel (lineDisplayStatus.truncateLines);
    updateNameLabel (false);
  }
}
