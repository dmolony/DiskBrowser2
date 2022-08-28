package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
class TreePane extends BorderPane
// -----------------------------------------------------------------------------------//
{
  private final String home = System.getProperty ("user.home");
  private final AppleTreeView tree;
  private final HeaderBar treeHeaderBar = new HeaderBar ();
  private final int suffixTypes = com.bytezone.utility.Utility.getSuffixes ().size ();
  private int[] extensionTotals = new int[suffixTypes];

  // ---------------------------------------------------------------------------------//
  public TreePane (File rootFolder)
  // ---------------------------------------------------------------------------------//
  {
    AppleTreeItem root = new AppleTreeItem (new TreeFile (rootFolder));
    root.setExpanded (true);
    createTree (root);
    tree = new AppleTreeView (root);
    showTotals ();

    setCenter (tree);
    setTop (treeHeaderBar);
    setFolderName ();
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
    setFolderName ();
  }

  // ---------------------------------------------------------------------------------//
  void setFolderName ()
  // ---------------------------------------------------------------------------------//
  {
    String pathName = tree.getRoot ().getValue ().getFile ().toPath ().toString ();
    if (pathName.startsWith (home))
      pathName = pathName.replace (home, "~");
    treeHeaderBar.leftLabel.setText (pathName);
  }

  // ---------------------------------------------------------------------------------//
  private void createTree (AppleTreeItem rootItem)
  // ---------------------------------------------------------------------------------//
  {
    try (DirectoryStream<Path> directoryStream =
        Files.newDirectoryStream (rootItem.getValue ().getPath ()))
    {
      for (Path path : directoryStream)
      {
        if (Files.isHidden (path))
          continue;

        boolean isDirectory = Files.isDirectory (path);
        int extensionNo = com.bytezone.utility.Utility.getSuffixNo (path.toFile ().getName ());
        if (!isDirectory && extensionNo < 0)
          continue;

        AppleTreeItem newItem = new AppleTreeItem (new TreeFile (path));

        if (isDirectory)
        {
          createTree (newItem);
          if (hasChildren (newItem))
          {
            rootItem.getChildren ().add (newItem);
            //            sort (newItem);
          }
        }
        else if (Files.isRegularFile (path))
        {
          rootItem.getChildren ().add (newItem);
          extensionTotals[extensionNo]++;
        }
        else
          System.out.println ("Unexpected file type");
      }
    }
    catch (IOException ex)
    {
      ex.printStackTrace ();
    }
  }

  // ---------------------------------------------------------------------------------//
  private boolean hasChildren (AppleTreeItem parent)
  // ---------------------------------------------------------------------------------//
  {
    return parent.getChildren ().size () > 0;
  }

  // ---------------------------------------------------------------------------------//
  //  private TreeItem<TreeFile> createTreeRoot ()
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    TreeItem<TreeFile> root = new TreeItem<> (new TreeFile (rootFolder));
  //    root.setExpanded (true);
  //
  //    return root;
  //  }

  // ---------------------------------------------------------------------------------//
  void showTotals ()
  // ---------------------------------------------------------------------------------//
  {
    List<String> suffixes = com.bytezone.utility.Utility.getSuffixes ();
    int total = 0;
    for (int i = 0; i < extensionTotals.length; i++)
    {
      String extension = suffixes.get (i);
      System.out.printf ("%2d  %-4s  %,6d%n", i, extension, extensionTotals[i]);
      total += extensionTotals[i];
    }
    System.out.printf ("        %,8d%n", total);
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
