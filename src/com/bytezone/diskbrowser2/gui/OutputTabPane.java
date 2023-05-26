package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabPaneBase;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class OutputTabPane extends TabPaneBase implements TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  final OutputTab outputTab = new OutputTab ("Data", KeyCode.D);
  final GraphicsTab graphicsTab = new GraphicsTab ("Graphics", KeyCode.G);
  final HexTab hexTab = new HexTab ("Hex", KeyCode.H);
  final ExtrasTab extrasTab = new ExtrasTab ("Extras", KeyCode.E);
  final MetaTab metaTab = new MetaTab ("Meta", KeyCode.M);

  // ---------------------------------------------------------------------------------//
  OutputTabPane (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    add (outputTab);
    add (graphicsTab);
    add (hexTab);
    add (extrasTab);
    add (metaTab);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    outputTab.setAppleTreeItem (appleTreeItem);
    graphicsTab.setAppleTreeItem (appleTreeItem);
    extrasTab.setAppleTreeItem (appleTreeItem);
    hexTab.setAppleTreeItem (appleTreeItem);
    metaTab.setAppleTreeItem (appleTreeItem);
  }
}
