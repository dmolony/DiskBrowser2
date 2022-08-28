package com.bytezone.diskbrowser2.gui;

import java.util.Comparator;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

// -----------------------------------------------------------------------------------//
public class AppleTreeItem extends TreeItem<TreeFile>
// -----------------------------------------------------------------------------------//
{
  private boolean firstTimeChildren = true;

  // ---------------------------------------------------------------------------------//
  public AppleTreeItem (TreeFile file)
  // ---------------------------------------------------------------------------------//
  {
    super (file);
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
  public ObservableList<TreeItem<TreeFile>> getChildren ()
  // ---------------------------------------------------------------------------------//
  {
    TreeFile treeFile = getValue ();
    if (treeFile.isLocalDirectory ())     // already built
      return super.getChildren ();

    if (firstTimeChildren)
    {
      firstTimeChildren = false;

      if (super.getChildren ().size () == 0)
      {
        if (treeFile.isLocalFile ())      // all local files should be file systems
          treeFile.setAppleFile (AppleTreeView.factory.getFileSystem (treeFile.getFile ()));

        super.getChildren ().setAll (buildChildren (treeFile));
      }
    }

    return super.getChildren ();
  }

  // ---------------------------------------------------------------------------------//
  private ObservableList<AppleTreeItem> buildChildren (TreeFile parent)
  // ---------------------------------------------------------------------------------//
  {
    ObservableList<AppleTreeItem> children = FXCollections.observableArrayList ();

    if (parent.isAppleFileSystem () || parent.isAppleFolder ())
      for (TreeFile treeFile : parent.listAppleFiles ())
      {
        children.add (new AppleTreeItem (treeFile));
        //        System.out.println (treeFile.getName ());
      }

    System.out.printf ("building: %4d in %s%n", children.size (), parent.getName ());
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
