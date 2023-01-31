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
  final OutputTab outputTab = new OutputTab ("Data", KeyCode.D);
  final MetaTab metaTab = new MetaTab ("Meta", KeyCode.M);
  final GraphicsTab graphicsTab = new GraphicsTab ("Graphics", KeyCode.G);

  private TreeFile treeFile;                    // the item to display
  private AppleFile appleFile;

  private FormattedAppleFile formattedAppleFile;
  //  private FormattedAppleFileFactory factory = new FormattedAppleFileFactory ();

  // ---------------------------------------------------------------------------------//
  OutputTabPane (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    add (metaTab);
    add (hexTab);
    add (outputTab);
    add (graphicsTab);

    setDefaultTab (2);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    treeFile = appleTreeItem.getValue ();
    appleFile = treeFile.isAppleDataFile () ? treeFile.getAppleFile () : null;

    System.out.println ("selected: " + appleTreeItem);

    //    if (treeFile.isAppleFileSystem ())
    //    {
    //      //      String catalog = treeFile.getAppleFile ().catalog ();
    //      //      return Arrays.asList (catalog.split ("\n"));
    //    }
    //    else if (treeFile.isAppleFolder ())
    //    {
    //      //      String catalog = treeFile.getAppleFile ().catalog ();
    //      //      return Arrays.asList (catalog.split ("\n"));
    //    }
    //    else if (treeFile.isAppleDataFile ())
    //    {
    formattedAppleFile = treeFile.getFormattedAppleFile ();
    //    }

    outputTab.setFormatter (formattedAppleFile);
    hexTab.setFormatter (formattedAppleFile);
    metaTab.setFormatter (formattedAppleFile);
    graphicsTab.setFormatter (formattedAppleFile);
  }
}
