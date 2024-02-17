package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabPaneBase;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleBlock;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class OutputTabPane extends TabPaneBase implements TreeNodeListener, GridClickListener
// -----------------------------------------------------------------------------------//
{
  final DataTab dataTab = new DataTab ("Data", KeyCode.D);
  final GraphicsTab graphicsTab = new GraphicsTab ("Graphics", KeyCode.G);
  final HexTab hexTab = new HexTab ("Hex", KeyCode.H);
  final ExtrasTab extrasTab = new ExtrasTab ("Extras", KeyCode.E);
  final MetaTab metaTab = new MetaTab ("Meta", KeyCode.M);

  private AppleTreeItem appleTreeItem;
  private AppleBlock appleBlock;

  // ---------------------------------------------------------------------------------//
  OutputTabPane (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    add (dataTab);
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
    if (this.appleTreeItem == appleTreeItem)
      return;

    this.appleTreeItem = appleTreeItem;
    appleBlock = null;

    dataTab.setAppleTreeItem (appleTreeItem);
    graphicsTab.setAppleTreeItem (appleTreeItem);
    extrasTab.setAppleTreeItem (appleTreeItem);
    hexTab.setAppleTreeItem (appleTreeItem);
    metaTab.setAppleTreeItem (appleTreeItem);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void gridClick (GridClickEvent event)
  // ---------------------------------------------------------------------------------//
  {
    if (appleBlock == event.block)
      return;

    appleTreeItem = null;
    appleBlock = event.block;

    dataTab.setAppleBlock (appleBlock);
    graphicsTab.setAppleBlock (appleBlock);
    extrasTab.setAppleBlock (appleBlock);
    hexTab.setAppleBlock (appleBlock);
    metaTab.setAppleBlock (appleBlock);
  }
}
