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
  //  private AppleTreeNode appleTreeNode;
  private AppleBlock appleBlock;
  private AppleFileSystem appleFileSystem;
  //  private FormattedAppleFile formattedAppleFile;

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
    {
      //      if (formattedAppleFile != null)
      //      {
      //        ApplePreferences preferences = formattedAppleFile.getPreferences ();
      //        leftLabel.setText (preferences != null ? preferences.getName () : "Options");
      //      }
      //      else
      leftLabel.setText ("Options");
    }
    else
      leftLabel.setText ("File Filter");
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeNode appleTreeNode)
  // ---------------------------------------------------------------------------------//
  {
    appleBlock = null;
    //    this.appleTreeNode = appleTreeNode;
    appleFile = appleTreeNode.getAppleFile ();
    //    formattedAppleFile = appleTreeNode.getFormattedAppleFile ();

    if (appleFile != null)
    {
      if (appleFile.hasEmbeddedFileSystem ())
        appleFileSystem = appleFile.getEmbeddedFileSystem ();
      else
        appleFileSystem = appleFile.getParentFileSystem ();
    }
    else
      appleFileSystem = appleTreeNode.getAppleFileSystem ();

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

  // ---------------------------------------------------------------------------------//
  @Override
  public void gridClick (GridClickEvent event)
  // ---------------------------------------------------------------------------------//
  {
    appleBlock = event.block;
    //    appleTreeNode = null;
    appleFile = appleBlock.getFileOwner ();
    appleFileSystem = appleBlock.getFileSystem ();
    //    formattedAppleFile = null;

    updateNameLabel ();
  }
}
