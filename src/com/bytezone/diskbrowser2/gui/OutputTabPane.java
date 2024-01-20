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

  //  final FormattedAppleFileFactory formattedAppleFileFactory;
  //  final FormattedAppleBlockFactory formattedAppleBlockFactory;

  // ---------------------------------------------------------------------------------//
  OutputTabPane (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    //    this.formattedAppleFileFactory = formattedAppleFileFactory;
    //    this.formattedAppleBlockFactory = formattedAppleBlockFactory;

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
    //    FormattedAppleFile faf = getFormattedAppleFile (appleTreeItem.getValue ());

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
    AppleBlock appleBlock = event.block;

    //    if (appleBlock.getUserData () == null)
    //      appleBlock
    //          .setUserData (formattedAppleBlockFactory.getFormattedAppleBlock (appleBlock));

    dataTab.setAppleBlock (appleBlock);
    graphicsTab.setAppleBlock (appleBlock);
    extrasTab.setAppleBlock (appleBlock);
    hexTab.setAppleBlock (appleBlock);
    metaTab.setAppleBlock (appleBlock);
  }
}
