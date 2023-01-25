package com.bytezone.diskbrowser2.gui;

import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

// -----------------------------------------------------------------------------------//
public class ExtrasHeaderBar extends HeaderBar implements TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  private TreeFile treeFile;

  // ---------------------------------------------------------------------------------//
  void updateNameLabel ()
  // ---------------------------------------------------------------------------------//
  {
    if (treeFile == null)
    {
      leftLabel.setText ("");
      return;
    }

    leftLabel.setText (treeFile.getCatalogLine ());
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
