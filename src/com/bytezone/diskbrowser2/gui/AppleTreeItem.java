package com.bytezone.diskbrowser2.gui;

import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFilePath;
import com.bytezone.filesystem.AppleFileSystem;
import com.bytezone.filesystem.Folder;

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

    assert parent.isAppleContainer ();// || parent.isAppleForkedFile ();

    for (AppleTreeFile treeFile : parent.listAppleFiles ())
      if (treeFile.hasSubdirectories ())    // ApplePath with a separator in the name
      {
        // find or create the folder path
        TreeItem<AppleTreeFile> targetFolder = findTreeItem (children, treeFile);
        targetFolder.getChildren ().add (new AppleTreeItem (treeFile));

        // add children to the Folder (so that the Catalog works)
        Folder folder = (Folder) targetFolder.getValue ().getAppleFile ();
        folder.addFile (treeFile.getAppleFile ());
      }
      else
        children.add (new AppleTreeItem (treeFile));

    return children;
  }

  // Walk the namePath, creating any folders that don't exist
  // ---------------------------------------------------------------------------------//
  private TreeItem<AppleTreeFile> findTreeItem (
      ObservableList<TreeItem<AppleTreeFile>> children, AppleTreeFile treeFile)
  // ---------------------------------------------------------------------------------//
  {
    TreeItem<AppleTreeFile> target = null;      // the folder at the end of the path

    AppleFile file = treeFile.getAppleFile ();
    AppleFileSystem fs = file.getParentFileSystem ();

    assert file instanceof AppleFilePath;

    String fullName = ((AppleFilePath) file).getFullFileName ();
    char separator = ((AppleFilePath) file).getSeparator ();
    String[] folders = Utility.getPathFolders (fullName, separator);

    loop: for (String name : folders)
    {
      for (TreeItem<AppleTreeFile> ati : children)
        if (ati.getValue ().getName ().equals (name))
        {
          children = ati.getChildren ();
          target = ati;
          continue loop;
        }

      AppleFile af = new Folder (fs, name);
      AppleTreeFile tf = new AppleTreeFile (af);
      AppleTreeItem ati = new AppleTreeItem (tf);

      children.add (ati);
      children = ati.getChildren ();
      target = ati;
    }

    return target;
  }
}
