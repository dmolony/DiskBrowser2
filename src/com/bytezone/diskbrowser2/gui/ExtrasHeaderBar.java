package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabChangeListener;
import com.bytezone.appleformat.FormattedAppleFile;
import com.bytezone.appleformat.Preferences;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;
import com.bytezone.filesystem.AppleFileSystem.FileSystemType;

import javafx.scene.control.Tab;

// -----------------------------------------------------------------------------------//
public class ExtrasHeaderBar extends HeaderBar
    implements TreeNodeListener, TabChangeListener
// -----------------------------------------------------------------------------------//
{
  private AppleTreeFile treeFile;
  private Tab selectedTab;

  // ---------------------------------------------------------------------------------//
  void updateNameLabel ()
  // ---------------------------------------------------------------------------------//
  {
    if (treeFile == null)
    {
      leftLabel.setText ("");
      return;
    }

    if (selectedTab instanceof LayoutTab tab)
    {
      AppleFileSystem fs = treeFile.getAppleFileSystem ();
      AppleFile file = treeFile.getAppleFile ();
      FileSystemType fst = null;

      if (fs != null)
        fst = fs.getFileSystemType ();
      else if (file != null)
        fst = file.getFileSystemType ();

      if (fst != null)
        leftLabel.setText (fst.name ());
      else
        leftLabel.setText ("File System");
    }
    else if (selectedTab instanceof OptionsTab tab)
    {
      FormattedAppleFile formattedAppleFile = treeFile.getFormattedAppleFile ();

      if (formattedAppleFile != null)
      {
        Preferences preferences = formattedAppleFile.getPreferences ();
        if (preferences != null)
          leftLabel.setText (preferences.getName ());
        else
          leftLabel.setText ("Options");
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
    treeFile = appleTreeItem.getValue ();
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
