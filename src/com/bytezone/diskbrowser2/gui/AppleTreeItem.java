package com.bytezone.diskbrowser2.gui;

import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.FileNuFX;
import com.bytezone.filesystem.FolderNuFX;
import com.bytezone.filesystem.FsNuFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

// -----------------------------------------------------------------------------------//
public class AppleTreeItem extends TreeItem<AppleTreeFile>
// -----------------------------------------------------------------------------------//
{
  private boolean firstTimeChildren = true;

  // ---------------------------------------------------------------------------------//
  public AppleTreeItem (AppleTreeFile file)
  // ---------------------------------------------------------------------------------//
  {
    super (file);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public boolean isLeaf ()
  // ---------------------------------------------------------------------------------//
  {
    //    System.out.printf ("%s%n", getValue ().getName ());
    //    System.out.printf ("%s%n", getValue ().isAppleDataFile ());
    //    return getValue ().isAppleFile () && !getValue ().isAppleContainer ();
    return getValue ().isAppleDataFile ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public ObservableList<TreeItem<AppleTreeFile>> getChildren ()
  // ---------------------------------------------------------------------------------//
  {
    AppleTreeFile treeFile = getValue ();

    if (treeFile.isLocalDirectory ())     // already built
      return super.getChildren ();

    if (firstTimeChildren)
    {
      firstTimeChildren = false;

      if (super.getChildren ().size () == 0)        // this MUST be zero - remove later
      {
        // same test as in the AppleTreeView selection listener
        // if down arrow happens first then we do this one

        if (treeFile.isLocalFile () && !treeFile.isAppleFileSystem ())
          treeFile.readAppleFileSystem ();

        super.getChildren ().setAll (buildChildren (treeFile));
      }
      else
        System.out.println ("Unexpected result in getChildren()");
    }

    return super.getChildren ();
  }

  // ---------------------------------------------------------------------------------//
  private ObservableList<AppleTreeItem> buildChildren (AppleTreeFile parent)
  // ---------------------------------------------------------------------------------//
  {
    ObservableList<AppleTreeItem> children = FXCollections.observableArrayList ();

    if (parent.isAppleContainer ())
    {
      for (AppleTreeFile treeFile : parent.listAppleFiles ())
      {
        if (treeFile.hasSubdirectories ())
        {
          String[] folderList = ((FileNuFX) treeFile.getAppleFile ()).getPathFolders ();
          AppleTreeItem ati = findTreeItem (children, folderList[0]);

          if (ati == null)
          {
            AppleFile appleFile = new FolderNuFX (
                (FsNuFX) treeFile.getAppleFile ().getFileSystem (), folderList[0]);
            AppleTreeFile tf = new AppleTreeFile (appleFile);
            AppleTreeItem ti = new AppleTreeItem (tf);
            children.add (ti);
            ti.getChildren ().add (new AppleTreeItem (treeFile));
          }
          else
            ati.getChildren ().add (new AppleTreeItem (treeFile));
        }
        else
          children.add (new AppleTreeItem (treeFile));
      }
    }
    else
      System.out.println ("Unexpected result in buildChildren()");

    return children;
  }

  // ---------------------------------------------------------------------------------//
  private AppleTreeItem findTreeItem (ObservableList<AppleTreeItem> items, String name)
  // ---------------------------------------------------------------------------------//
  {
    for (AppleTreeItem appleTreeItem : items)
      if (appleTreeItem.getValue ().getName ().equals (name))
        return appleTreeItem;

    return null;
  }
}
