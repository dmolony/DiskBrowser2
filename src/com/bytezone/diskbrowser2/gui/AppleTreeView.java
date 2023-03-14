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
public class AppleTreeView extends TreeView<AppleTreeFile>
    implements SaveState, FontChangeListener
// ---------------------------------------------------------------------------------//
{
  static FileSystemFactory fileSystemFactory = new FileSystemFactory ();
  static FormattedAppleFileFactory formattedAppleFileFactory =
      new FormattedAppleFileFactory ();

  private static final String PREFS_LAST_PATH = "LastPath";
  private static String SEPARATOR = "|";

  private Font font;

  private final MultipleSelectionModel<TreeItem<AppleTreeFile>> model =
      getSelectionModel ();
  private final List<TreeNodeListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public AppleTreeView (AppleTreeItem root)
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

      AppleTreeFile treeFile = newSel.getValue ();      // newSel is a TreeItem<TreeFile>

      // same test as in AppleTreeItem.getChildren()
      // if item selection happens first then we do this one
      if (treeFile.isLocalFile () && !treeFile.isAppleFileSystem ())
        newSel.getChildren ();          // force the disk to be read

      for (TreeNodeListener listener : listeners)
        listener.treeNodeSelected ((AppleTreeItem) newSel);
    });

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
  public void setRootFolder (AppleTreeItem appleTreeItem)
  // ---------------------------------------------------------------------------------//
  {
    setRoot (appleTreeItem);
    appleTreeItem.setExpanded (true);
  }

  // ---------------------------------------------------------------------------------//
  Optional<TreeItem<AppleTreeFile>> getNode (String path)
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
  String getSelectedItemPath ()
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
    public void treeNodeSelected (AppleTreeItem appleTreeItem);
  }
}
