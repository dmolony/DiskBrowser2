package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.bytezone.appbase.TabPaneBase;
import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.appleformat.file.FormattedAppleFile;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;
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
  private Map<AppleTreeNode, FormattedAppleFile> formatMap = new HashMap<> ();

  // ---------------------------------------------------------------------------------//
  OutputTabPane (String prefsId, FormattedAppleFileFactory factory)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);
    this.formattedAppleFileFactory = factory;

    add (dataTab);
    add (graphicsTab);
    add (hexTab);
    add (extrasTab);
    add (metaTab);
  }

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
    if (appleBlock == event.block)
      return;

    treeNode = null;
    formattedAppleFile = null;
    appleBlock = event.block;

    dataTab.setAppleBlock (appleBlock);
    graphicsTab.setAppleBlock (appleBlock);
    extrasTab.setAppleBlock (appleBlock);
    hexTab.setAppleBlock (appleBlock);
    metaTab.setAppleBlock (appleBlock);
  }
}
