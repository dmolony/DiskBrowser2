package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import com.bytezone.diskbrowser2.gui.FilterPanel.FilterListener;

import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
class TreePane extends BorderPane implements FilterListener
// -----------------------------------------------------------------------------------//
{
  private final String home = System.getProperty ("user.home");
  private final HeaderBar treeHeaderBar = new HeaderBar ();

  private int[] extensionTotals = new int[AppleTreeView.factory.getSuffixesSize ()];
  private int totalFilesIgnored;

  private final AppleTreeView tree;
  private AppleTreeItem root;
  private File rootFolder;

  private FilterPanel filterPanel;

  // ---------------------------------------------------------------------------------//
  public TreePane (File rootFolder)
  // ---------------------------------------------------------------------------------//
  {
    this.rootFolder = rootFolder;

    root = createTreeRoot ();             // creates an AppleTreeItem from the root folder
    createTree (root);                    // adds all the tree nodes to the root
    tree = new AppleTreeView (root);      // creates the actual tree

    setCenter (tree);

    setTop (treeHeaderBar);
    setTreeHeaderBarName ();
    showTotals ();

    filterPanel = new FilterPanel (extensionTotals);
    filterPanel.addListener (this);
  }

  // ---------------------------------------------------------------------------------//
  AppleTreeView getTree ()
  // ---------------------------------------------------------------------------------//
  {
    return tree;
  }

  // ---------------------------------------------------------------------------------//
  void setRootFolder (AppleTreeItem treeItem)
  // ---------------------------------------------------------------------------------//
  {
    tree.setRootFolder (treeItem);
    setTreeHeaderBarName ();
  }

  // ---------------------------------------------------------------------------------//
  void setTreeHeaderBarName ()
  // ---------------------------------------------------------------------------------//
  {
    String pathName = tree.getRoot ().getValue ().getFile ().toPath ().toString ();

    if (pathName.startsWith (home))
      pathName = pathName.replaceFirst (home, "~");

    treeHeaderBar.leftLabel.setText (pathName);
  }

  // ---------------------------------------------------------------------------------//
  private void createTree (AppleTreeItem rootItem)
  // ---------------------------------------------------------------------------------//
  {
    // Build the entire tree down to the local file/folder level. The only files included are
    // ones that appear to be AppleFileSystem files. They will be expanded when they
    // are selected.

    try (DirectoryStream<Path> directoryStream =
        Files.newDirectoryStream (rootItem.getValue ().getPath ()))
    {
      for (Path path : directoryStream)
      {
        if (Files.isHidden (path))
          continue;

        boolean isLocalDirectory = Files.isDirectory (path);
        String fileName = path.toFile ().getName ();
        int extensionNo = AppleTreeView.factory.getSuffixNumber (fileName);
        if (!isLocalDirectory && extensionNo < 0)
        {
          ++totalFilesIgnored;
          continue;
        }

        AppleTreeItem newItem = new AppleTreeItem (new TreeFile (path));

        if (isLocalDirectory)
        {
          createTree (newItem);
          if (newItem.getChildren ().size () > 0)     // ignore empty folders
            rootItem.getChildren ().add (newItem);
        }
        else if (Files.isRegularFile (path))
        {
          rootItem.getChildren ().add (newItem);
          extensionTotals[extensionNo]++;
        }
        else
          System.out.println ("Unexpected file type - " + path);
      }
    }
    catch (IOException ex)
    {
      ex.printStackTrace ();
    }

    sort (rootItem);
  }

  // ---------------------------------------------------------------------------------//
  private AppleTreeItem createTreeRoot ()
  // ---------------------------------------------------------------------------------//
  {
    AppleTreeItem root = new AppleTreeItem (new TreeFile (rootFolder));
    root.setExpanded (true);

    return root;
  }

  // ---------------------------------------------------------------------------------//
  FilterPanel getFilterPanel ()
  // ---------------------------------------------------------------------------------//
  {
    return filterPanel;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void filterChanged ()
  // ---------------------------------------------------------------------------------//
  {
    if (filterPanel.filtersActive ())
    {
      AppleTreeItem filteredRoot = createTreeRoot ();
      createFilteredTree (root, filteredRoot);
      tree.setRoot (filteredRoot);
    }
    else
      tree.setRoot (root);
  }

  // ---------------------------------------------------------------------------------//
  private void createFilteredTree (AppleTreeItem root, AppleTreeItem filteredRoot)
  // ---------------------------------------------------------------------------------//
  {
    for (TreeItem<TreeFile> child : root.getChildren ())
    {
      AppleTreeItem filteredChild = new AppleTreeItem (child.getValue ());

      createFilteredTree ((AppleTreeItem) child, filteredChild);

      TreeFile filePath = filteredChild.getValue ();

      if (filePath.isLocalFile () && filterPanel.isMatch (filePath))
        filteredRoot.getChildren ().add (filteredChild);
      else if (filePath.isLocalDirectory () && filteredChild.getChildren ().size () > 0)
        filteredRoot.getChildren ().add (filteredChild);
    }
  }

  // ---------------------------------------------------------------------------------//
  void showTotals ()
  // ---------------------------------------------------------------------------------//
  {
    List<String> suffixes = com.bytezone.utility.Utility.getSuffixes ();
    int totalFilesAccepted = 0;

    for (int i = 0; i < extensionTotals.length; i++)
    {
      String extension = suffixes.get (i);
      System.out.printf ("%2d  %-4s  %,6d%n", i, extension, extensionTotals[i]);
      totalFilesAccepted += extensionTotals[i];
    }

    System.out.printf ("        %,8d  (%,d ignored)%n", totalFilesAccepted, totalFilesIgnored);
  }

  // ---------------------------------------------------------------------------------//
  private void sort (AppleTreeItem node)
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
