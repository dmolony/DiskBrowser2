package com.bytezone.diskbrowser2.gui;

import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleBlock;

// -----------------------------------------------------------------------------------//
class OutputHeaderBar extends HeaderBar implements TreeNodeListener, GridClickListener
// -----------------------------------------------------------------------------------//
{
  private AppleTreeNode treeNode;
  private AppleBlock appleBlock;

  // ---------------------------------------------------------------------------------//
  void updateNameLabel ()
  // ---------------------------------------------------------------------------------//
  {
    if (treeNode != null)
      leftLabel.setText (treeNode.toString ());
    else if (appleBlock != null)
      leftLabel.setText (appleBlock.getBlockType ().toString ());
    else
      leftLabel.setText ("");
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeNode appleTreeNode)
  // ---------------------------------------------------------------------------------//
  {
    treeNode = appleTreeNode;
    appleBlock = null;

    updateNameLabel ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void gridClick (GridClickEvent event)
  // ---------------------------------------------------------------------------------//
  {
    appleBlock = event.block;
    treeNode = null;

    updateNameLabel ();
  }
}
