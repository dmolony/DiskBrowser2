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
      assert super.getChildren ().size () == 0;

      // same test as in AppleTreeView.itemSelected()
      // if the item is opened BEFORE it is selected then we do this one

      if (treeFile.isLocalFile () && !treeFile.isAppleFileSystem ())
        treeFile.readAppleFileSystem ();

      super.getChildren ().setAll (buildChildren (treeFile));
    }

    return super.getChildren ();
  }

  // ---------------------------------------------------------------------------------//
  private ObservableList<TreeItem<AppleTreeFile>> buildChildren (AppleTreeFile parent)
  // ---------------------------------------------------------------------------------//
  {
    ObservableList<TreeItem<AppleTreeFile>> children =
        FXCollections.observableArrayList ();

    assert parent.isAppleContainer ();

    for (AppleTreeFile treeFile : parent.listAppleFiles ())
      if (treeFile.hasSubdirectories ())                    // must contain a FileNuFX
      {
        TreeItem<AppleTreeFile> targetFolder = findTreeItem (children, treeFile);
        targetFolder.getChildren ().add (new AppleTreeItem (treeFile));
        targetFolder.getValue ().getAppleFile ().addFile (treeFile.getAppleFile ());
      }
      else
        children.add (new AppleTreeItem (treeFile));

    return children;
  }

  // Walk the namePath, creating any folders that don't exist
  // ---------------------------------------------------------------------------------//
  private TreeItem<AppleTreeFile>
      findTreeItem (ObservableList<TreeItem<AppleTreeFile>> items, AppleTreeFile treeFile)
  // ---------------------------------------------------------------------------------//
  {
    TreeItem<AppleTreeFile> target = null;      // the folder at the end of the path

    FileNuFX fileNuFX = (FileNuFX) treeFile.getAppleFile ();
    String[] namePath = fileNuFX.getPathFolders ();
    FsNuFX fs = (FsNuFX) fileNuFX.getFileSystem ();

    loop: for (String name : namePath)
    {
      for (TreeItem<AppleTreeFile> ati : items)
        if (ati.getValue ().getName ().equals (name))
        {
          items = ati.getChildren ();
          target = ati;
          continue loop;
        }

      AppleFile af = new FolderNuFX (fs, name);
      AppleTreeFile tf = new AppleTreeFile (af);
      AppleTreeItem ati = new AppleTreeItem (tf);

      items.add (ati);
      items = ati.getChildren ();
      target = ati;
    }

    return target;
  }
}
