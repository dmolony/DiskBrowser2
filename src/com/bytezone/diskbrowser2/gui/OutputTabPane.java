package com.bytezone.diskbrowser2.gui;

import com.bytezone.appbase.TabPaneBase;
import com.bytezone.appleformat.FormattedAppleFile;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleFile;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
class OutputTabPane extends TabPaneBase implements TreeNodeListener
// -----------------------------------------------------------------------------------//
{
  final HexTab hexTab = new HexTab ("Hex", KeyCode.H);
  final OutputTab outputTab = new OutputTab ("Formatted", KeyCode.F);
  final MetaTab metaTab = new MetaTab ("Meta", KeyCode.M);
  final GraphicsTab graphicsTab = new GraphicsTab ("Graphics", KeyCode.G);
  final ExtrasTab extrasTab = new ExtrasTab ("Extras", KeyCode.E);

  private TreeFile treeFile;                    // the item to display
  private AppleFile appleFile;

  private FormattedAppleFile formattedAppleFile;

  // ---------------------------------------------------------------------------------//
  OutputTabPane (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    add (outputTab);
    add (graphicsTab);
    add (hexTab);
    add (metaTab);
    add (extrasTab);

    setDefaultTab (0);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    treeFile = appleTreeItem.getValue ();
    appleFile = treeFile.isAppleDataFile () ? treeFile.getAppleFile () : null;

    System.out.println ("selected: " + appleTreeItem);
    formattedAppleFile = treeFile.getFormattedAppleFile ();

    outputTab.setFormattedAppleFile (formattedAppleFile);
    hexTab.setFormattedAppleFile (formattedAppleFile);
    metaTab.setFormattedAppleFile (formattedAppleFile);
    graphicsTab.setFormattedAppleFile (formattedAppleFile);
    extrasTab.setFormattedAppleFile (formattedAppleFile);
  }
}
