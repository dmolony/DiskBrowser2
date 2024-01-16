package com.bytezone.diskbrowser2.gui;

import java.io.File;

import com.bytezone.appbase.TabPaneBase;
import com.bytezone.appleformat.FormattedAppleBlockFactory;
import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.appleformat.block.FormattedAppleBlock;
import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

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

  final FormattedAppleFileFactory formattedAppleFileFactory;
  final FormattedAppleBlockFactory formattedAppleBlockFactory;

  // ---------------------------------------------------------------------------------//
  OutputTabPane (String prefsId, FormattedAppleFileFactory formattedAppleFileFactory,
      FormattedAppleBlockFactory formattedAppleBlockFactory)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    this.formattedAppleFileFactory = formattedAppleFileFactory;
    this.formattedAppleBlockFactory = formattedAppleBlockFactory;

    add (dataTab);
    add (graphicsTab);
    add (hexTab);
    add (extrasTab);
    add (metaTab);
  }

  // ---------------------------------------------------------------------------------//
  FormattedAppleFile getFormattedAppleFile (AppleTreeFile treeFile)
  // ---------------------------------------------------------------------------------//
  {
    AppleFile appleFile = treeFile.getAppleFile ();
    if (appleFile == null)
      return null;

    AppleFileSystem appleFileSystem = treeFile.getAppleFileSystem ();
    File localFile = treeFile.getLocalFile ();
    FormattedAppleFile formattedAppleFile = (FormattedAppleFile) appleFile.getUserData ();

    if (formattedAppleFile == null && appleFile != null)
    {
      formattedAppleFile = formattedAppleFileFactory.getFormattedAppleFile (appleFile);
      appleFile.setUserData (formattedAppleFile);
    }

    if (formattedAppleFile == null && appleFileSystem != null)
    {
      formattedAppleFile =
          formattedAppleFileFactory.getFormattedAppleFile (appleFileSystem);
      appleFile.setUserData (formattedAppleFile);
    }

    if (formattedAppleFile == null && localFile != null)
    {
      formattedAppleFile = formattedAppleFileFactory.getFormattedAppleFile (localFile);
      appleFile.setUserData (formattedAppleFile);
    }

    return formattedAppleFile;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    getFormattedAppleFile (appleTreeItem.getValue ());

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
    FormattedAppleBlock formattedAppleBlock =
        (FormattedAppleBlock) event.block.getUserData ();

    if (formattedAppleBlock == null)
    {
      formattedAppleBlock =
          formattedAppleBlockFactory.getFormattedAppleBlock (event.block);
      event.block.setUserData (formattedAppleBlock);
    }

    dataTab.setAppleBlock (event.block);
  }
}
