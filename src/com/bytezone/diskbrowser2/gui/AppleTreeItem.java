package com.bytezone.diskbrowser2.gui;

import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFilePath;
import com.bytezone.filesystem.AppleFileSystem;
import com.bytezone.filesystem.Folder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

// -----------------------------------------------------------------------------------//
public class AppleTreeItem extends TreeItem<AppleTreeNode>
// -----------------------------------------------------------------------------------//
{
  private boolean firstTimeChildren = true;

  // ---------------------------------------------------------------------------------//
  public AppleTreeItem (AppleTreeNode treeNode)
  // ---------------------------------------------------------------------------------//
  {
    super (treeNode);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public boolean isLeaf ()
  // ---------------------------------------------------------------------------------//
  {
    return getValue ().isAppleDataFile ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public ObservableList<TreeItem<AppleTreeNode>> getChildren ()
  // ---------------------------------------------------------------------------------//
  {
    AppleTreeNode treeNode = getValue ();

    if (treeNode.isLocalDirectory ())     // already built
      return super.getChildren ();

    if (firstTimeChildren)
    {
      firstTimeChildren = false;
      assert super.getChildren ().size () == 0;

      // if the node is OPENED before it is SELECTED
      treeNode.checkForFileSystem ();    // also called by AppleTreeView.itemSelected()

      super.getChildren ().setAll (buildChildren (treeNode));
    }

    return super.getChildren ();
  }

  // ---------------------------------------------------------------------------------//
  private ObservableList<TreeItem<AppleTreeNode>> buildChildren (AppleTreeNode parent)
  // ---------------------------------------------------------------------------------//
  {
    ObservableList<TreeItem<AppleTreeNode>> children =
        FXCollections.observableArrayList ();

    assert parent.isAppleContainer ();

    for (AppleTreeNode treeNode : parent.listAppleFiles ())
      if (treeNode.hasSubdirectories ())    // ApplePath with a separator in the name
      {
        // find or create the folder path
        TreeItem<AppleTreeNode> targetFolder = findTreeItem (children, treeNode);
        targetFolder.getChildren ().add (new AppleTreeItem (treeNode));

        // add children to the Folder (so that the Catalog works)
        Folder folder = (Folder) targetFolder.getValue ().getAppleFile ();
        folder.addFile (treeNode.getAppleFile ());
      }
      else
        children.add (new AppleTreeItem (treeNode));

    if (children.size () > 0)
      AppleTreeView.getTreeView ().startBuilding (this);              // JDK-8293018

    return children;
  }

  // Walk the namePath, creating any folders that don't exist
  // ---------------------------------------------------------------------------------//
  private TreeItem<AppleTreeNode> findTreeItem (
      ObservableList<TreeItem<AppleTreeNode>> children, AppleTreeNode treeFile)
  // ---------------------------------------------------------------------------------//
  {
    TreeItem<AppleTreeNode> targetFolder = null;    // the folder at the end of the path

    AppleFile file = treeFile.getAppleFile ();
    assert file instanceof AppleFilePath;

    AppleFileSystem fs = file.getParentFileSystem ();
    String[] folders = getPathFolders ((AppleFilePath) file);

    loop: for (String name : folders)
    {
      for (TreeItem<AppleTreeNode> treeItem : children)
        if (treeItem.getValue ().getName ().equals (name))
        {
          children = treeItem.getChildren ();
          targetFolder = treeItem;
          continue loop;
        }

      AppleFile appleFile = new Folder (fs, name);
      AppleTreeNode treeNode = new AppleTreeNode (appleFile);
      AppleTreeItem treeItem = new AppleTreeItem (treeNode);

      children.add (treeItem);
      children = treeItem.getChildren ();
      targetFolder = treeItem;
    }

    return targetFolder;
  }

  // ---------------------------------------------------------------------------------//
  private String[] getPathFolders (AppleFilePath filePath)
  // ---------------------------------------------------------------------------------//
  {
    String fullName = filePath.getFullFileName ();
    char separator = filePath.getSeparator ();

    String[] pathItems = fullName.split ("\\" + separator);
    String[] pathFolders = new String[pathItems.length - 1];

    for (int i = 0; i < pathFolders.length; i++)
      pathFolders[i] = pathItems[i];

    return pathFolders;
  }
}
