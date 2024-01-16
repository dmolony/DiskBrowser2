package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabPaneBase;
import com.bytezone.appleformat.FormattedAppleBlockFactory;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

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

  final FormattedAppleBlockFactory formattedAppleBlockFactory;

  // ---------------------------------------------------------------------------------//
  OutputTabPane (FormattedAppleBlockFactory formattedAppleBlockFactory, String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    this.formattedAppleBlockFactory = formattedAppleBlockFactory;

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
    dataTab.setAppleBlock (event.block,
        formattedAppleBlockFactory.getFormattedAppleBlock (event.block));
  }
}
