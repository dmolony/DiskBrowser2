package com.bytezone.diskbrowser2.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;

import com.bytezone.appbase.FontChangeListener;
import com.bytezone.appbase.SaveState;
import com.bytezone.filesystem.FileSystemFactory;

import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Font;

// ---------------------------------------------------------------------------------//
public class AppleTreeView extends TreeView<TreeFile> implements SaveState, FontChangeListener
// ---------------------------------------------------------------------------------//
{
  public static FileSystemFactory factory = new FileSystemFactory ();
  private static final String PREFS_LAST_PATH = "LastPath";
  private static String SEPARATOR = "/";
  private Font font;

  private final MultipleSelectionModel<TreeItem<TreeFile>> model = getSelectionModel ();
  private final List<TreeNodeListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public AppleTreeView (String rootFolderName)
  // ---------------------------------------------------------------------------------//
  {
    this (new AppleTreeItem (new TreeFile (new File (rootFolderName))));
  }

  // ---------------------------------------------------------------------------------//
  public AppleTreeView (TreeItem<TreeFile> root)
  // ---------------------------------------------------------------------------------//
  {
    super (root);

    model.selectedItemProperty ().addListener ( (obs, oldSel, newSel) ->
    {
      if (newSel == null)
      {
        System.out.println ("Should never happen - newSel is null");
        return;
      }

      TreeFile treeFile = newSel.getValue ();
      if (treeFile.isFile () && !treeFile.isAppleFileSystem ())
        treeFile.setAppleFile (factory.getFileSystem (treeFile.getFile ()));

      for (TreeNodeListener listener : listeners)
        listener.treeNodeSelected (treeFile);
    });
  }

  // ---------------------------------------------------------------------------------//
  public void setRootFolder (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    setRoot (appleTreeItem);
    appleTreeItem.setExpanded (true);
  }

  // ---------------------------------------------------------------------------------//
  Optional<TreeItem<TreeFile>> getNode (String path)
  // ---------------------------------------------------------------------------------//
  {
    TreeItem<TreeFile> node = getRoot ();
    Optional<TreeItem<TreeFile>> optionalNode = Optional.empty ();

    String[] chunks = path.split (SEPARATOR);

    for (int i = 2; i < chunks.length; i++)
    {
      model.select (node);
      optionalNode = search (node, chunks[i]);
      if (!optionalNode.isPresent ())
        break;
      node = optionalNode.get ();
    }

    setShowRoot (false);        // workaround for stupid javafx bug
    return optionalNode;
  }

  // ---------------------------------------------------------------------------------//
  private Optional<TreeItem<TreeFile>> search (TreeItem<TreeFile> parentNode, String name)
  // ---------------------------------------------------------------------------------//
  {
    parentNode.setExpanded (true);

    for (TreeItem<TreeFile> childNode : parentNode.getChildren ())
      if (childNode.getValue ().getName ().equals (name))
        return Optional.of (childNode);

    return Optional.empty ();
  }

  // ---------------------------------------------------------------------------------//
  String getSelectedItemPath ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder pathBuilder = new StringBuilder ();

    TreeItem<TreeFile> item = model.getSelectedItem ();
    while (item != null)
    {
      pathBuilder.insert (0, SEPARATOR + item.getValue ().getName ());
      item = item.getParent ();
    }

    return pathBuilder.toString ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    prefs.put (PREFS_LAST_PATH, getSelectedItemPath ());
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    String lastPath = prefs.get (PREFS_LAST_PATH, "");

    if (!lastPath.isEmpty ())
    {
      Optional<TreeItem<TreeFile>> optionalNode = getNode (lastPath);
      if (optionalNode.isPresent ())
      {
        int row = getRow (optionalNode.get ());
        model.select (row);
        scrollTo (model.getSelectedIndex ());
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void setFont (Font font)
  // ---------------------------------------------------------------------------------//
  {
    this.font = font;
    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  public void addListener (TreeNodeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  interface TreeNodeListener
  // ---------------------------------------------------------------------------------//
  {
    public void treeNodeSelected (TreeFile treeFile);
  }
}