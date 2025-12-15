package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.bytezone.appbase.TabPaneBase;
import com.bytezone.appleformat.FormattedAppleBlockFactory;
import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.appleformat.block.FormattedAppleBlock;
import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
import com.bytezone.diskbrowser2.gui.GridClickEvent.GridClickType;
import com.bytezone.filesystem.AppleBlock;
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

  private AppleTreeNode treeNode;
  private FormattedAppleFile formattedAppleFile;
  private AppleBlock appleBlock;

  private final FormattedAppleFileFactory formattedAppleFileFactory;
  private final FormattedAppleBlockFactory formattedAppleBlockFactory;

  private final Map<AppleTreeNode, FormattedAppleFile> formatMap = new HashMap<> ();
  private final Map<AppleBlock, FormattedAppleBlock> formattedBlockMap = new HashMap<> ();

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

  // should be a call to FormattedAppleBlock.of (appleBlock)
  // ---------------------------------------------------------------------------------//
  FormattedAppleBlock getFormattedAppleBlock (AppleBlock appleBlock)
  // ---------------------------------------------------------------------------------//
  {
    FormattedAppleBlock formattedAppleBlock = formattedBlockMap.get (appleBlock);
    if (formattedAppleBlock == null)
    {
      formattedAppleBlock =
          formattedAppleBlockFactory.getFormattedAppleBlock (appleBlock);
      formattedBlockMap.put (appleBlock, formattedAppleBlock);
    }

    return formattedAppleBlock;
  }

  // should be a call to FormattedAppleFile.of (appleFile)
  // ---------------------------------------------------------------------------------//
  FormattedAppleFile getFormattedAppleFile (AppleTreeNode treeNode)
  // ---------------------------------------------------------------------------------//
  {
    FormattedAppleFile formattedAppleFile = formatMap.get (treeNode);
    if (formattedAppleFile != null)
      return formattedAppleFile;

    AppleFile appleFile = treeNode.getAppleFile ();

    if (appleFile != null)
      formattedAppleFile = formattedAppleFileFactory.getFormattedAppleFile (appleFile);

    if (formattedAppleFile != null)
    {
      formatMap.put (treeNode, formattedAppleFile);
      return formattedAppleFile;
    }

    AppleFileSystem appleFileSystem = treeNode.getAppleFileSystem ();

    if (appleFileSystem != null)
      formattedAppleFile =
          formattedAppleFileFactory.getFormattedAppleFile (appleFileSystem);

    if (formattedAppleFile != null)
    {
      formatMap.put (treeNode, formattedAppleFile);
      return formattedAppleFile;
    }

    File localFile = treeNode.getLocalFile ();

    if (localFile != null && localFile.isDirectory ())
      formattedAppleFile = formattedAppleFileFactory.getFormattedAppleFile (localFile);

    if (formattedAppleFile != null)
    {
      formatMap.put (treeNode, formattedAppleFile);
      return formattedAppleFile;
    }

    assert formattedAppleFile != null;
    return null;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void treeNodeSelected (AppleTreeNode treeNode)
  // ---------------------------------------------------------------------------------//
  {
    if (this.treeNode == treeNode)
      return;

    this.treeNode = treeNode;
    formattedAppleFile = getFormattedAppleFile (treeNode);
    appleBlock = null;

    dataTab.setAppleTreeNode (formattedAppleFile);
    graphicsTab.setAppleTreeNode (treeNode, formattedAppleFile);
    extrasTab.setAppleTreeNode (formattedAppleFile);
    hexTab.setAppleTreeNode (treeNode, formattedAppleFile);
    metaTab.setAppleTreeNode (treeNode);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void gridClick (GridClickEvent event)
  // ---------------------------------------------------------------------------------//
  {
    switch (event.gridClickType)
    {
      case GridClickType.SINGLE:
        if (appleBlock == event.block)
          return;

        treeNode = null;
        formattedAppleFile = null;
        appleBlock = event.block;

        if (appleBlock.getUserData () == null)
          appleBlock.setUserData (getFormattedAppleBlock (event.block));

        dataTab.setAppleBlock (appleBlock);
        graphicsTab.setAppleBlock (appleBlock);
        extrasTab.setAppleBlock (appleBlock);
        hexTab.setAppleBlock (appleBlock);
        metaTab.setAppleBlock (appleBlock);
        break;

      case MULTI:
        System.out.println ("MULTI grid click not written");
        break;
    }
  }
}
