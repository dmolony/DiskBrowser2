package com.bytezone.diskbrowser2.gui;

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
        // same test as in the selection listener
        if (treeFile.isLocalFile () && !treeFile.isAppleFileSystem ())
          treeFile.setAppleFile (AppleTreeView.factory.getFileSystem (treeFile.getFile ()));

        super.getChildren ().setAll (buildChildren (treeFile));
      }
      else
        System.out.println ("Unexpected result in getChildren()");
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
        children.add (new AppleTreeItem (treeFile));
    else
      System.out.println ("Unexpected result in buildChildren()");

    //    System.out.printf ("building: %4d in %s%n", children.size (), parent.getName ());
    return children;
  }
}
