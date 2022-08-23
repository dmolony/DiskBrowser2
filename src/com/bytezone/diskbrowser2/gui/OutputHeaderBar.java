package com.bytezone.diskbrowser2.gui;

import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

// -----------------------------------------------------------------------------------//
class OutputHeaderBar extends HeaderBar implements TreeNodeListener, ShowLinesListener
// -----------------------------------------------------------------------------------//
{
  private LineDisplayStatus lineDisplayStatus;
  private TreeFile nodeData;
  //  private CatalogEntry catalogEntry;

  // ---------------------------------------------------------------------------------//
  void updateNameLabel (boolean truncateLines)
  // ---------------------------------------------------------------------------------//
  {
    if (nodeData == null)// || !nodeData.isDataset () || catalogEntry == null)
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
    leftLabel.setText (indicator + nodeData.getName ());
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
    this.nodeData = nodeData;

    //    rightLabel.setText (nodeData.isDataset () ? nodeData.getDisposition ().toString () : "");
    //    updateNameLabel (lineDisplayStatus.truncateLines);
    updateNameLabel (false);
  }
}
