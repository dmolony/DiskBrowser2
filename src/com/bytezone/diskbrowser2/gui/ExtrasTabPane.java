package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabPaneBase;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

import javafx.scene.input.KeyCode;

//-----------------------------------------------------------------------------------//
class ExtrasTabPane extends TabPaneBase implements TreeNodeListener
//-----------------------------------------------------------------------------------//
{
  final IncludeFilesTab includeFilesTab = new IncludeFilesTab ("Include", KeyCode.I);
  final FileOptionsTab fileOptionsTab = new FileOptionsTab ("Options", KeyCode.O);
  final DiskLayoutTab diskLayoutTab = new DiskLayoutTab ("Layout", KeyCode.L);

  // ---------------------------------------------------------------------------------//
  ExtrasTabPane (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    add (includeFilesTab);        // filters which file extensions to show
    add (fileOptionsTab);         // display options for each file type
    add (diskLayoutTab);          // disk layout
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    includeFilesTab.setAppleTreeItem (appleTreeItem);
    fileOptionsTab.setAppleTreeItem (appleTreeItem);
    diskLayoutTab.setAppleTreeItem (appleTreeItem);
  }
}
