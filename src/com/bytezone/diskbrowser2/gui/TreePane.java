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
import com.bytezone.appleformat.ApplePreferences;
import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.diskbrowser2.gui.AppleTreeView.TreeNodeListener;

import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
class TreePane extends BorderPane implements RootFolderChangeListener, FontChangeListener,
    SaveState, PreferenceChangeListener
// -----------------------------------------------------------------------------------//
{
  private int[] extensionTotals =
      new int[AppleTreeView.fileSystemFactory.getSuffixesSize ()];
  private int totalFilesIgnored;

  private AppleTreeView treeView;
  private AppleTreeItem root;
  private Font treeViewFont;

  private List<SuffixTotalsListener> suffixTotalsListeners = new ArrayList<> ();
  private final List<TreeNodeListener> treeNodeListeners = new ArrayList<> ();

  private FileFilterPreferences fileFilterPreferences;
  private final FormattedAppleFileFactory formattedAppleFileFactory;

  File rootFolder;

  // ---------------------------------------------------------------------------------//
  public TreePane (PreferencesManager preferencesManager,
      FormattedAppleFileFactory formattedAppleFileFactory)
  // ---------------------------------------------------------------------------------//
  {
    this.fileFilterPreferences = preferencesManager.fileFilter;
    this.formattedAppleFileFactory = formattedAppleFileFactory;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void rootFolderChanged (File rootFolder)
  // ---------------------------------------------------------------------------------//
  {
    this.rootFolder = rootFolder;

    root = new AppleTreeItem (new AppleTreeNode (rootFolder));
    root.setExpanded (true);

    createTree (root);                // adds all the tree nodes to the root

    treeView = new AppleTreeView (root, formattedAppleFileFactory);
    treeView.setFont (treeViewFont);
    //    treeView = new AppleTreeView (root);

    for (TreeNodeListener treeNodeListener : treeNodeListeners)
      treeView.addListener (treeNodeListener);

    setCenter (treeView);

    if (fileFilterPreferences.filtersActive ())
      preferenceChanged (fileFilterPreferences);
  }

  // ---------------------------------------------------------------------------------//
  private void createTree (AppleTreeItem rootItem)          // recursive
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

        AppleTreeItem newItem = new AppleTreeItem (new AppleTreeNode (path.toFile ()));

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
  private void createFilteredTree (AppleTreeItem root, AppleTreeItem filteredRoot)
  // ---------------------------------------------------------------------------------//
  {
    for (TreeItem<AppleTreeNode> child : root.getChildren ())
    {
      AppleTreeItem filteredChild = new AppleTreeItem (child.getValue ());

      if (child.getValue ().isLocalDirectory ())
        createFilteredTree ((AppleTreeItem) child, filteredChild);

      AppleTreeNode filePath = filteredChild.getValue ();

      if (filePath.isLocalFile ())
      {
        if (fileFilterPreferences.getShowFileTypes (filePath.getExtensionNo ()))
          filteredRoot.getChildren ().add (filteredChild);

      }
      else if (filePath.isLocalDirectory () && filteredChild.getChildren ().size () > 0)
        filteredRoot.getChildren ().add (filteredChild);
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void setFont (Font font)
  // ---------------------------------------------------------------------------------//
  {
    if (treeView != null)           // it may not exist yet
      treeView.setFont (font);
    treeViewFont = font;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    if (treeView != null)
      treeView.save (prefs);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    if (treeView != null)
      treeView.restore (prefs);
  }

  // ---------------------------------------------------------------------------------//
  private void sort (AppleTreeItem node)
  // ---------------------------------------------------------------------------------//
  {
    node.getChildren ()
        .sort (Comparator.comparing (new Function<TreeItem<AppleTreeNode>, String> ()
        {
          @Override
          public String apply (TreeItem<AppleTreeNode> t)
          {
            return t.getValue ().getSortString ();
          }
        }));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void preferenceChanged (ApplePreferences preference)
  // ---------------------------------------------------------------------------------//
  {
    if (preference instanceof FileFilterPreferences ffp)
    {
      fileFilterPreferences = ffp;

      if (fileFilterPreferences.filtersActive ())
      {
        AppleTreeItem filteredRoot = new AppleTreeItem (new AppleTreeNode (rootFolder));
        filteredRoot.setExpanded (true);
        createFilteredTree (root, filteredRoot);
        treeView.setRoot (filteredRoot);
      }
      else
        treeView.setRoot (root);
    }
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
    if (!suffixTotalsListeners.contains (listener))
      suffixTotalsListeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  private void notifyTotalsListeners ()
  // ---------------------------------------------------------------------------------//
  {
    for (SuffixTotalsListener listener : suffixTotalsListeners)
      listener.totalsChanged (extensionTotals);
  }

  // ---------------------------------------------------------------------------------//
  interface SuffixTotalsListener
  // ---------------------------------------------------------------------------------//
  {
    public void totalsChanged (int[] totals);
  }
}
