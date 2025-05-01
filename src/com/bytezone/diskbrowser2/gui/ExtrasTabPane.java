package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabPaneBase;
import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

//-----------------------------------------------------------------------------------//
class ExtrasTabPane extends TabPaneBase implements TreeNodeListener
//-----------------------------------------------------------------------------------//
{
  final IncludeFilesTab includeFilesTab = new IncludeFilesTab ("Include", KeyCode.I);
  final FileOptionsTab fileOptionsTab = new FileOptionsTab ("Options", KeyCode.O);
  final DiskLayoutTab diskLayoutTab = new DiskLayoutTab ("Layout", KeyCode.L);

  // ---------------------------------------------------------------------------------//
  ExtrasTabPane (String prefsId, FormattedAppleFileFactory formattedAppleFileFactory)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    add (includeFilesTab);        // filters which file extensions to show
    add (fileOptionsTab);         // display options for each file type
    add (diskLayoutTab);          // disk layout

    fileOptionsTab.setFactory (formattedAppleFileFactory);

    BackgroundFill fill = new BackgroundFill (Color.WHITE, null, null);
    setBackground (new Background (fill));
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
