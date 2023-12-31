package com.bytezone.diskbrowser2.gui;

import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

// -----------------------------------------------------------------------------------//
class OutputHeaderBar extends HeaderBar implements TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  private AppleTreeFile treeFile;

  // ---------------------------------------------------------------------------------//
  void updateNameLabel ()
  // ---------------------------------------------------------------------------------//
  {
    if (treeFile == null)
    {
      leftLabel.setText ("");
      return;
    }

    //    leftLabel.setText (treeFile.getCatalogLine ());
    leftLabel.setText (treeFile.toString ());
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    this.treeFile = appleTreeItem.getValue ();

    updateNameLabel ();
  }
}
