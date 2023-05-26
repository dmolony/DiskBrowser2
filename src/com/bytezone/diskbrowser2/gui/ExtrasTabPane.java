package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabPaneBase;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

import javafx.scene.input.KeyCode;

//-----------------------------------------------------------------------------------//
class ExtrasTabPane extends TabPaneBase implements TreeNodeListener
//-----------------------------------------------------------------------------------//
{
  final FilterTab filterTab = new FilterTab ("Include", KeyCode.I);
  final OptionsTab optionsTab = new OptionsTab ("Options", KeyCode.O);
  final LayoutTab layoutTab = new LayoutTab ("Layout", KeyCode.L);

  // ---------------------------------------------------------------------------------//
  ExtrasTabPane (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    add (filterTab);        // filters which file extensions to show
    add (optionsTab);       // display options for each file type
    add (layoutTab);        // disk layout
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    filterTab.setAppleTreeItem (appleTreeItem);
    optionsTab.setAppleTreeItem (appleTreeItem);
    layoutTab.setAppleTreeItem (appleTreeItem);
  }
}
