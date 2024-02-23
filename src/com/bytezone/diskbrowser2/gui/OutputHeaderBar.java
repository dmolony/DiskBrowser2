package com.bytezone.diskbrowser2.gui;

import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleBlock;

// -----------------------------------------------------------------------------------//
class OutputHeaderBar extends HeaderBar implements TreeNodeListener, GridClickListener
// -----------------------------------------------------------------------------------//
{
  private AppleTreeNode treeFile;
  private AppleBlock appleBlock;

  // ---------------------------------------------------------------------------------//
  void updateNameLabel ()
  // ---------------------------------------------------------------------------------//
  {
    if (treeFile != null)
      leftLabel.setText (treeFile.toString ());
    else if (appleBlock != null)
      leftLabel.setText (appleBlock.getBlockType ().toString ());
    else
      leftLabel.setText ("");
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    treeFile = appleTreeItem.getValue ();
    appleBlock = null;

    updateNameLabel ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void gridClick (GridClickEvent event)
  // ---------------------------------------------------------------------------------//
  {
    appleBlock = event.block;
    treeFile = null;

    updateNameLabel ();
  }
}
