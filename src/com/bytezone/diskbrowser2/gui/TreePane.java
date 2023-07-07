package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.prefs.Preferences;

import com.bytezone.appbase.FontChangeListener;
import com.bytezone.appbase.SaveState;
import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
class TreePane extends BorderPane
    implements RootFolderChangeListener, FontChangeListener, SaveState
// -----------------------------------------------------------------------------------//
{
  private int[] extensionTotals =
      new int[AppleTreeView.fileSystemFactory.getSuffixesSize ()];
  private int totalFilesIgnored;

  private AppleTreeView treeView;
  private AppleTreeItem root;
  private File rootFolder;

  private FormattedAppleFileFactory formattedAppleFileFactory;

  private List<SuffixTotalsListener> listeners = new ArrayList<> ();
  private final List<TreeNodeListener> treeNodeListeners = new ArrayList<> ();

  //  private FilterPanel filterPanel;

  // ---------------------------------------------------------------------------------//
  public TreePane (FormattedAppleFileFactory formattedAppleFileFactory)
  // ---------------------------------------------------------------------------------//
  {
    this.formattedAppleFileFactory = formattedAppleFileFactory;

    //    filterPanel = new FilterPanel (extensionTotals);
    //    filterPanel.addListener (this);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void rootFolderChanged (File rootFolder)
  // ---------------------------------------------------------------------------------//
  {
    this.rootFolder = rootFolder;

    root = createTreeRoot ();         // creates an AppleTreeItem from the root folder
    createTree (root);                // adds all the tree nodes to the root

    treeView = new AppleTreeView (root, formattedAppleFileFactory);

    for (TreeNodeListener treeNodeListener : treeNodeListeners)
      treeView.addListener (treeNodeListener);

    setCenter (treeView);

    showTotals ();
  }

  // ---------------------------------------------------------------------------------//
  private void createTree (AppleTreeItem rootItem)
  // ---------------------------------------------------------------------------------//
  {
    // Build the entire tree down to the local file/folder level. The only files
    // included are ones that appear to be AppleFileSystem files. They will be 
    // expanded when they are selected.

    try (DirectoryStream<Path> directoryStream =
        Files.newDirectoryStream (rootItem.getValue ().getPath ()))
    {
      for (Path path : directoryStream)
      {
        if (Files.isHidden (path))
          continue;

        boolean isLocalDirectory = Files.isDirectory (path);
        String fileName = path.toFile ().getName ();
        int extensionNo = AppleTreeView.fileSystemFactory.getSuffixNumber (fileName);
        if (!isLocalDirectory && extensionNo < 0)
        {
          ++totalFilesIgnored;
          continue;
        }

        AppleTreeItem newItem = new AppleTreeItem (new AppleTreeFile (path.toFile ()));

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

    notifyTotalsListeners ();
    sort (rootItem);
  }

  // ---------------------------------------------------------------------------------//
  private AppleTreeItem createTreeRoot ()
  // ---------------------------------------------------------------------------------//
  {
    AppleTreeItem root = new AppleTreeItem (new AppleTreeFile (rootFolder));
    root.setExpanded (true);

    return root;
  }

  // ---------------------------------------------------------------------------------//
  //  FilterPanel getFilterPanel ()
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    return filterPanel;
  //  }

  // ---------------------------------------------------------------------------------//
  //  @Override
  //  public void filterChanged ()
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    if (filterPanel.filtersActive ())
  //    {
  //      AppleTreeItem filteredRoot = createTreeRoot ();
  //      createFilteredTree (root, filteredRoot);
  //      tree.setRoot (filteredRoot);
  //    }
  //    else
  //      tree.setRoot (root);
  //  }

  // ---------------------------------------------------------------------------------//
  private void createFilteredTree (AppleTreeItem root, AppleTreeItem filteredRoot)
  // ---------------------------------------------------------------------------------//
  {
    for (TreeItem<AppleTreeFile> child : root.getChildren ())
    {
      AppleTreeItem filteredChild = new AppleTreeItem (child.getValue ());

      createFilteredTree ((AppleTreeItem) child, filteredChild);

      AppleTreeFile filePath = filteredChild.getValue ();

      if (filePath.isLocalFile ())//&& filterPanel.isMatch (filePath))
        filteredRoot.getChildren ().add (filteredChild);
      else if (filePath.isLocalDirectory () && filteredChild.getChildren ().size () > 0)
        filteredRoot.getChildren ().add (filteredChild);
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void setFont (Font font)
  // ---------------------------------------------------------------------------------//
  {
    treeView.setFont (font);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    treeView.save (prefs);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    treeView.restore (prefs);
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

    System.out.printf ("        %,8d  (%,d ignored)%n", totalFilesAccepted,
        totalFilesIgnored);
  }

  // ---------------------------------------------------------------------------------//
  private void sort (AppleTreeItem node)
  // ---------------------------------------------------------------------------------//
  {
    node.getChildren ()
        .sort (Comparator.comparing (new Function<TreeItem<AppleTreeFile>, String> ()
        {
          @Override
          public String apply (TreeItem<AppleTreeFile> t)
          {
            return t.getValue ().getSortString ();
          }
        }));
  }

  // ---------------------------------------------------------------------------------//
  void addTreeNodeListener (TreeNodeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!treeNodeListeners.contains (listener))
      treeNodeListeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  void addSuffixTotalsListener (SuffixTotalsListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  private void notifyTotalsListeners ()
  // ---------------------------------------------------------------------------------//
  {
    for (SuffixTotalsListener listener : listeners)
      listener.totalsChanged (extensionTotals);
  }

  // ---------------------------------------------------------------------------------//
  interface SuffixTotalsListener
  // ---------------------------------------------------------------------------------//
  {
    public void totalsChanged (int[] totals);
  }
}
