package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabChangeListener;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleBlock;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;
import com.bytezone.filesystem.AppleFileSystem.FileSystemType;

import javafx.scene.control.Tab;

// -----------------------------------------------------------------------------------//
public class ExtrasHeaderBar extends HeaderBar
    implements TreeNodeListener, TabChangeListener, GridClickListener
// -----------------------------------------------------------------------------------//
{
  private Tab selectedTab;

  private AppleFile appleFile;
  private AppleBlock appleBlock;
  private AppleFileSystem appleFileSystem;

  // ---------------------------------------------------------------------------------//
  void updateNameLabel ()
  // ---------------------------------------------------------------------------------//
  {
    if (selectedTab instanceof DiskLayoutTab tab)
    {
      if (appleFileSystem != null)
      {
        FileSystemType fst = appleFileSystem.getFileSystemType ();
        if (appleBlock != null)
          leftLabel.setText (fst + " " + appleBlock.getBlockSubType ());
        else
          leftLabel.setText (fst.toString ());
      }
      else
        leftLabel.setText ("");
    }
    else if (selectedTab instanceof FileOptionsTab tab)
      leftLabel.setText ("Options");
    else
      leftLabel.setText ("File Filter");
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeNode appleTreeNode)
  // ---------------------------------------------------------------------------------//
  {
    appleBlock = null;
    appleFile = appleTreeNode.getAppleFile ();

    appleFileSystem = appleFile == null ?         //
        appleTreeNode.getAppleFileSystem () :     //
        appleFile.getParentFileSystem ();

    updateNameLabel ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void gridClick (GridClickEvent event)
  // ---------------------------------------------------------------------------------//
  {
    appleBlock = event.block;
    appleFile = appleBlock.getFileOwner ();
    appleFileSystem = appleBlock.getFileSystem ();

    updateNameLabel ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void tabChanged (Tab oldTab, Tab newTab)
  // ---------------------------------------------------------------------------------//
  {
    selectedTab = newTab;

    updateNameLabel ();
  }
}
