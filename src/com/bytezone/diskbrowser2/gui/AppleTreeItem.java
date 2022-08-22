package com.bytezone.diskbrowser2.gui;

import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

// -----------------------------------------------------------------------------------//
public class AppleTreeItem extends TreeItem<TreeFile>
// -----------------------------------------------------------------------------------//
{
  private boolean isLeaf;
  private boolean firstTimeChildren = true;
  private boolean firstTimeLeaf = true;

  // ---------------------------------------------------------------------------------//
  public AppleTreeItem (TreeFile file)
  // ---------------------------------------------------------------------------------//
  {
    super (file);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public ObservableList<TreeItem<TreeFile>> getChildren ()
  // ---------------------------------------------------------------------------------//
  {
    if (firstTimeChildren)
    {
      firstTimeChildren = false;
      super.getChildren ().setAll (buildChildren ());
    }

    return super.getChildren ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public boolean isLeaf ()
  // ---------------------------------------------------------------------------------//
  {
    if (firstTimeLeaf)
    {
      firstTimeLeaf = false;
      TreeFile treeFile = getValue ();

      if (treeFile.isFile () || treeFile.isDirectory ())
        isLeaf = false;
      else
      {
        AppleFile appleFile = treeFile.getAppleFile ();
        isLeaf = !(appleFile.isDirectory () || appleFile.isFileSystem ());
      }
    }

    return isLeaf;
  }

  // ---------------------------------------------------------------------------------//
  private ObservableList<AppleTreeItem> buildChildren ()
  // ---------------------------------------------------------------------------------//
  {
    TreeFile treeFile = getValue ();
    ObservableList<AppleTreeItem> children = FXCollections.observableArrayList ();

    if (treeFile.isDirectory ())
    {
      for (TreeFile childFile : treeFile.listFiles ())
        if (childFile.isDirectory () || Utility.isValidExtension (childFile.getName ()))
          children.add (new AppleTreeItem (childFile));
    }
    else if (treeFile.isFile ())
    {
      AppleFileSystem fs = AppleTreeView.factory.getFileSystem (treeFile.getFile ());
      for (AppleFile appleFile : fs.getFiles ())
      {
        children.add (new AppleTreeItem (new TreeFile (appleFile)));
        //        System.out.println (appleFile);
      }
    }
    else if (treeFile.isAppleFileSystem () || treeFile.isAppleDirectory ())
    {
      for (AppleFile appleFile : treeFile.listAppleFiles ())
        children.add (new AppleTreeItem (new TreeFile (appleFile)));
    }

    return children;
  }
}
