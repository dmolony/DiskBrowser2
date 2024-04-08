package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabPaneBase;
import com.bytezone.appleformat.FormattedAppleBlockFactory;
import com.bytezone.appleformat.FormattedAppleFileFactory;
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
  ExtrasTabPane (String prefsId, FormattedAppleBlockFactory formattedAppleBlockFactory,
      FormattedAppleFileFactory formattedAppleFileFactory)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    add (includeFilesTab);        // filters which file extensions to show
    add (fileOptionsTab);         // display options for each file type
    add (diskLayoutTab);          // disk layout

    fileOptionsTab.setFactory (formattedAppleFileFactory);
    diskLayoutTab.setFactory (formattedAppleBlockFactory);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeNode appleTreeNode)
  // ---------------------------------------------------------------------------------//
  {
    includeFilesTab.setAppleTreeNode (appleTreeNode);
    fileOptionsTab.setAppleTreeNode (appleTreeNode);
    diskLayoutTab.setAppleTreeNode (appleTreeNode);
  }
}
