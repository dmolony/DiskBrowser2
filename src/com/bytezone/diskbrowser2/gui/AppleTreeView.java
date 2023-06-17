package com.bytezone.diskbrowser2.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;

import com.bytezone.appbase.FontChangeListener;
import com.bytezone.appbase.SaveState;
import com.bytezone.appleformat.FormattedAppleFileFactory;
import com.bytezone.filesystem.FileSystemFactory;

import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Callback;

// ---------------------------------------------------------------------------------//
class AppleTreeView extends TreeView<AppleTreeFile>
    implements SaveState, FontChangeListener
// ---------------------------------------------------------------------------------//
{
  static FileSystemFactory fileSystemFactory = new FileSystemFactory ();
  static FormattedAppleFileFactory formattedAppleFileFactory;
  //      new FormattedAppleFileFactory (prefs);

  private static final String PREFS_LAST_PATH = "LastPath";
  private static String SEPARATOR = "|";

  private Font font;

  private final MultipleSelectionModel<TreeItem<AppleTreeFile>> model;
  private final List<TreeNodeListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  AppleTreeView (AppleTreeItem root, FormattedAppleFileFactory formattedAppleFileFactory)
  // ---------------------------------------------------------------------------------//
  {
    super (root);

    AppleTreeView.formattedAppleFileFactory = formattedAppleFileFactory;

    model = getSelectionModel ();
    model.selectedItemProperty ()
        .addListener ( (obs, oldSel, newSel) -> itemSelected ((AppleTreeItem) newSel));

    setCellFactory (new Callback<TreeView<AppleTreeFile>, TreeCell<AppleTreeFile>> ()
    {
      @Override
      public TreeCell<AppleTreeFile> call (TreeView<AppleTreeFile> parm)
      {
        TreeCell<AppleTreeFile> cell = new TreeCell<> ()
        {
          private final ImageView imageView = new ImageView ();

          public void updateItem (AppleTreeFile treeFile, boolean empty)
          {
            super.updateItem (treeFile, empty);

            if (empty || treeFile == null)
            {
              setText (null);
              setGraphic (null);
            }
            else
            {
              setText (treeFile.toString ());
              imageView.setImage (treeFile.getImage ());
              setGraphic (imageView);
              setFont (font);
            }
          }
        };

        return cell;
      }
    });
  }

  // ---------------------------------------------------------------------------------//
  private void itemSelected (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    if (appleTreeItem == null)
    {
      System.out.println ("Should never happen - appleTreeItem is null");
      return;
    }

    AppleTreeFile treeFile = appleTreeItem.getValue ();

    // same test as in AppleTreeItem.getChildren()
    // if the item is selected BEFORE it is opened then we do this one

    if (treeFile.isLocalFile () && !treeFile.isAppleFileSystem ())
      appleTreeItem.getChildren ();          // force the file to be processed

    for (TreeNodeListener listener : listeners)
      listener.treeNodeSelected (appleTreeItem);
  }

  // ---------------------------------------------------------------------------------//
  void setRootFolder (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    setRoot (appleTreeItem);
    appleTreeItem.setExpanded (true);
  }

  // ---------------------------------------------------------------------------------//
  private Optional<TreeItem<AppleTreeFile>> getNode (String path)
  // ---------------------------------------------------------------------------------//
  {
    TreeItem<AppleTreeFile> node = getRoot ();
    Optional<TreeItem<AppleTreeFile>> optionalNode = Optional.empty ();

    String[] chunks = path.split ("\\" + SEPARATOR);

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
  private Optional<TreeItem<AppleTreeFile>> search (TreeItem<AppleTreeFile> parentNode,
      String name)
  // ---------------------------------------------------------------------------------//
  {
    parentNode.setExpanded (true);

    for (TreeItem<AppleTreeFile> childNode : parentNode.getChildren ())
      if (childNode.getValue ().getName ().equals (name))
        return Optional.of (childNode);

    return Optional.empty ();
  }

  // ---------------------------------------------------------------------------------//
  private String getSelectedItemPath ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder pathBuilder = new StringBuilder ();

    TreeItem<AppleTreeFile> item = model.getSelectedItem ();
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
      Optional<TreeItem<AppleTreeFile>> optionalNode = getNode (lastPath);
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
  void addListener (TreeNodeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  interface TreeNodeListener
  // ---------------------------------------------------------------------------------//
  {
    public void treeNodeSelected (AppleTreeItem appleTreeItem);
  }
}
