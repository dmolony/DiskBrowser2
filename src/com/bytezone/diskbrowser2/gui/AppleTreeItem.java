package com.bytezone.diskbrowser2.gui;

import java.util.Comparator;
import java.util.function.Function;

import com.bytezone.filesystem.AppleFile;

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
      sort (this);
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
    TreeFile parent = getValue ();
    ObservableList<AppleTreeItem> children = FXCollections.observableArrayList ();

    if (parent.isDirectory ())
    {
      for (TreeFile treeFile : parent.listFiles ())
        if (treeFile.isDirectory ()
            || AppleTreeView.factory.getSuffixNumber (treeFile.getName ()) >= 0)
          children.add (new AppleTreeItem (treeFile));
    }
    else if (parent.isFile () && !parent.isAppleFileSystem ())
      parent.setAppleFile (AppleTreeView.factory.getFileSystem (parent.getFile ()));

    if (parent.isAppleFileSystem () || parent.isAppleDirectory ())
      for (TreeFile treeFile : parent.listAppleFiles ())
        children.add (new AppleTreeItem (treeFile));

    return children;
  }

  // ---------------------------------------------------------------------------------//
  private void sort (TreeItem<TreeFile> node)
  // ---------------------------------------------------------------------------------//
  {
    node.getChildren ().sort (Comparator.comparing (new Function<TreeItem<TreeFile>, String> ()
    {
      @Override
      public String apply (TreeItem<TreeFile> t)
      {
        return t.getValue ().getSortString ();
      }
    }));
  }
}
