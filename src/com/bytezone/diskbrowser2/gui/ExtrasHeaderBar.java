package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabChangeListener;
import com.bytezone.appleformat.ApplePreferences;
import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleFileSystem;
import com.bytezone.filesystem.AppleFileSystem.FileSystemType;

import javafx.scene.control.Tab;

// -----------------------------------------------------------------------------------//
public class ExtrasHeaderBar extends HeaderBar
    implements TreeNodeListener, TabChangeListener
// -----------------------------------------------------------------------------------//
{
  private AppleTreeFile appleTreeFile;
  private FormattedAppleFile formattedAppleFile;
  private Tab selectedTab;

  // ---------------------------------------------------------------------------------//
  void updateNameLabel ()
  // ---------------------------------------------------------------------------------//
  {
    if (appleTreeFile == null)
    {
      leftLabel.setText ("");
      return;
    }

    if (selectedTab instanceof DiskLayoutTab tab)
    {
      AppleFileSystem appleFileSystem = appleTreeFile.getAppleFileSystem ();
      if (appleFileSystem != null)
      {
        // this should include the dos version
        FileSystemType fst = appleFileSystem.getFileSystemType ();
        leftLabel.setText (fst != null ? fst.name () : "File System");
      }
      else
        leftLabel.setText ("");
    }
    else if (selectedTab instanceof FileOptionsTab tab)
    {
      if (formattedAppleFile != null)
      {
        ApplePreferences preferences = formattedAppleFile.getPreferences ();
        leftLabel.setText (preferences != null ? preferences.getName () : "Options");
      }
      else
        leftLabel.setText ("Options");
    }
    else
      leftLabel.setText ("File Filter");
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    appleTreeFile = appleTreeItem.getValue ();
    formattedAppleFile = appleTreeFile.getFormattedAppleFile ();

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
